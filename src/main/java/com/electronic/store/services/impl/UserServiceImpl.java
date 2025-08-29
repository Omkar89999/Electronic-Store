package com.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entity.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	// image path
	@Value("${user.profile.image.path}")
	private String imagePath;

	private org.slf4j.Logger llogger = LoggerFactory.getLogger(UserServiceImpl.class);

	// create user
	@Override
	public UserDto createUser(UserDto userDto) {

		// generate unique id in string format
		String userId = UUID.randomUUID().toString();

		userDto.setUserId(userId);

		// dto to entity
		User user = DtoToUser(userDto);
		User savedUser = userRepository.save(user);

		// entity to dto
		UserDto newDto = entityToDto(savedUser);

		return newDto;
	}

	// update user
	@Override
	public UserDto updateUser(UserDto userDto, String userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given Id !!"));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setAbout(userDto.getAbout());
		user.setImage(userDto.getImage());

		// save data

		User updatedUser = userRepository.save(user);
		UserDto newDto = entityToDto(updatedUser);

		return newDto;
	}

	// delete user
	@Override
	public void deleteUser(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given Id !!"));

		// delete user profile image
		String fullPath = imagePath + user.getImage();

		Path path = Paths.get(fullPath);
		try {
			Files.delete(path);
		} catch (NoSuchFileException ex) {
			llogger.info("User image not found in folder ");
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// delete user

		userRepository.delete(user);

	}

	// get all users
	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

		// sorting descending and ascending
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepository.findAll(pageable);

		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

		return response;
	}

	//  get single user by id
	@Override
	public UserDto getUserById(String userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));

		return entityToDto(user);
	}

	// get user using email
	@Override
	public UserDto getUserByEmail(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given email id !!"));

		return entityToDto(user);
	}

	// search user using keyword
	@Override
	public List<UserDto> searchUser(String keyword) {

		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}


	
	private UserDto entityToDto(User savedUser) {
		return mapper.map(savedUser, UserDto.class);
	}

	private User DtoToUser(UserDto userDto) {
		return mapper.map(userDto, User.class);
	}

}

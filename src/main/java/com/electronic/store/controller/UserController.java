package com.electronic.store.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.ImageResponse;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.services.FileService;
import com.electronic.store.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
// @Builder
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imageUpaloadPath;

	private Logger logger=LoggerFactory.getLogger(UserController.class);

	// Create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto user = userService.createUser(userDto);

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	// Update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

		UserDto updatedUserDto = userService.updateUser(userDto, userId);

		return new ResponseEntity<UserDto>(updatedUserDto, HttpStatus.OK);

	}

	// Delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);

		ApiResponseMessage message = ApiResponseMessage.builder().message("User Deleted Successfully !!").success(true)
				.status(HttpStatus.OK).build();

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// Get All
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
		// List<UserDto> allUsers =
		// userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
		// return new ResponseEntity<PageableResponse<UserDto>>(allUsers,
		// HttpStatus.OK);

	}

	// Get Single
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId) {

		UserDto userDto = userService.getUserById(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);

	}

	// Get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getEmail(@PathVariable String email) {
		UserDto userByEmailDto = userService.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userByEmailDto, HttpStatus.OK);

	}

	// Search User
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
		List<UserDto> searchUserDto = userService.searchUser(keyword);
		return new ResponseEntity<>(searchUserDto, HttpStatus.OK);

	}

	// upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String userId,
			@RequestParam("userImage") MultipartFile image) throws IOException {

		String imageName = fileService.uploadFile(image, imageUpaloadPath);

		UserDto user = userService.getUserById(userId);

		user.setImage(imageName);
		userService.updateUser(user, userId);

		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("image is uploaded successfully..")
				.status(HttpStatus.CREATED).build();

		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	}

	// serve user image

	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException{
		UserDto user = userService.getUserById(userId);

		// logger

		logger.info("User image name : {}",user.getImage());
		InputStream resource= fileService.getResource(imageUpaloadPath, user.getImage());

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}
}

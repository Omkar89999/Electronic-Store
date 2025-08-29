package com.electronic.store.services;

import java.util.List;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;

public interface UserService {

	// create user
	UserDto createUser(UserDto userDto);

	// update user
	UserDto updateUser(UserDto userDto, String userId);

	// delete user
	void deleteUser(String userId);

	// get all user
	PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir);

	// get single user by id
	UserDto getUserById(String userId);

	// get single user by email
	UserDto getUserByEmail(String email);

	// search user
	List<UserDto> searchUser(String keyword);
	
	// rested all feature
	

}

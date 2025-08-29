package com.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.store.entity.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String>{

	
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email,String password);
	List<User> findByNameContaining(String keyword);
	
	
}

package com.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.model.UsersModel;

public interface UsersRepository extends JpaRepository<UsersModel, Long>{
	
	
	UsersModel findByEmailAndPassword(String email, String password);
	
	UsersModel findFirstByEmail(String email);

}

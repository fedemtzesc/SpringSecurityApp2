package com.fdxsoft.SpringSecurityApp.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.fdxsoft.SpringSecurityApp.persistence.entity.UserEntity;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {

	Optional<UserEntity> findUserEntityByUsername(String username);
	
}

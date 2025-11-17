package com.fdxsoft.SpringSecurityApp;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdxsoft.SpringSecurityApp.persistence.entity.PermissionEntity;

import com.fdxsoft.SpringSecurityApp.persistence.entity.RoleEntity;
import com.fdxsoft.SpringSecurityApp.persistence.entity.UserEntity;
import com.fdxsoft.SpringSecurityApp.persistence.repository.IUserRepository;

import enums.RoleEnum;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(IUserRepository userRepository, PasswordEncoder crypt) {
		return args -> {
			/*Crear los permisos*/
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();
			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();
			
			/*Creamos los roles*/
			RoleEntity roleAdmin = RoleEntity.builder()
					.role(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();
			RoleEntity roleUser = RoleEntity.builder()
					.role(RoleEnum.USER)
					.permissions(Set.of(createPermission, readPermission))
					.build();
			RoleEntity roleInvited = RoleEntity.builder()
					.role(RoleEnum.INVITED)
					.permissions(Set.of(readPermission))
					.build();
			RoleEntity roleDeveloper = RoleEntity.builder()
					.role(RoleEnum.DEVELOPER)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();
			
			/*Creamos los usuarios*/
			UserEntity userAdmin = UserEntity.builder()
					.username("admin")
					.password(crypt.encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialsNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();
			UserEntity userUser = UserEntity.builder()
					.username("user")
					.password(crypt.encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialsNoExpired(true)
					.roles(Set.of(roleUser))
					.build();
			UserEntity userInvited = UserEntity.builder()
					.username("invited")
					.password(crypt.encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialsNoExpired(true)
					.roles(Set.of(roleInvited))
					.build();
			UserEntity userDeveloper = UserEntity.builder()
					.username("developer")
					.password(crypt.encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialsNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();
			/*Finalmente guardamos todas las entidades en la BD*/
			userRepository.saveAll(List.of(userAdmin, userUser, userInvited, userDeveloper));
		};
	}

}

package com.fdxsoft.SpringSecurityApp.persistence.entity;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="e_user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;
	
	//Spring Security Parameters
	private boolean isEnabled;
	private boolean accountNoExpired;
	private boolean accountNoLocked;
	private boolean credentialsNoExpired;
	
	//FetchType.EAGER hace que al cargar el usuario en automatico me cargue los roles
	//CascadeType.ALL hace que al guardar el usuario, va a guardar todos los roles asociados
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(	name = "user_role", 
				joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles = new HashSet<>();
}

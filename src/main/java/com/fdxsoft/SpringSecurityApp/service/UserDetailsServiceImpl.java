package com.fdxsoft.SpringSecurityApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdxsoft.SpringSecurityApp.persistence.entity.UserEntity;
import com.fdxsoft.SpringSecurityApp.persistence.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//El metodo findUserEntityByUserName lo creamos dentro del repositorio de IUserRepository
		UserEntity userEntity = userRepository.findUserEntityByUsername(username)
						.orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe."));
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		//Tomamos cada rol del usuario y vamos convirtiendo cada uno en un SimpleGrantedAuthority y luego
		//Lo vamos metiendo a la lista de authorityList.
		userEntity.getRoles()
				.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))));
		
		//Ahora agregamos los permisos sacandolos de cada rol
		userEntity.getRoles().stream()
						.flatMap(role -> role.getPermissions().stream())
						.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
		
		return new User(userEntity.getUsername(), 
						userEntity.getPassword(),
						userEntity.isEnabled(),
						userEntity.isAccountNoExpired(),
						userEntity.isCredentialsNoExpired(),
						userEntity.isAccountNoLocked(),
						authorityList);
	}

	
}

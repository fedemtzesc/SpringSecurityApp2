package com.fdxsoft.SpringSecurityApp.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * En esta clase se define toda la arquitectura de Spring Security Los
 * componentes contenidos del diagrama son los siguientes:
 * 
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Esta configuracion es para poder configurar permisos en los end poits
						// directamente
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		/*	El objeto http se va pasando por cada uno de los filtros y lo van modificando
		 	Aqui se definen las condiciones de nuestros filtros que van en el DelegatingFilterProxy
		 	OJO:
			En este caso, quitamos los filtros authoriseHttpRequest del DelegatedFilterSecurity 
			para configurar los accesos desde los endpoint gracias a la anotacion @EnableMethodSecurity
		
		 */
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.httpBasic(Customizer.withDefaults()) //Si no lo pongo no funciona Basic Auth
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	/*** Se encarga de conectarse a la BD para traer el usuario
	 * Pero en este caso, vamos a cargar los usuarios en memoria
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		//Estamos creando manualmente los usuarios, simulando que los 
		//fue a traer a la BD.
		userDetailsList.add(User.withUsername("federico")
				.password("AhMesAmies2506")
				.roles("ADMIN")
				.authorities("READ","CREATE")
				.build());
		
		userDetailsList.add(User.withUsername("fedemtzesc")
				.password("calibre3006")
				.roles("USER")
				.authorities("READ", "CREATE")
				.build());
		//		
		return new InMemoryUserDetailsManager(userDetailsList);
	}

	/**
	 * Se encarga de validar el password del usuario 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder();
		// Esta instancia puede validar el password sin tener que encriptarlo
		return NoOpPasswordEncoder.getInstance();
	}

}

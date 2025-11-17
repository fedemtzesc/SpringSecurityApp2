package com.fdxsoft.SpringSecurityApp.config;


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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fdxsoft.SpringSecurityApp.service.UserDetailsServiceImpl;

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
				.authorizeHttpRequests(http -> {
					//Configuracion de endpoints publicos con acceso a todo mundo sin filtros
					http.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();
					
					//Configuracion de endpoints privados, con acceso basados en los filtros
					http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAuthority("CREATE");
					http.requestMatchers(HttpMethod.GET, "/auth/hello-secured2").hasAuthority("REFACTOR");
					
					//Configuracion de endpoints de los Verbos HTTP
					http.requestMatchers(HttpMethod.GET, "/auth/get").hasAuthority("READ");
					http.requestMatchers(HttpMethod.POST, "/auth/post").hasAuthority("CREATE");
					http.requestMatchers(HttpMethod.PUT, "/auth/put").hasAuthority("UPDATE");
					http.requestMatchers(HttpMethod.DELETE, "/auth/delete").hasAuthority("DELETE");
					http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAuthority("REFACTOR");
					
					//Configuracion el resto de los endpoints - NO DEFINIDOS AUN
					//http.anyRequest().denyAll();
					http.anyRequest().authenticated();
				})
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Para que ahora vaya a la BD a atraer la BD eliminamos el metodo userDetailsService()
	 * y le inyectamos nuestra nueva clase UserDetailsServiceImpl para que obtenga ahora si
	 * de la BD la informacion del usario.
	 * 
	 * @param userDetailsService
	 * @return
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	
	/**
	 * Se encarga de validar el password del usuario 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
		// Esta instancia puede validar el password sin tener que encriptarlo
		//return NoOpPasswordEncoder.getInstance();
	}

}

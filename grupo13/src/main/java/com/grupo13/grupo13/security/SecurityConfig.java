package com.grupo13.grupo13.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.grupo13.grupo13.security.jwt.JwtRequestFilter;
import com.grupo13.grupo13.security.jwt.UnauthorizedHandlerJwt;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	RepositoryUserDetailsService userDetailsService;

	@Autowired
	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.securityMatcher("/api/**")
			.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));
		
		http
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()				
					.requestMatchers(HttpMethod.GET, "/api/weapon/*/image").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/armor/*/image").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/search").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/weapons").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/armors").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()

                    // PRIVATE ENDPOINTS
					.requestMatchers(HttpMethod.GET, "/api/characters").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/weapons").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/armors").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/weapon/*/image").hasRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/api/armor/*/image").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/api/weapon/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/api/armor/**").hasRole("ADMIN")

					.requestMatchers(HttpMethod.DELETE, "/api/character/**").hasRole("USER")
					.requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("USER")

					.requestMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
                    .requestMatchers(HttpMethod.POST,"/api/**").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT,"/api/**").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/api/editCharacter").hasRole("USER")
					.requestMatchers(HttpMethod.PUT, "/api/editUser").hasRole("USER")
					.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
					

					// PUBLIC ENDPOINTS
					.anyRequest().permitAll()
			);
		
        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http
				.authorizeHttpRequests(authorize -> authorize
						/////// PUBLIC PAGES
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/armorshop/**").permitAll()
						.requestMatchers("/weaponshop/**").permitAll()
						.requestMatchers("/weapon/*").permitAll()
						.requestMatchers("/armor/*").permitAll()
						.requestMatchers("/register/**").permitAll()
						.requestMatchers("/error").permitAll()
						.requestMatchers("/image/**").permitAll()
						/////// PRIVATE PAGES
						//USER PAGES
						.requestMatchers("/user").hasAnyRole("USER")
						.requestMatchers("/").hasAnyRole("USER")
						.requestMatchers("/formProcess/**").hasAnyRole("USER")
						.requestMatchers("/search/**").hasAnyRole("USER")
						.requestMatchers("/purchaseWeapon").hasAnyRole("USER")
						.requestMatchers("/purchaseArmor").hasAnyRole("USER")
						.requestMatchers("/equipWeapon/**").hasAnyRole("USER")
						.requestMatchers("/equipArmor/**").hasAnyRole("USER")
						.requestMatchers("/unEquipWeapon/**").hasAnyRole("USER")
						.requestMatchers("/unEquipArmor/**").hasAnyRole("USER")
						.requestMatchers("/character/**").hasAnyRole("USER")
						.requestMatchers("editUser").hasAnyRole("USER")

						//ADMIN PAGES
						.requestMatchers("/equipment_manager/**").hasAnyRole("ADMIN")
						.requestMatchers("/userList").hasAnyRole("ADMIN")
						.requestMatchers("/armor/*/edit").hasAnyRole("ADMIN")
						.requestMatchers("/weapon/*/edit").hasAnyRole("ADMIN")
						.requestMatchers("/new_armor/**").hasAnyRole("ADMIN")
						.requestMatchers("/new_weapon/**").hasAnyRole("ADMIN")
						.requestMatchers("/admin/**").hasAnyRole("ADMIN")

						//with all this should cover all adminController and etc, but keep checking, regular tests
						.anyRequest().authenticated()


				)
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginerror")
						.defaultSuccessUrl("/")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll()
				);

		return http.build();
	}
}

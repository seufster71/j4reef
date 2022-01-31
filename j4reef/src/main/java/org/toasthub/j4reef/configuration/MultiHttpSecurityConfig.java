package org.toasthub.j4reef.configuration;

import org.toasthub.j4reef.controller.RestAuthenticationEntryPoint;
import org.toasthub.j4reef.security.api.UserManagerSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MultiHttpSecurityConfig {
	
	@Autowired
	UserManagerSvc userManagerSvc;
		
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(4);
	}
	
	@Configuration
	@Order(1)                                                        
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.antMatcher("/api/**")
			//	.addFilterAfter(toasthubLoginFilter(), BasicAuthenticationFilter.class)=
				.authorizeRequests()
					.antMatchers("/api/public/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.httpBasic()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint);
		}
	}

	@Configuration                                                   
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
	    private AccessDeniedHandler accessDeniedHandler;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
            .authorizeRequests()
				.antMatchers("/", "/login/**", "/css/**", "/img/**", "/libs/**", "/js/**", "/public/**", "/dist/**").permitAll()
				.antMatchers("/test/**").hasAuthority("PRIVATE")
				.anyRequest().authenticated()
            .and()
   			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
		}
	}

	
	//private ToasthubLoginFilter toasthubLoginFilter() {
	//	return new ToasthubLoginFilter(userManagerSvc);
	//}
}

package de.mediapool.server.security.simple.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import de.mediapool.server.security.services.MPUserDetailsService;
 
@ComponentScan("de.mediapool.server")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	/**
	 * This section defines the user accounts which can be used for
	 * authentication as well as the roles each user has.
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("Invoking: configure(auth)");

		auth.userDetailsService(customUserDetailsService());
	}

	@Bean @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean   
	public UserDetailsService customUserDetailsService() {
		return new MPUserDetailsService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Invoking: configure(http)");

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

		//		http.authorizeRequests().antMatchers("/rest/**").authenticated();
		//		http.addFilterBefore(new SimpleCORSFilter(), ChannelProcessingFilter.class);
		//		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		//		http.formLogin().successHandler(authenticationSuccessHandler);
		//		http.formLogin().failureHandler(authenticationFailureHandler);
		http.formLogin().loginPage("/login-page");
		http.formLogin().failureUrl("/login-error");
		http.exceptionHandling().accessDeniedPage("/login-error");
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.csrf();
		http.rememberMe();
	}

	@PostConstruct
	private void init() {
		logger.debug("Invoking: init()");
	}
}
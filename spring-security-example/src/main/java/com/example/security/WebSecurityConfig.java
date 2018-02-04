package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/hello").access("hasRole('ROLE_ADMIN')").anyRequest().permitAll()
			.antMatchers("/css/**", "/js/**", "/home").permitAll()
			.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.authenticationDetailsSource(customWebAuthenticationDetailsSource).defaultSuccessUrl("/")
				.successHandler(successHandler())
			.and()
				.logout().logoutSuccessUrl("/login?logout")
			.and()
				.exceptionHandling().accessDeniedPage("/403")
			.and()
				.csrf();
//		http.addFilterBefore(authenticationFilterObtainUsername(),UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(authenticationFilterAnotherParam(),UsernamePasswordAuthenticationFilter.class);

	}
	/**
	 * 로그인 풀렸을 때 다시 로그인하면 그 전 페이지로 이동
	 * .successHandler(successHandler()) 추가
	 * /login 컨트롤러에 
	 * String referrer = request.getHeader("Referer");
	 * request.getSession().setAttribute("prevPage", referrer); 추가    	
	 * successHandler() 주석 풀기.
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    return new CustomLoginSuccessHandler("/");
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	*  AuthenticationFilterAnotherParam 방법을 쓸 경우
	*  이 주석과 http.addFilterBefore(authenticationFilterAnotherParam(),UsernamePasswordAuthenticationFilter.class); 주석을 풀어야 함.
	*  이 방법은 세션에 파라미터 저장하여 컨트롤러에서 HttpServletRequest로 불러와 씀.
	*  authenticationManagerBean()와 authenticationFilterAnotherParam 주석 풀기.
	*/
	/*@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationFilterAnotherParam authenticationFilterAnotherParam() throws Exception {
	    AuthenticationFilterAnotherParam authenticationFilterAnotherParam = new AuthenticationFilterAnotherParam();
	    authenticationFilterAnotherParam.setAuthenticationManager(this.authenticationManagerBean());
	    authenticationFilterAnotherParam.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
	    return authenticationFilterAnotherParam;
	}*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *  AuthenticationFilterObtainUsername 방법을 쓸 경우
	 *  이 주석과 http.addFilterBefore(authenticationFilterObtainUsername(),UsernamePasswordAuthenticationFilter.class); 주석을 풀어야 함.
	 *  이 방법은 UserDetails의 loadUserByUsername에 username에 파라미터가 붙어 잘라서 쓰는 방법임.
	 *  authenticationManagerBean()와 authenticationFilterObtainUsername() 주석 풀기.
	 */
	/*@Bean  
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
	}

    @Bean
    public AuthenticationFilterObtainUsername authenticationFilterObtainUsername() throws Exception {
       AuthenticationFilterObtainUsername authenticationFilterObtainUsername = new AuthenticationFilterObtainUsername();
       authenticationFilterObtainUsername.setAuthenticationManager(this.authenticationManagerBean());
       authenticationFilterObtainUsername.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
       return authenticationFilterObtainUsername;
    }*/
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
/*	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests().antMatchers("/", "/home", "/auau", "/js/**", "/css/**").permitAll().anyRequest().authenticated().
			and().formLogin().loginPage("/login").permitAll().
			and().logout().permitAll();
	}*/
	
/*	@Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
        web.ignoring().antMatchers("/static/**"); // #3
        web.ignoring().antMatchers("/ajax/**");
        web.ignoring().antMatchers("/common/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/css/**");
    }*/

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configure global");
//		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).;
	}*/
}

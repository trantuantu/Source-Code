package com.dulich.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 
	   MyDBAuthenticationService myDBAauthenticationService;
	 
	   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		   
	       auth.userDetailsService(myDBAauthenticationService);
	 
	   }
	 
	   @Override
	   protected void configure(HttpSecurity http) throws Exception {
	 
	       http.csrf().disable();

	       http.authorizeRequests().antMatchers("/", "/home", "/login", "/logout").permitAll();
	  
	       http.authorizeRequests().antMatchers("/user", "/searchUser", "/deleteUser", "/editUser", "/addUser",
	    		   "/duyet_nghiphep", "/duyet_tour", "/thongketour", "/thongke_sltour_Nam_Thang", "/tour_In_Thang" ).access("hasRole('ROLE_0'");
	       
	       http.authorizeRequests().antMatchers("/comfirmDuyetTour", "/searchDuyetTour").access("hasRole('ROLE_1'");
	       
	       http.authorizeRequests().antMatchers("/tour_updatechiphi").access("hasRole('ROLE_2'");
	       
	       http.authorizeRequests().antMatchers( "/addTour", "/searchRestaurant", "/editRestaurant", "/deleteRestaurant",
	    		   "/addRestaurant", "/searchHotel", "/deleteHotel", "/editHotel", "/addHotel").access("hasAnyRole('ROLE_0', 'ROLE_2)");
	       
	       http.authorizeRequests().antMatchers("/dangky_nghiphep").access("hasAnyRole('ROLE_1', 'ROLE_2)");
	 
	       http.authorizeRequests().and().formLogin()//

	               .loginProcessingUrl("/j_spring_security_check") // Submit URL
	               .loginPage("/login")//
	               .defaultSuccessUrl("/user")//
	               .failureUrl("/login?error=true")//
	               .usernameParameter("username")//
	               .passwordParameter("password")

	               .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
	   }
}

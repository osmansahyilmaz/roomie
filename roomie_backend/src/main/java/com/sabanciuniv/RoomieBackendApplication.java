package com.sabanciuniv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.sabanciuniv.filter.LoginFilter;
import com.sabanciuniv.service.UserService;

@SpringBootApplication
public class RoomieBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomieBackendApplication.class, args);
	}
	
	@Bean
	FilterRegistrationBean<LoginFilter> loginFilter(UserService userService){
	    FilterRegistrationBean<LoginFilter> registrationBean 
	      = new FilterRegistrationBean<>();
	        
	    registrationBean.setFilter(new LoginFilter(userService));
	    registrationBean.addUrlPatterns("/user/userinfo/*");
	    registrationBean.addUrlPatterns("/contact/*");
	    registrationBean.setOrder(1);
	        
	    return registrationBean;    
	}
}

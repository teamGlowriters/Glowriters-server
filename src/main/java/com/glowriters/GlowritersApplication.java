package com.glowriters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//스프핑 시큐리티 끔
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GlowritersApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlowritersApplication.class, args);
	}

}

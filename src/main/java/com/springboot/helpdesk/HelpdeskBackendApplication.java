package com.springboot.helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class HelpdeskBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskBackendApplication.class, args);
	}

}

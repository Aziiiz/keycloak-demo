package com.aziz.springbootkeycloakdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringbootKeycloakDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKeycloakDemoApplication.class, args);
	}

}

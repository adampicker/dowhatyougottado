package com.android.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@ComponentScan(basePackages= {"com.android.controller"})
@ComponentScan({"com.android.controller"})
@EntityScan("com.android.model")
@EnableJpaRepositories("com.android.repository")
public class AndroidRestApplication {

	public static void main(String[] args) {
		

		
		
		
		SpringApplication.run(AndroidRestApplication.class, args);
		
                
                
	}

}


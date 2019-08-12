package com.android.repository;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.android.model.Task;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class InitDatabase {
	
	@Bean
	CommandLineRunner initDatabase(TaskRepository repository) {
		return args -> {
			//System.out.println("Preloading " + repository.save(new Task("Do dishes", false, new Date())));
			//System.out.println("Preloading " + repository.save(new Task("Do laundry", false, new Date())));
		};
	}
}

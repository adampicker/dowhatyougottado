package com.android.controller;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.android.model.Founder;
import com.android.model.Task;
import com.android.modeldto.TaskDTO;
import com.android.repository.TaskRepository;
import com.android.repository.FounderRepository;

@RestController
public class TaskController {
	
	Calendar rightNow;

	TaskRepository taskRepository;
	FounderRepository founderRepository;;
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(TaskDTO.personMap);
	    return modelMapper;
	}
	
	TaskController(TaskRepository taskRepository, FounderRepository accountRepository) {
		this.taskRepository = taskRepository;
		this.founderRepository = accountRepository;
	}
	
	 @RequestMapping(value = "/", method = RequestMethod.GET)
		public String home(){

			rightNow = Calendar.getInstance();
			System.out.println("HOME: " + rightNow);
			Founder f = new Founder("Wojtek");
			Founder f2 = new Founder("Mati");
			founderRepository.save(f);
			founderRepository.save(f2);
			taskRepository.save(new Task("Do dishes", false, rightNow, rightNow, f));
			taskRepository.save(new Task("Do laundry", false, rightNow, rightNow, f));
			
			taskRepository.save(new Task("Do shoping", false, rightNow, rightNow, f2));
			taskRepository.save(new Task("Buy milk", false, rightNow, rightNow, f2));
			
			int length = 5;
		    boolean useLetters = true;
		    boolean useNumbers = true;
		    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		 
		    System.out.println(generatedString);
			return "hELLO HOME";
		}
	
	
	@GetMapping("/task")
	List<TaskDTO> all(){
		ModelMapper modelMapper = modelMapper();
		List<TaskDTO> data = new LinkedList<>();
		
		for (Task task : taskRepository.findAll()) data.add(modelMapper.map(task, TaskDTO.class));
		return data;
	}

	
	@PostMapping("/task")
	TaskDTO newTask(@RequestBody TaskDTO newTask) {
		System.out.println("XD");
		ModelMapper modelMapper = modelMapper();

		Calendar deadline = Calendar.getInstance();
		deadline.setTimeInMillis(newTask.getDeadline());
		Task task = new Task(newTask.getContent(), newTask.getShared(), deadline, Calendar.getInstance(), founderRepository.getFounderByName(newTask.getFounderName()));
		System.out.println("creating task....");
		System.out.println("Content: " + newTask.getContent() + " ifshared: " + newTask.getShared().toString() );
		TaskDTO toSend = modelMapper.map(taskRepository.save(task),TaskDTO.class);
		
		return toSend;
	}
	
	@GetMapping("/task/{id}")
	TaskDTO one(@PathVariable Long id) {
		
		ModelMapper modelMapper = modelMapper();			
		return modelMapper.map(taskRepository.findById(id).get(), TaskDTO.class);
	}
	
	@GetMapping("/key/{key}")
	List<TaskDTO> tasksByKey(@PathVariable String key) {
		
		System.out.println("Getting key.. = " + key);
		//Founder founder = customAccountRepository.getFounderByListKey(key);
		ModelMapper modelMapper = modelMapper();	
		List<TaskDTO> taskdto = new LinkedList<>();
		for (Task task : taskRepository.getTaskByFounderListKey(key) ) taskdto.add(modelMapper.map(task, TaskDTO.class));
		System.out.println("listsize:" + taskdto.size());
		return taskdto;
	}
	
	@GetMapping("/task/remove/{id}")
	void removeTask(@PathVariable long id) {
		
		System.out.println("Task to remove = " + id);
		//Founder founder = customAccountRepository.getFounderByListKey(key);
		taskRepository.deleteById(id);
		System.out.println("task removed");
		
	}
	
	@GetMapping("/task/done/{id}")
	void makeTaskDone(@PathVariable long id) {
		
		System.out.println("Task to done = " + id);
		Task taskToDone = taskRepository.findById(id).get();
		taskToDone.setDone(true);
		taskRepository.save(taskToDone);
		System.out.println("task done!");
		
	}
	
	


}

package com.android.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.android.model.Founder;
import com.android.model.Task;
import com.android.modeldto.FounderDTO;
import com.android.modeldto.TaskDTO;
import com.android.repository.FounderRepository;
import com.android.repository.TaskRepository;

@RestController
public class FounderController {
	
	TaskRepository taskRepository;
	FounderRepository founderRepository;;
	
	Calendar rightNow;
	
	@Bean
	public ModelMapper founderModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(FounderDTO.founderMap);
	    return modelMapper;
	}
	
	FounderController(TaskRepository taskRepository, FounderRepository accountRepository) {
		this.taskRepository = taskRepository;
		this.founderRepository = accountRepository;
	}
	
	@GetMapping("/f")
	FounderDTO createAccount(){
		
		System.out.println("XX");
		ModelMapper modelMapper = founderModelMapper();
		return modelMapper.map(founderRepository.findById((long)1).get(), FounderDTO.class);		
	}

	
	
	@PostMapping("/createaccount")
	Founder createAccount(@RequestBody Founder newFounder){
		rightNow = Calendar.getInstance();
		System.out.println("creating new account...");
		
		Founder createdFounder = founderRepository.save(new Founder(newFounder.getAccountName()));	
		
		
		System.out.println("Account created");
		return createdFounder;
		
	}
	
	@PostMapping("/getcoordinates")
	LinkedList<FounderDTO> getCoordinates(@RequestBody Founder founder){
		System.out.println("Getting co-ordinates");		
		ModelMapper modelMapper = founderModelMapper();
		LinkedList<FounderDTO> founderWithCoordinates = new LinkedList<>();
		List<Founder> yourMates = founderRepository.getFoundersWithMyList(founder.getAccountName());
		for (Founder f : yourMates) founderWithCoordinates.add(modelMapper.map(f, FounderDTO.class));
		return founderWithCoordinates;
	}
	
	@PostMapping("/setlistkey")
		FounderDTO sendListKey(@RequestBody FounderDTO founder){
		System.out.println("Setting list key: " + founder.getListKey());
		System.out.println("founder name:  " + founder.getAccountName());
		Founder founderToSet = founderRepository.getFounderByName(founder.getAccountName());
		System.out.println("key founde to set: " + founderToSet.getAccountName());
		System.out.println("key: " + founder.getListKey() );
		founderToSet.setKey(founder.getListKey());
		ModelMapper modelMapper = founderModelMapper();
		

		return modelMapper.map(founderRepository.save(founderToSet), FounderDTO.class);
	}
	
	@PutMapping("/setcoordinates")
	void sendCoordinates(@RequestBody Founder founder){
		System.out.println("Setting co-ordinates");
		
		Founder founderToSet = founderRepository.getFounderByName(founder.getAccountName());
		founderToSet.setLongtitude(founder.getLongtitude());
		founderToSet.setLatitude(founder.getLatitude());
		founderRepository.save(founderToSet);
		
	}

}

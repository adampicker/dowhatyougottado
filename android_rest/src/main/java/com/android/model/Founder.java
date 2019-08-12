package com.android.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;

import com.android.model.Task;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@Entity
@JsonSerialize
public class Founder {
	
	@Column(name = "id")
	private @Id @GeneratedValue Long id;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "list_key")
	private String list_key;
	
	@OneToMany(targetEntity=Task.class, mappedBy = "founder", cascade = CascadeType.ALL)
	private Set<Task> tasks;
	
	@Column(name = "longtitude")
	private double longtitude;
	
	@Column(name = "latitude")
	private double latitude;
	
	public Founder(String name) {
		this.accountName = name;
		generateListKey();
	}
	
	public Founder() {}

	public Long getId() {
		return this.id;
	}
	public String getAccountName() {
		return this.accountName;
	}
	public String getListKey() {
		return this.list_key;
	}
	
	//@OneToMany(targetEntity=Task.class, mappedBy = "account", cascade = CascadeType.ALL)
	public Set<Task> getTasks() {
		return tasks;
	}
	
	public double getLongtitude() {
		return this.longtitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public void setKey(String listKey) {
		this.list_key = listKey;
	}
	
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void setLongtitude(double longtitude) {
		this.longtitude  = longtitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void generateListKey() {
		int length = 5;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    this.list_key = RandomStringUtils.random(length, useLetters, useNumbers);
	}

}

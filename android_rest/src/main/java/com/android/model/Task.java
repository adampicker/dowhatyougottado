package com.android.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@JsonSerialize
public class Task {
	
	@Column(name = "id")
	private @Id @GeneratedValue Long id;
	@Column(name = "content")
	private String content;
	@Column(name = "shared")
	private boolean shared;
	@Column(name ="deadline")
	private Calendar deadline;
	@Column(name="created_at")
	private Calendar createdAt;
	@Column(name="done")
	private boolean done;

	@ManyToOne
    @JoinColumn(name = "founder_id")
	private Founder founder;
	
	public Task(String content, boolean ifShared, Calendar deadline, Calendar createdAt, Founder founder) {
		this.content = content;
		this.shared = ifShared;
		this.deadline = deadline;
		this.createdAt = createdAt;
		this.founder = founder;
		this.done = false;
	}
	
	public Task() {}
	
	public Long getId() {
		return this.id;
	}
	public String getContent() {
		return this.content;
	}
	
	public Boolean getShared() {
		return this.shared;
	}
	
	public Calendar getDeadline() {
		return this.deadline;
	}
	
	public Calendar getCreatedAt() {
		return this.createdAt;
	}
	
	public boolean getDone() {
		return this.done;
	}
	
	public Founder getFounder(){
		return this.founder;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	
	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}
	
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setFounder(Founder founder) {
		this.founder = founder;
	}
	
	public long getCreatedAtInMillis() {
		return this.createdAt.getTimeInMillis();
	}
	
	public long getDeadlineInMillis() {
		return this.deadline.getTimeInMillis();
	}
	
	

}

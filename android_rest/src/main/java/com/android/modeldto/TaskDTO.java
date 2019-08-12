package com.android.modeldto;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.modelmapper.PropertyMap;

import com.android.model.Founder;
import com.android.model.Task;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@JsonSerialize
@Data
public class TaskDTO {
	
	
	public Long id;
	public String content;
	public boolean shared;
	public long deadline;
	public long createdAt;
	public boolean done;
	public String founderName;
	
	public TaskDTO(String content, boolean ifShared, long deadline, long createdAt, String founderName) {
		this.content = content;
		this.shared = ifShared;
		this.deadline = deadline;
		this.createdAt = createdAt;
		this.founderName = founderName;
	}
	
	public TaskDTO() {}
	
	public Long getId() {
		return this.id;
	}
	public String getContent() {
		return this.content;
	}
	
	public Boolean getShared() {
		return this.shared;
	}
	
	public long getDeadline() {
		return this.deadline;
	}
	
	public long getCreatedAt() {
		return this.createdAt;
	}
	
	public boolean getDone() {
		return this.done;
	}
	
	public String getFounderName(){
		return this.founderName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setFounderId(String founderName) {
		this.founderName = founderName;
	}
	
	
	public static PropertyMap<Task, TaskDTO> personMap = new PropertyMap<Task, TaskDTO>() {
		  protected void configure() {
		    map(source.getId(), destination.id);
		    map(source.getContent(), destination.content);
		    map(source.getShared(), destination.shared);
		    map(source.getDeadlineInMillis(), destination.deadline);
		    map(source.getCreatedAtInMillis(), destination.createdAt);
		    map(source.getDone(), destination.done);
		    map(source.getFounder().getAccountName(), destination.founderName);
		  }
		};
	

}

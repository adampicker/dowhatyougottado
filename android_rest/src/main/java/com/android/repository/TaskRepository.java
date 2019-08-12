package com.android.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.android.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("SELECT task FROM com.android.model.Founder founder, com.android.model.Task task WHERE founder.list_key LIKE :list_key AND task.founder.id = founder.id")
	List<Task> getTaskByFounderListKey(@Param("list_key") String key);
	
	

}

package com.android.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.android.model.Founder;

@Repository
public interface FounderRepository extends JpaRepository<Founder, Long> {
	
	@Query("SELECT COUNT(*) FROM com.android.model.Founder")
	String countFounder();
	
	@Query("SELECT founder FROM com.android.model.Founder founder WHERE founder.accountName  = :founderName")
	Founder getFounderByName(@Param("founderName") String founderName);
	
	@Query("SELECT founderThem FROM com.android.model.Founder founderMe, com.android.model.Founder founderThem WHERE"
			+ " founderMe.accountName LIKE :founderName AND founderMe.list_key LIKE founderThem.list_key "
			+ "AND founderMe.accountName != founderThem.accountName")
	List<Founder> getFoundersWithMyList(@Param("founderName") String founderName);
	
	

}

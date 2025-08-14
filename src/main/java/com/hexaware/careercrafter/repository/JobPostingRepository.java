package com.hexaware.careercrafter.repository;

import com.hexaware.careercrafter.entities.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * Repository interface for job postings.
 */


@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {
	
	List<JobPosting> findByEmployerEmployerId(int employerId);
	List<JobPosting> findByActiveTrue();

}

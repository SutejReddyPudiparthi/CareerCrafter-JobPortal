package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Entity representing a JobListing is made my an employer
 * It contains job details
 * It is linked to an employer and also connected to applications
 */

@Entity
@Table(name = "job_listings")
public class JobListing {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int jobListingId;
	
	@ManyToOne
	@JoinColumn(name = "employer_id", nullable = false)
	private Employer employer;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	private String qualification;

	private String location;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private JobType jobType;

	@Column(updatable = false)
	private LocalDateTime postedDate;

	private LocalDateTime expiryDate;
	
	
	private boolean active = true;

	@OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL)
	private List<Application> applications;

	public enum JobType {
		FULL_TIME, PART_TIME, INTERNSHIP
	}
	
	public JobListing() {
		
	}

	public JobListing(int jobListingId, Employer employer, String title, String description, String qualification, String location,
			JobType jobType, LocalDateTime postedDate, LocalDateTime expiryDate, boolean active,
			List<Application> applications) {
		super();
		this.jobListingId = jobListingId;
		this.employer = employer;
		this.title = title;
		this.description = description;
		this.qualification = qualification;
		this.location = location;
		this.jobType = jobType;
		this.postedDate = postedDate;
		this.expiryDate = expiryDate;
		this.active = active;
		this.applications = applications;
	}

	public int getJobListingId() {
		return jobListingId;
	}

	public void setJobListingId(int jobListingId) {
		this.jobListingId = jobListingId;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public LocalDateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
}

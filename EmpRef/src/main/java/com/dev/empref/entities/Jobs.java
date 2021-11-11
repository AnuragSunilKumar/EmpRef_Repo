package com.dev.empref.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "jobs", uniqueConstraints = @UniqueConstraint(columnNames = "job_name"))
public class Jobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id;
	
	@Column(name = "job_name")
	private String jobName;
	
	@Column(name = "job_description")
	private String jobdescription;
	
	@Column(name = "job_exp")
	private String jobExp;
	
	@Column(name ="job_Contact")
	private String Contact;

    public Jobs() {
    }

    public Jobs(int id, String jobName, String jobdescription, String jobExp, String contact) {
        this.id = id;
        this.jobName = jobName;
        this.jobdescription = jobdescription;
        this.jobExp = jobExp;
        this.Contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getJobExp() {
        return jobExp;
    }

    public void setJobExp(String jobExp) {
        this.jobExp = jobExp;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        this.Contact = contact;
    }

   
    
}

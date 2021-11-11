package com.dev.empref.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "app_name"))
public class Applications {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String app_name;
    private String app_number;
    private String app_for;
    private String app_email;
    private String resume;

    public Applications(int id, String app_name, String app_number, String app_for, String app_email,
			String resume, User user) {
		super();
		this.id = id;
		this.app_name = app_name;
		this.app_number = app_number;
		this.app_for = app_for;
		this.app_email = app_email;
		this.resume = resume;
		this.user = user;
	}

    
    public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}


	@ManyToOne
    private User user;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Applications() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_number() {
        return app_number;
    }

    public void setApp_number(String app_number) {
        this.app_number = app_number;
    }

    public String getApp_for() {
        return app_for;
    }

    public void setApp_for(String app_for) {
        this.app_for = app_for;
    }

    public String getApp_email() {
        return app_email;
    }

    public void setApp_email(String app_email) {
        this.app_email = app_email;
    }

    

    

}
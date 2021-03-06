package com.dev.empref.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "app_name"))
public class Applications {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "app_name")
    private String appname;
    private String app_number;
    private String app_for;
    private String app_email;
    private String resume;

    public Applications(int id, String appname, String app_number, String app_for, String app_email,
			String resume, User user) {
		super();
		this.id = id;
		this.appname = appname;
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
	@JsonIgnore
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

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
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


	@Override
	public String toString() {
		return "Applications [id=" + id + ", app_name=" + appname + ", app_number=" + app_number + ", app_for="
				+ app_for + ", app_email=" + app_email + ", resume=" + resume + ", user=" + user + "]";
	}

    

    

}
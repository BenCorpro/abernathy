package com.abernathyclinic.patientinfo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.abernathyclinic.patientinfo.util.Gender;

@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 45)
	private String firstname;
	@Column(nullable = false, length = 45)
	private String lastname;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthdate;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	@Column(length = 255)
	private String address;
	@Column(length = 20)
	private String phone;
	
	
	public Patient() {
	}

	public Patient(String firstname, String lastname, Date birthdate, Gender gender, String address,
			String phone) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}

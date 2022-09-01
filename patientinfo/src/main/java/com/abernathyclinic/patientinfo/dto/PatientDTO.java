package com.abernathyclinic.patientinfo.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.abernathyclinic.patientinfo.util.Gender;

public class PatientDTO {

	private long id;
	@NotNull(message = "Le prénom doit être renseigné")
	@Pattern(regexp = "[-'a-zA-ZÀ-ÖØ-öø-ÿ\\s]{3,15}", message = "Le prénom doit être valide et comporter 3 caractères minimum")
	private String firstname;
	@NotNull(message = "Le nom doit être renseigné")
	@Pattern(regexp = "[-'a-zA-ZÀ-ÖØ-öø-ÿ\\\\s]{3,15}", message = "Le nom doit être valide et comporter 3 caractères minimum")
	private String lastname;
	@NotNull
	private Date birthdate;
	@NotNull
	private Gender gender;
	@Pattern(regexp = "[-'a-zA-ZÀ-ÖØ-öø-ÿ0-9\\s]{3,50}", message = "L'adresse ne doit contenir que lettres et chiffres")
	private String address;
	@Pattern(regexp = "[0-9-\\s]{3,12}", message = "Le numéro de téléphone ne doit contenir que des chiffres")
	private String phone;
	
	
	public PatientDTO() {
	}

	public PatientDTO(String firstname, String lastname, Date birthdate, Gender gender, String address, String phone) {
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

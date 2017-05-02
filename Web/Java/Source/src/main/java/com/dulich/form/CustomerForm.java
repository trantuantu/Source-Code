package com.dulich.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CustomerForm {

	private String Ma;
	
	@NotEmpty
	@Size(min=5, max=50)
	private String Name;
	
	@NotEmpty
	@Size(min=5, max=100)
	private String address;
	
	@NotEmpty
	private String phone;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{9,12}|^$")
	private String CMND;

	public String getMa() {
		return Ma;
	}

	public void setMa(String ma) {
		Ma = ma;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		CMND = cMND;
	}
	
	
}

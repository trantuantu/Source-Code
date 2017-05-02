package com.dulich.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AcceptRegistrationForm {

	private String key_registration;
	
	private String key_tour;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getKey_tour() {
		return key_tour;
	}
	public void setKey_tour(String key_tour) {
		this.key_tour = key_tour;
	}
	public String getKey_registration() {
		return key_registration;
	}
	public void setKey_registration(String key_registration) {
		this.key_registration = key_registration;
	}
	@NotEmpty
	@Size(min=5, max=50)
	private String name_user;
	
	@NotEmpty
	@Size(min=5, max=50)
	private String name_tour;
	
	@NotEmpty
	@Size(min=5, max=50)
	private String name_guides;
	
	@NotEmpty
	private int total_money;
	
	public int getPrices() {
		return prices;
	}
	public void setPrices(int prices) {
		this.prices = prices;
	}
	@NotEmpty
	private int prices;
	
	@NotEmpty
	private String date_start;
	
	@NotEmpty
	private int count_people;
	
	@NotEmpty
	private String note;
	
	
	
	public int getCount_people() {
		return count_people;
	}
	public void setCount_people(int count_people) {
		this.count_people = count_people;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@NotEmpty
	private String date_end;
	
	@NotEmpty
	private String phone_customer;
	
	@NotEmpty
	private int deposit;
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@NotEmpty
	private String introduce;
	
	
	public AcceptRegistrationForm() {
		super();
	}
	public String getName_user() {
		return name_user;
	}
	public void setName_user(String name_user) {
		this.name_user = name_user;
	}
	public String getName_tour() {
		return name_tour;
	}
	public void setName_tour(String name_tour) {
		this.name_tour = name_tour;
	}
	public String getName_guides() {
		return name_guides;
	}
	public void setName_guides(String name_guides) {
		this.name_guides = name_guides;
	}
	public int getTotal_money() {
		return total_money;
	}
	public void setTotal_money(int total_money) {
		this.total_money = total_money;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
//	public String getPhone() {
//		return phone_customer;
//	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getPhone_customer() {
		return phone_customer;
	}
	public void setPhone_customer(String phone_customer) {
		this.phone_customer = phone_customer;
	}
//	public void setPhone(String phone) {
//		this.phone_customer = phone;
//	}
	
}

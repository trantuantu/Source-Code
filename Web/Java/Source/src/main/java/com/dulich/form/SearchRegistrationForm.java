package com.dulich.form;

public class SearchRegistrationForm {
	
	private String key_registration;
	private int condition_tour;
	private int condition_user;
	private int  approval;
	private String input_condition_tour;
	private String input_condition_user;
	
	
	public SearchRegistrationForm() {
		super();
		
	}
	public SearchRegistrationForm(int approval,int condition_user,int condition_tour) {
		super();
		this.approval = approval;
		this.condition_user = condition_user;
		this.condition_tour = condition_tour;
	}
	public String getKey_registration() {
		return key_registration;
	}
	public void setKey_registration(String key_registration) {
		this.key_registration = key_registration;
	}
	public int getCondition_tour() {
		return condition_tour;
	}
	public void setCondition_tour(int condition_tour) {
		this.condition_tour = condition_tour;
	}
	public int getCondition_user() {
		return condition_user;
	}
	public void setCondition_user(int condition_user) {
		this.condition_user = condition_user;
	}
	public String getInput_condition_tour() {
		return input_condition_tour;
	}
	public void setInput_condition_tour(String input_condition_tour) {
		this.input_condition_tour = input_condition_tour;
	}
	
	public String getInput_condition_user() {
		return input_condition_user;
	}
	public void setInput_condition_user(String input_condition_user) {
		this.input_condition_user = input_condition_user;
	}
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}

	

}

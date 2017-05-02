package com.dulich.model.dao;



import java.util.List;

import com.dulich.form.AcceptRegistrationForm;
import com.dulich.form.HotelForm;
import com.dulich.form.SearchRegistrationForm;
import com.dulich.form.TourForm;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.RegistrationForm;

public interface RegistrationFormDAO {
	
	public void  edit(RegistrationForm registrationForm);
	public 	RegistrationForm getRegistration(String key);
	public List<RegistrationForm> getAllRegistration();
	public Boolean flagEnabledEditing(String key);
	public Boolean updateRegistration(AcceptRegistrationForm acceptRegistrationForm);
	public Boolean deleteRegistration(String id) ;
	
	public List<RegistrationForm> getListRegistrationConditional(SearchRegistrationForm searchRegistrationForm);
	
}

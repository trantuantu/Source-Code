package com.dulich.model.dao;

import com.dulich.form.CustomerForm;
import com.dulich.form.HotelForm;
import com.dulich.model.pojo.Customer;

public interface CustomerDAO {

	public Customer getCustomerByKey(String iduser) ;
	public Boolean updateCustomer(CustomerForm customerForm) ; 
}

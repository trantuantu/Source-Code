package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.CustomerForm;
import com.dulich.form.HotelForm;
import com.dulich.model.pojo.Customer;
import com.dulich.model.pojo.Hotel;

@Transactional
public class CustomerDAOImpl implements CustomerDAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	
	public Customer getCustomerByKey(String iduser) {
		Customer uresult = null;
		if(!iduser.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select customer from Customer customer where co = '0' and ma = '" + iduser + "'" ;
				System.out.println(sql);
				List<Customer> list = session.createQuery(sql).list();
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	public Boolean updateCustomer(CustomerForm customerForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = (Customer) session.get(Customer.class, Integer.parseInt(customerForm.getMa()));
			if(customer.isFlag() == false){
				customer.setAddress(customerForm.getAddress());
				customer.setCMND(customerForm.getCMND());
				customer.setName(customerForm.getName());
				customer.setPhone(customerForm.getPhone());
				session.update(customer);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}

}

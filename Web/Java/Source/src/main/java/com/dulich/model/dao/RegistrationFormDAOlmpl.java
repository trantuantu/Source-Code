package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.AcceptRegistrationForm;
import com.dulich.form.SearchRegistrationForm;
import com.dulich.model.pojo.Customer;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.RegistrationForm;
import com.dulich.model.pojo.Tour;

@Transactional
public class RegistrationFormDAOlmpl implements RegistrationFormDAO {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	public void edit(RegistrationForm registrationForm) {
		sessionFactory.getCurrentSession().update(registrationForm);

	}

	
	public RegistrationForm getRegistration(String key) {

		RegistrationForm registrationresult = null;
		if(!key.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select registrationForm from RegistrationForm registrationForm where co = '0' and ma = '" + key + "'" ;
				List<RegistrationForm> list = session.createQuery(sql).list();
				registrationresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return registrationresult;
		
	}

	@SuppressWarnings("unchecked")
	public List<RegistrationForm> getAllRegistration() {
		List<RegistrationForm> list = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
            String sql = "select registrationForm from RegistrationForm registrationForm where co = '0' and status <= '2' ";
			list = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
		return list;
	}
	
	public Boolean flagEnabledEditing(String key){
		Boolean rs = false;
		if(!key.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				RegistrationForm registrationForm = (RegistrationForm) session.get(RegistrationForm.class, Integer.parseInt(key));
				if(registrationForm.getIs_flag() == 0 && registrationForm.getIs_edit_tour() == 1){
					registrationForm.setIs_edit_tour(2);
					session.update(registrationForm);
					rs = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
		        return false;
			} 
		}
		return rs;
	}
	
	public Boolean updateRegistration(AcceptRegistrationForm acceptRegistrationForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			RegistrationForm registrationForm = (RegistrationForm) session.get(RegistrationForm.class, Integer.parseInt(acceptRegistrationForm.getKey_registration()));
			if(registrationForm.getIs_flag() == 0){
				int deposit = acceptRegistrationForm.getDeposit();
				int total_money =  acceptRegistrationForm.getTotal_money();
				if(deposit== total_money)
					registrationForm.setStatus(2);
				else
					registrationForm.setStatus(1);
				registrationForm.setDeposit(deposit);
				registrationForm.setTotal_money(total_money);
				session.update(registrationForm);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	/*@SuppressWarnings("unchecked")
	public List<RegistrationForm> getListRegistrationConditional(SearchRegistrationForm searchRegistrationForm){
		List<RegistrationForm> list = null;
		if(searchRegistrationForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			try {
				String sql = "select registrationForm from RegistrationForm registrationForm where co = '0' ";
				if(String.valueOf(searchRegistrationForm.getApproval()) !=  "" && !String.valueOf(searchRegistrationForm.getApproval()).equals("2")){
					if(String.valueOf(searchRegistrationForm.getApproval()).equals("0"))
                        sql += "and " + "trang_thai < '2' ";
					else
                        sql += "and " + "trang_thai = '2' ";
				}
				if(searchRegistrationForm.getInput_condition_user() != null &&  !searchRegistrationForm.getInput_condition_user().equals("")){
					String sql_temp = "select customer from Customer customer where co = '0'";
					if(searchRegistrationForm.getCondition_user()== 0){
						sql_temp += "and " +  "ma = '" + searchRegistrationForm.getInput_condition_user() + "'" ;
					}else{
						sql_temp += "and " +  "ten like '%" + searchRegistrationForm.getInput_condition_user() + "%'" ;
					}
					sql += "and " +   "ma in (" + sql_temp + ")"  ;
				}
				if(searchRegistrationForm.getInput_condition_tour()!= null &&  !searchRegistrationForm.getInput_condition_tour().equals("")){
					String sql_temp = "select tour from Tour tour where co = '0'";
					if(searchRegistrationForm.getCondition_tour()== 0){
						sql_temp += "and " +  "ma = '" + searchRegistrationForm.getInput_condition_tour() + "'" ;
					}else{
						sql_temp += "and " +  "ten like '%" + searchRegistrationForm.getInput_condition_tour() + "%'" ;
					}
					sql += "and " +   "ma in (" + sql_temp + ")"  ;
	
				}
				System.out.println(sql);
				list = session.createQuery(sql).list();
			}catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
			
		}
		
		return list;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<RegistrationForm> getListRegistrationConditional(SearchRegistrationForm searchRegistrationForm){
		List<RegistrationForm> list = null;
		if(searchRegistrationForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			try {
				String sql = "select registrationForm from RegistrationForm registrationForm where co = '0' ";
				if(String.valueOf(searchRegistrationForm.getApproval()) !=  "" && !String.valueOf(searchRegistrationForm.getApproval()).equals("2")){
					if(String.valueOf(searchRegistrationForm.getApproval()).equals("0"))
                        sql += "and " + "trang_thai < '2' ";
					else
                        sql += "and " + "trang_thai = '2' ";
				}
				if(searchRegistrationForm.getInput_condition_user() != null &&  !searchRegistrationForm.getInput_condition_user().equals("")){
					String sql_temp = "select customer from Customer customer where co = '0'";
					if(searchRegistrationForm.getCondition_user()== 0){
						sql_temp += "and " +  "ma = '" + searchRegistrationForm.getInput_condition_user() + "'" ;
					}else{
						sql_temp += "and " +  "ten like '%" + searchRegistrationForm.getInput_condition_user() + "%'" ;
					}
					sql += "and " +   "ma_khach_hang in (" + sql_temp + ")"  ;
				}
				if(searchRegistrationForm.getInput_condition_tour()!= null &&  !searchRegistrationForm.getInput_condition_tour().equals("")){
					String sql_temp = "select tour from Tour tour where co = '0'";
					if(searchRegistrationForm.getCondition_tour()== 0){
						sql_temp += "and " +  "ma = '" + searchRegistrationForm.getInput_condition_tour() + "'" ;
					}else{
						sql_temp += "and " +  "ten like '%" + searchRegistrationForm.getInput_condition_tour() + "%'" ;
					}
					sql += "and " +   "ma_tour in (" + sql_temp + ")"  ;
	
				}
				System.out.println(sql);
				list = session.createQuery(sql).list();
			}catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
			
		}
		
		return list;
	}
	
	public Boolean deleteRegistration(String id){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			RegistrationForm registrationForm = (RegistrationForm) session.get(RegistrationForm.class, Integer.parseInt(id));
			registrationForm.setIs_flag(1);
			session.update(registrationForm);
			session.flush();
			Tour tour = (Tour) session.get(Tour.class, registrationForm.getKey_tour());
			int ve_temp = tour.getSo_ve_con_lai(); 
			tour.setSo_ve_con_lai(ve_temp + registrationForm.getCount_people());
			session.update(tour);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}

}

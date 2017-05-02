package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.KhachHangForm;
import com.dulich.model.pojo.KhachHang;
import com.dulich.model.pojo.User;


@Transactional
public class KhachHangDAOImpl implements KhachHangDAO {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")	
	public KhachHang getKhachHang(String id){
		KhachHang uresult = null;
		if(!id.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select k from KhachHang k where co = '0' and ma = '" + id + "'" ;
				List<KhachHang> list = session.createQuery(sql).list();
				
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
				
	}
	
	public Integer create_Web(KhachHang user) {
		Integer result = getIdByCmnd_Web(user.getCMND());
		if (result > 0)
			return result;
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			result = (Integer)session.save(user);	
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}	
		
		return result;
	}

	public Integer getIdByCmnd_Web(String cmnd) {
		Session session = this.sessionFactory.getCurrentSession();

		Integer result = -1;
		try {
			String sql = "select user.Ma from KhachHang user where user.CMND =" + cmnd;
			Query query = session.createQuery(sql);
			if (query.list().size() > 0)
				result = (Integer) query.list().get(0);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}	
		
		return result;
	}
}

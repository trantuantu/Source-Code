package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.RestaurantForm;
import com.dulich.model.pojo.Restaurant;

@Transactional
public class RestaurantDAOImpl implements RestaurantDAO{
private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> getListRestaurant(RestaurantForm restaurantForm){
		List<Restaurant> list = null;
		if(restaurantForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select restaurant from Restaurant restaurant where co = '0' ";
				if(String.valueOf(restaurantForm.getLoai()) !=  "" && !String.valueOf(restaurantForm.getLoai()).equals("4")){
					sql += "and " + "restaurant.Loai = '" + String.valueOf(restaurantForm.getLoai()) + "' ";
				}
				if(restaurantForm.getMa() != null && !restaurantForm.getMa().toString().equals("")){
					sql += "and " + "restaurant.Ma = '" + restaurantForm.getMa().toString() + "	' ";
				}
				if(restaurantForm.getTen() != null && !restaurantForm.getTen().equals("")){
					sql += "and " + "restaurant.Ten like '%" + restaurantForm.getTen() + "%' ";
				}
				if(restaurantForm.getDiaChi()!= null && !restaurantForm.getDiaChi().equals("")){
					sql += "and " + "restaurant.DiaChi like '%" + restaurantForm.getDiaChi() + "%' ";
				}
				list = session.createQuery(sql).list();
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Boolean deleteRestaurant(String idRestaurant){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Restaurant restaurant = (Restaurant) session.get(Restaurant.class, Integer.parseInt(idRestaurant));
			restaurant.setCo(true);
			session.update(restaurant);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public Restaurant getRestaurantByMa(String idRestaurant) {
		Restaurant uresult = null;
		if(!idRestaurant.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select restaurant from Restaurant restaurant where co = '0' and ma = '" + idRestaurant + "'" ;
				List<Restaurant> list = session.createQuery(sql).list();
				
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	public Boolean updateRestaurant(RestaurantForm restaurantForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Restaurant restaurant = (Restaurant) session.get(Restaurant.class, Integer.parseInt(restaurantForm.getMa()));
			if(restaurant.isCo() == false){
				restaurant.setDiaChi(restaurantForm.getDiaChi());
				restaurant.setLoai(String.valueOf(restaurantForm.getLoai()));
				restaurant.setTen(restaurantForm.getTen());
				session.update(restaurant);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public String addRestaurant(RestaurantForm restaurantForm) {
		String rs = "";
		Session session = this.sessionFactory.getCurrentSession();
		try {

			Restaurant restaurant = new Restaurant();
			restaurant.setDiaChi(restaurantForm.getDiaChi());
			restaurant.setLoai(String.valueOf((restaurantForm.getLoai())));
			restaurant.setTen(restaurantForm.getTen());
			session.save(restaurant);
			session.flush();
			rs = "1";
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return "Lỗi database";
		} 
		return rs;
	}
	
    
    @SuppressWarnings("unchecked")
       public List<Restaurant> getListRestaurantbyString(String str){
            
            Session session = this.sessionFactory.getCurrentSession();
               
            List<Restaurant> result = null;
            try {
                String sql = "select restaurant from Restaurant restaurant where co = '0' and  restaurant.DiaChi like '%"+ str +"%'";
                Query query = session.createQuery(sql);
                result = query.list();
            }
            catch (Exception ex) {
                System.out.println(ex);
                session.getTransaction().rollback();
            }

            return result;
        }
    public List<Restaurant> getAllListRestaurant(){
        List<Restaurant> list = null;
           Session session = this.sessionFactory.getCurrentSession();
           try {
               String sql = "select restaurant from Restaurant restaurant where co = '0' ";
               list = session.createQuery(sql).list();
           } catch (Exception e) {
               e.printStackTrace();
               session.getTransaction().rollback();
           }
           
           return list;
    }
}

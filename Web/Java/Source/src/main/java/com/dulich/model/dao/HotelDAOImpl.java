package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.HotelForm;
import com.dulich.form.UserForm;
import com.dulich.model.pojo.Hotel;
import com.dulich.model.pojo.User;


@Transactional
public class HotelDAOImpl implements HotelDAO{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Hotel> getListHotel(HotelForm hotelForm){
		List<Hotel> list = null;
		if(hotelForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			try {
				String sql = "select hotel from Hotel hotel where co = '0' ";
				if(String.valueOf(hotelForm.getLoai()) !=  "" && !String.valueOf(hotelForm.getLoai()).equals("6")){
					sql += "and " + "hotel.Loai = '" + String.valueOf(hotelForm.getLoai()) + "' ";
				}
				if(hotelForm.getMa() != null && !hotelForm.getMa().toString().equals("")){
					sql += "and " + "hotel.Ma = '" + hotelForm.getMa().toString() + "	' ";
				}
				if(hotelForm.getTen() != null && !hotelForm.getTen().equals("")){
					sql += "and " + "hotel.Ten like '%" + hotelForm.getTen() + "%' ";
				}
				if(hotelForm.getDiaChi()!= null && !hotelForm.getDiaChi().equals("")){
					sql += "and " + "hotel.DiaChi like '%" + hotelForm.getDiaChi() + "%' ";
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
	public Boolean deleteHotel(String idHotel){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Hotel hotel = (Hotel) session.get(Hotel.class, Integer.parseInt(idHotel));
			hotel.setCo(true);
			session.update(hotel);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public Hotel getHotelByMa(String idHotel) {
		Hotel uresult = null;
		if(!idHotel.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select hotel from Hotel hotel where co = '0' and ma = '" + idHotel + "'" ;
				List<Hotel> list = session.createQuery(sql).list();
				
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	public Boolean updateHotel(HotelForm hotelForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Hotel hotel = (Hotel) session.get(Hotel.class, Integer.parseInt(hotelForm.getMa()));
			if(hotel.isCo() == false){
				hotel.setDiaChi(hotelForm.getDiaChi());
				hotel.setLoai(String.valueOf(hotelForm.getLoai()));
				hotel.setTen(hotelForm.getTen());
				session.update(hotel);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public String addHotel(HotelForm hotelForm) {
		String rs = "";
		Session session = this.sessionFactory.getCurrentSession();
		try {

			Hotel hotel = new Hotel();
			hotel.setDiaChi(hotelForm.getDiaChi());
			hotel.setLoai(String.valueOf((hotelForm.getLoai())));
			hotel.setTen(hotelForm.getTen());
			session.save(hotel);
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
   public List<Hotel> getListHotelbyString(String str){
        
        Session session = this.sessionFactory.getCurrentSession();
           
        List<Hotel> result = null;
        try {
            String sql = "select hotel from Hotel hotel where co = '0' and  hotel.DiaChi like '%"+ str +"%'";
            Query query = session.createQuery(sql);
            result = query.list();
        }
        catch (Exception ex) {
            System.out.println(ex);
            session.getTransaction().rollback();
        }

        return result;
    }
    
    public List<Hotel> getAllListHotel(){
        List<Hotel> list = null;
           Session session = this.sessionFactory.getCurrentSession();
           try {
               String sql = "select hotel from Hotel hotel where co = '0' ";
               list = session.createQuery(sql).list();
           } catch (Exception e) {
               e.printStackTrace();
               session.getTransaction().rollback();
           }
           
           return list;
    }
   

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

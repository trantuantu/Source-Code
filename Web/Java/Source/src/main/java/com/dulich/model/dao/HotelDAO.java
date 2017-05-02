package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.form.HotelForm;
import com.dulich.model.pojo.Hotel;


public interface HotelDAO {

	 public List<Hotel> getListHotel(HotelForm hotelForm) ;
	 public Hotel getHotelByMa(String idHotel) ;
	 public Boolean deleteHotel(String id) ;
	 public Boolean updateHotel(HotelForm hotelForm) ; 
	 public String addHotel(HotelForm hotelForm) ; 
     public List<Hotel> getListHotelbyString(String str);
     public List<Hotel> getAllListHotel();
}

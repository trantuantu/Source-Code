package com.dulich.model.dao;

import java.util.List;

import com.dulich.form.RestaurantForm;
import com.dulich.model.pojo.Restaurant;

public interface RestaurantDAO {
	 public List<Restaurant> getListRestaurant(RestaurantForm restaurantForm) ;
	 public Restaurant getRestaurantByMa(String idRestaurant) ;
	 public Boolean deleteRestaurant(String id) ;
	 public Boolean updateRestaurant(RestaurantForm restaurantForm) ; 
	 public String addRestaurant(RestaurantForm restaurantForm) ; 
     public List<Restaurant> getListRestaurantbyString(String str);
     public List<Restaurant> getAllListRestaurant();
}

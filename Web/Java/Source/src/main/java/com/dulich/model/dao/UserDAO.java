package com.dulich.model.dao;

import java.util.List;

import com.dulich.form.NghiPhepForm;
import com.dulich.form.UserForm;
import com.dulich.model.pojo.NghiPhep;
import com.dulich.model.pojo.User;

public interface UserDAO {

	 public List<User> getListUser(UserForm userForm) ;
	 public List<User> getListUserDuyetNghiPhep(UserForm userForm) ;
	 public User getUserByMa(String idUer) ;
	 public Boolean deleteUser(String id) ;
	 public Boolean updateUser(UserForm userForm) ; 
	 public String addUser(UserForm userForm) ; 
	 public Boolean resetPassword(String id) ;
	 public List<NghiPhep> getNNPByIdNV(String idUer, int type) ; 
	 public Boolean duyetNNPByListID(String[] listId) ; 
	 public Boolean suaNNP(String id, String ngNghi, String ngLamLai) ; 
	 public Boolean xoaNNPByListID(String[] listId) ; 
	 public Boolean themNNP(String userId, String ngNghi, String ngLam) ;
	 public List<NghiPhep> getListNghiPhepByIdUser(String id);
	 public void deleteNghiPhepById(String idNghiPhep);
	 public User getUserByTaiKhoan(String username); 
}

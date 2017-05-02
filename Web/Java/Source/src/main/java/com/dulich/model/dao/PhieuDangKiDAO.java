package com.dulich.model.dao;

import java.util.List;

import com.dulich.form.PhieuDangKiForm;
import com.dulich.model.pojo.PhieuDangKi;


public interface PhieuDangKiDAO {
	
	 public List<PhieuDangKiForm> getListPhieuDangKi(PhieuDangKiForm phieuDangKiForm) ;
	 public boolean duyetPhieuDangKi(int id, int stutus) ;
	 public void create_Web(PhieuDangKi booking);
}

package com.dulich.model.dao;

import java.util.List;

import com.dulich.form.KhachHangForm;
import com.dulich.model.pojo.KhachHang;

public interface KhachHangDAO {

	public KhachHang getKhachHang(String id);
	
	public Integer create_Web(KhachHang user);
	
	public Integer getIdByCmnd_Web(String cmnd);	
}

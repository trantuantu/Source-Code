package com.dulich.model.dao;

import java.util.List;

import com.dulich.model.pojo.DanhSachDiaDiem;

public interface DanhSachDiaDiemDAO {
	public List<DanhSachDiaDiem> getListByTourId_Web(String id);
	public Boolean addDsDiaDiem(String key,int key_tour) ; 
}

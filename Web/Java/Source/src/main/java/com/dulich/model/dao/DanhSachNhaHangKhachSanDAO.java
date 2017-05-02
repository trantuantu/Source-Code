package com.dulich.model.dao;

import java.util.List;

import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DanhSachNhaHangKhachSan;
import com.dulich.model.pojo.Hotel;

public interface DanhSachNhaHangKhachSanDAO {
    
    public List<DanhSachNhaHangKhachSan> getListByPlaceid(String id);
    public Boolean addDsNhahangKhachsan(String listIdhotel,String listIdRestaurant,String key_place) ; 

}
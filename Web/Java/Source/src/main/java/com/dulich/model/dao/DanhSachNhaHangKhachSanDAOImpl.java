package com.dulich.model.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.model.pojo.DSNHKHKey;
import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DanhSachNhaHangKhachSan;
import com.dulich.model.pojo.Hotel;

@Transactional
public class DanhSachNhaHangKhachSanDAOImpl implements DanhSachNhaHangKhachSanDAO {
    
    private SessionFactory sessionFactory;
     
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public Boolean addDsNhahangKhachsan(String listIdhotel,String listIdRestaurant,String key_place){
        Boolean rs = false;
        Session session = this.sessionFactory.getCurrentSession();
        String[] parts = key_place.split(",");
        
        JSONArray jsonarrayhotel = new JSONArray(listIdhotel);
        JSONArray jsonarrayrestaurant = new JSONArray(listIdRestaurant);
        
        for(int i = 0; i< parts.length; i++){
            ArrayList<Integer> idhotel = new ArrayList<>();
            ArrayList<Integer> idrestaurant = new ArrayList<>();
            for(int j = 0; j <jsonarrayhotel.length(); j++){
                 JSONObject jsonobject = jsonarrayhotel.getJSONObject(j);
                 if(jsonobject.getInt("idplace") == Integer.parseInt(parts[i])){
                     idhotel.add(jsonobject.getInt("idhotel"));
                 }
            }
            for(int k = 0 ; k<jsonarrayrestaurant.length(); k++){
                 JSONObject jsonobject = jsonarrayrestaurant.getJSONObject(k);
                 if(jsonobject.getInt("idplace") == Integer.parseInt(parts[i])){
                     idrestaurant.add(jsonobject.getInt("idrestaurant"));
                 }
            }
            int max = idhotel.size() >= idrestaurant.size() ? idhotel.size(): idrestaurant.size();
            
            for(int h = 0 ; h< max; h++){
                DSNHKHKey nDsnhkhKey = new DSNHKHKey();
                if(idrestaurant.size()>h)
                    nDsnhkhKey.setMa_nha_hang(idrestaurant.get(h));
                if(idhotel.size()>h)
                    nDsnhkhKey.setMa_khach_san(idhotel.get(h));
                nDsnhkhKey.setMa_dia_diem(Integer.parseInt(parts[i]));
                try {
                    DanhSachNhaHangKhachSan danhSachNhaHangKhachSan = new DanhSachNhaHangKhachSan();
                    danhSachNhaHangKhachSan.setCo(0);
                    danhSachNhaHangKhachSan.setKey(nDsnhkhKey);
                    session.save(danhSachNhaHangKhachSan);
                    session.flush();
                    rs = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    session.getTransaction().rollback();
                    return false;
                } 
            }
        }
        return rs;
    }
    
    @SuppressWarnings("unchecked")
    public List<DanhSachNhaHangKhachSan> getListByPlaceid(String id){
        List<DanhSachNhaHangKhachSan> list = new ArrayList<>();
        Session session = this.sessionFactory.getCurrentSession();
        
        try {
            String sql = "select dsnhks from DanhSachNhaHangKhachSan dsnhks where dsnhks.key.ma_dia_diem = " + id;
            Query query = session.createQuery(sql);
            list = query.list();
        }
        catch (Exception ex) {
            System.out.println(ex);
            session.getTransaction().rollback();
        }
        
        return list;
        
    }
    

}
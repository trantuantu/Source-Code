package com.dulich.model.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dulich.model.pojo.DSDDKey;
import com.dulich.model.pojo.DanhSachDiaDiem;
import com.dulich.model.pojo.DiaDiemDuLich;

@Transactional
public class DanhSachDiaDiemDAOImpl implements DanhSachDiaDiemDAO{

	private SessionFactory sessionFactory;
	 
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<DanhSachDiaDiem> getListByTourId_Web(String id) {
		Session session = this.sessionFactory.getCurrentSession();

		List<DanhSachDiaDiem> result = null;
		try {
			String sql = "select dsdd from DanhSachDiaDiem dsdd where dsdd.key.ma_tour = " + id;
			Query query = session.createQuery(sql);
			result = query.list();
			
			Comparator<DanhSachDiaDiem> comp = new Comparator<DanhSachDiaDiem>() {
				public int compare(DanhSachDiaDiem a, DanhSachDiaDiem b) {
				    return a.getThu_tu().compareTo(b.getThu_tu());
				}
			};

			Collections.sort(result, comp);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		
		return result;
	}
	public Boolean addDsDiaDiem(String key,int key_tour){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		String[] parts = key.split(",");
		for(int i = 0 ; i< parts.length; i++){
			DSDDKey dskey = new DSDDKey();
			dskey.setMa_dia_diem(Integer.parseInt(parts[i]));
			dskey.setMa_tour(key_tour);
			try {
				DanhSachDiaDiem danhSachDiaDiem = new DanhSachDiaDiem();
				danhSachDiaDiem.setCo(0);
				danhSachDiaDiem.setKey(dskey);
				danhSachDiaDiem.setThu_tu(i+1);
				session.save(danhSachDiaDiem);
				session.flush();
				rs = true;
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
		        return false;
			} 
		}
		return rs;
		
	}
}

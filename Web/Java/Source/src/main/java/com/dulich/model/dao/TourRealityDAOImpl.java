package com.dulich.model.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import com.dulich.form.TourForm;
import com.dulich.model.pojo.PhieuDangKi;
import com.dulich.model.pojo.TourThucTe;

@Transactional
public class TourRealityDAOImpl implements TourRealityDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}

	
	public String createTourReality(TourForm tourForm) {
		String rs = "";
		Session session = this.sessionFactory.getCurrentSession();
		PhieuDangKi phieuDangKi = (PhieuDangKi) session.get(PhieuDangKi.class, Integer.parseInt(tourForm.getMa_phieu_dang_ki()));
		int so_nguoi = phieuDangKi.getSLNguoi();
		session.flush();
		try {

			TourThucTe tourReality = new TourThucTe();
			tourReality.setChuong_trinh(tourForm.getChuong_trinh());
			tourReality.setTen(tourForm.getTen());
			tourReality.setMa_tour_goc(tourForm.getMa_tour_goc());
			tourReality.setPhuong_tien(tourForm.getPhuong_tien());
			tourReality.setNgay_di(tourForm.getNgay_di());
			tourReality.setNgay_ve(tourForm.getNgay_ve());
			tourReality.setGioi_thieu(tourForm.getGioi_thieu());
			tourReality.setSo_ve(so_nguoi);
			tourReality.setSo_ve_con_lai(0);
			tourReality.setGia(tourForm.getGia());
			//tourReality.set
			//tourReality.setMa_dang_ky(tourForm.getMa_phieu_dang_ki());
			session.save(tourReality);
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
	public TourThucTe getTourThucTeByKey(String key_tour_original) {
		TourThucTe tourReality = null;
		if(!key_tour_original.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				
				String sql = "select tourReality from TourThucTe tourReality where co = '0' and ma_tour_goc = '" + key_tour_original + "'" ;
				System.out.println(sql);
				List<TourThucTe> tourRealitytemp =  session.createQuery(sql).list();
				tourReality = (tourRealitytemp.size() == 0)? null : tourRealitytemp.get(0);
				
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		
		return tourReality;
	}
	
	public Boolean updateTourReality(TourForm tourForm){
		Boolean rs = false;
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			TourThucTe tourReality = (TourThucTe) session.get(TourThucTe.class, tourForm.getMa_tour_goc());
			if(tourReality.getCo() == false){
				//System.out.println(tourForm.getProgram());
				tourReality.setNgay_di(tourForm.getNgay_di());
				tourReality.setNgay_ve(tourForm.getNgay_ve());
				tourReality.setChuong_trinh(tourForm.getChuong_trinh());
				tourReality.setGia(tourForm.getGia());
				//tourReality.setGioi_thieu(tourForm.getGioi_thieu());
				tourReality.setPhuong_tien(tourForm.getPhuong_tien());
				tourReality.setTen(tourForm.getTen());
				
				session.update(tourReality);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	public TourThucTe getTourThucTebyKeyThucte(String key_tour_reality){
		TourThucTe tourReality = null;
		if(!key_tour_reality.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				
				String sql = "select tourReality from TourThucTe tourReality where co = '0' and ma = '" + key_tour_reality + "'" ;
				System.out.println(sql);
				List<TourThucTe> tourRealitytemp =  session.createQuery(sql).list();
				tourReality = (tourRealitytemp.size() == 0)? null : tourRealitytemp.get(0);
				
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		
		return tourReality;
	}

}

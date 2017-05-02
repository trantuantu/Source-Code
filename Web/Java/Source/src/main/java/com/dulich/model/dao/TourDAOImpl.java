package com.dulich.model.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dulich.form.ChiPhiThucTeForm;
import com.dulich.form.PhieuDangKiForm;
import com.dulich.form.StatictisForm;
import com.dulich.form.TourForm;
import com.dulich.model.pojo.ChiPhiThucTe;
import com.dulich.model.pojo.KhachHang;
import com.dulich.model.pojo.PhieuDangKi;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.TourThucTe;
import com.dulich.model.pojo.User;

@Transactional
public class TourDAOImpl implements TourDAO{
	private SessionFactory sessionFactory;
	 
	private List<String> listTourInMonth_ma = new ArrayList<String>();
	private List<String> listTourInMonth_ten = new ArrayList<String>();
	private List<String> listTourInMonth_ngVe = new ArrayList<String>();
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Tour> getListTour() {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Tour> result = null;
		try {
			String sql = "select tour from Tour tour where co = '0' ";
			Query query = session.createQuery(sql);
			result = query.list();
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

	    return result;
	}

	public List<PhieuDangKi> getListPDKByID(String idTour, Boolean isTT){
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PhieuDangKi> result = null;
		try {
			String sql = "select tour from PhieuDangKi tour where Co = '0' and MaTour = '" + idTour + "'";
			if(isTT == true){
				sql += " and (ChinhSua = '1' or ChinhSua = '2') ";
			}else{
				sql += " and ChinhSua = '0'";
			}
			Query query = session.createQuery(sql);
			result = query.list();
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

	    return result;
	}
	
	@SuppressWarnings("unchecked")	
	public TourForm getTourByIDOfPDK(String idPDK){
		TourForm uresult = null;
		if(!idPDK.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select k from PhieuDangKi k where co = '0' and ma = '" + idPDK + "'" ;
				List<PhieuDangKi> list = session.createQuery(sql).list();
				
				if(list.size() != 0){
					PhieuDangKi temp = list.get(0);
					if(temp.isChinhSua() == true){
						sql = "select k from TourThucTe k where co = '0' and ma_tour_goc = '" + temp.getMaTour() + "'" ;
						TourThucTe ttt = (TourThucTe)session.createQuery(sql).list().get(0);
						uresult = this.convertTourTTToTourForm(ttt);
					}else{
						sql = "select k from Tour k where co = '0' and ma = '" + temp.getMaTour() + "'" ;
						Tour ttt = (Tour)session.createQuery(sql).list().get(0);
						uresult = this.convertTourToTourForm(ttt);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	@SuppressWarnings("unchecked")	
	public TourForm getTourById(String id){
		TourForm uresult = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			String sql = "" ;

			sql = "select k from TourThucTe k where co = '0' and ma_tour_goc = '" + id + "'" ;
			List<Object> rs = session.createQuery(sql).list();
			if(!rs.isEmpty()){
				TourThucTe ttt = (TourThucTe)rs.get(0);
				uresult = this.convertTourTTToTourForm(ttt);
			}else{
				sql = "select k from Tour k where co = '0' and ma = '" + id + "'" ;
				Tour ttt = (Tour)session.createQuery(sql).list().get(0);
				uresult = this.convertTourToTourForm(ttt);
			}
				 
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
		return uresult;
	}

	public StatictisForm thongKeSLTourTheoNam_Thang(String year){
		listTourInMonth_ma.clear();
		listTourInMonth_ten.clear();
		listTourInMonth_ngVe.clear();
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Tour> listTour = null;
		List<TourThucTe> listTourTT = null;
		try {
			String sql = "select t from Tour t, TourThucTe ttt where t.co = '0' and ttt.co = '0' and t.ma != ttt.ma_tour_goc";
			Query query = session.createQuery(sql);
			listTour = query.list();
			if(listTour != null){
				for(int i = 0; i < listTour.size(); i++){
					String ngVe = listTour.get(i).getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						rs.getSLTour_thang()[ngVeInt-1] += 1;
						listTourInMonth_ma.add(listTour.get(i).getMa().toString());
						listTourInMonth_ten.add(listTour.get(i).getTen());
						listTourInMonth_ngVe.add(listTour.get(i).getNgay_ve());
					}
				}
			}
			//////////////////
			sql = "select ttt from TourThucTe ttt where  ttt.co = '0' ";
			query = session.createQuery(sql);
			listTourTT = query.list();
			if(listTourTT != null){
				for(int i = 0; i < listTourTT.size(); i++){
					String ngVe = listTourTT.get(i).getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						rs.getSLTour_thang()[ngVeInt-1] += 1;
						listTourInMonth_ma.add(listTourTT.get(i).getMa().toString());
						listTourInMonth_ten.add(listTourTT.get(i).getTen());
						listTourInMonth_ngVe.add(listTourTT.get(i).getNgay_ve());
					}
				}
			}

		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeSLTourTheoNam_Qui(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Tour> listTour = null;
		List<TourThucTe> listTourTT = null;
		try {
			String sql = "select t from Tour t, TourThucTe ttt where t.co = '0' and ttt.co = '0' and t.ma != ttt.ma_tour_goc";
			Query query = session.createQuery(sql);
			listTour = query.list();
			if(listTour != null){
				for(int i = 0; i < listTour.size(); i++){
					String ngVe = listTour.get(i).getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						int index = (ngVeInt == 1 || ngVeInt == 2 || ngVeInt == 3 )? 0 :
							(ngVeInt == 4 || ngVeInt == 5 || ngVeInt == 6 )? 1 :
							(ngVeInt == 7 || ngVeInt == 8 || ngVeInt == 9 )? 2 :3;
						rs.getSLTour_qui()[index] += 1;
					}
				}
			}
			//////////////////
			sql = "select ttt from TourThucTe ttt where  ttt.co = '0' ";
			query = session.createQuery(sql);
			listTourTT = query.list();
			if(listTourTT != null){
				for(int i = 0; i < listTourTT.size(); i++){
					String ngVe = listTourTT.get(i).getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						int index = (ngVeInt == 1 || ngVeInt == 2 || ngVeInt == 3 )? 0 :
							(ngVeInt == 4 || ngVeInt == 5 || ngVeInt == 6 )? 1 :
							(ngVeInt == 7 || ngVeInt == 8 || ngVeInt == 9 )? 2 :3;
						rs.getSLTour_qui()[index] += 1;
					}
				}
			}

		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}

	public StatictisForm thongKeSLTourTheoNhieuNam(String startYear, String endYear){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		int startY = Integer.parseInt(startYear);
		int endY = Integer.parseInt(endYear);
		List<Tour> listTour = null;
		List<TourThucTe> listTourTT = null;
		try {
			String sql = "select t from Tour t, TourThucTe ttt where t.co = '0' and ttt.co = '0' and t.ma != ttt.ma_tour_goc";
			Query query = session.createQuery(sql);
			listTour = query.list();
			if(listTour != null){
				for(int i = 0; i < listTour.size(); i++){
					String ngVe = listTour.get(i).getNgay_ve();
					ngVe = ngVe.split("/")[2];
					int ngVeInt = Integer.parseInt(ngVe);
					if(ngVeInt >= startY && ngVeInt <= endY){
						int index = ngVeInt - startY;
						rs.getSLTour_nhieunam()[index] += 1;
					}
				}
			}
			//////////////////
			sql = "select ttt from TourThucTe ttt where  ttt.co = '0' ";
			query = session.createQuery(sql);
			listTourTT = query.list();
			if(listTourTT != null){
				for(int i = 0; i < listTourTT.size(); i++){
					String ngVe = listTourTT.get(i).getNgay_ve();
					ngVe = ngVe.split("/")[2];
					int ngVeInt = Integer.parseInt(ngVe);
					if(ngVeInt >= startY && ngVeInt <= endY){
						int index = ngVeInt - startY;
						rs.getSLTour_nhieunam()[index] += 1;
					}
				}
			}
			for(int i = startY, j = 0; i <= endY; i++, j++){
				rs.getNhieunam()[j] = String.valueOf(i);
			}

		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeDoanhThuTheoNam_Thang(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3 ";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						rs.getDoanhThu_thang()[ngVeInt-1] += listPDK.get(i).getTongTien();
					}
				}
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeDoanhThuTheoNam_Qui(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						int index = (ngVeInt == 1 || ngVeInt == 2 || ngVeInt == 3 )? 0 :
									(ngVeInt == 4 || ngVeInt == 5 || ngVeInt == 6 )? 1 :
									(ngVeInt == 7 || ngVeInt == 8 || ngVeInt == 9 )? 2 :3;
						rs.getDoanhThu_qui()[index] += listPDK.get(i).getTongTien();
					}
				}
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeDoanhThuTheoNhieuNam(String startYear, String endYear){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		int startY = Integer.parseInt(startYear);
		int endY = Integer.parseInt(endYear);
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3 ";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					ngVe = ngVe.split("/")[2];
					int ngVeInt = Integer.parseInt(ngVe);
					if(ngVeInt >= startY && ngVeInt <= endY){
						int index = ngVeInt - startY;
						rs.getDoanhThu_nhieunam()[index] += listPDK.get(i).getTongTien();
					}
				}
			}
			for(int i = startY, j = 0; i <= endY; i++, j++){
				rs.getNhieunam()[j] = String.valueOf(i);
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeKhachTheoNam_Thang(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3 ";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						rs.getKhach_thang()[ngVeInt-1] += listPDK.get(i).getSLNguoi();
					}
				}
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeKhachTheoNam_Qui(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					if(ngVe.split("/")[2].equals(year)){
						ngVe = ngVe.split("/")[1];
						int ngVeInt = Integer.parseInt(ngVe);
						int index = (ngVeInt == 1 || ngVeInt == 2 || ngVeInt == 3 )? 0 :
									(ngVeInt == 4 || ngVeInt == 5 || ngVeInt == 6 )? 1 :
									(ngVeInt == 7 || ngVeInt == 8 || ngVeInt == 9 )? 2 :3;
						rs.getKhach_qui()[index] += listPDK.get(i).getSLNguoi();
					}
				}
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeKhachTheoNhieuNam(String startYear, String endYear){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		int startY = Integer.parseInt(startYear);
		int endY = Integer.parseInt(endYear);
		
		List<PhieuDangKi> listPDK = null;
		try {
			String sql = "select p from PhieuDangKi p where p.Co = '0' and p.TrangThai = 3";
			Query query = session.createQuery(sql);
			listPDK = query.list();
			if(listPDK != null){
				for(int i = 0; i < listPDK.size(); i++){
					TourForm tt = this.getTourByIDOfPDK(String.valueOf(listPDK.get(i).getMa()));
					String ngVe = tt.getNgay_ve();
					ngVe = ngVe.split("/")[2];
					int ngVeInt = Integer.parseInt(ngVe);
					if(ngVeInt >= startY && ngVeInt <= endY){
						int index = ngVeInt - startY;
						rs.getKhach_nhieunam()[index] += listPDK.get(i).getSLNguoi();
					}					
				}
			}
			for(int i = startY, j = 0; i <= endY; i++, j++){
				rs.getNhieunam()[j] = String.valueOf(i);
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeTienGocTheoNam_Thang(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();

		try {
			thongKeSLTourTheoNam_Thang(year);
			rs = thongKeDoanhThuTheoNam_Thang(year);
			for(int i = 0; i < listTourInMonth_ma.size(); i++){
				String sql = "select p from ChiPhiThucTe p where p.Co = '0' and p.MaTour = '" + listTourInMonth_ma.get(i) + "'";
				Query query = session.createQuery(sql);
				List<Object> listobj = query.list();
				if(listobj.size() != 0){
					ChiPhiThucTe cptt = (ChiPhiThucTe)query.list().get(0);
					float chiphitt = Integer.parseInt(cptt.getChiPhiAnUong()) + Integer.parseInt(cptt.getChiPhiDiChuyen()) +
							Integer.parseInt(cptt.getChiPhiO()) + Integer.parseInt(cptt.getChiPhiPhatSinh());
					
					String ngVe = listTourInMonth_ngVe.get(i).split("/")[1];;
					int ngVeInt = Integer.parseInt(ngVe);
					rs.getTienGoc_thang()[ngVeInt-1] += chiphitt;
				}
			}
			for(int i = 0; i < 12; i++){
				rs.getLoiNhuan_thang()[i] = rs.getDoanhThu_thang()[i] - rs.getTienGoc_thang()[i];
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	
	public StatictisForm thongKeTienGocTheoNam_Qui(String year){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();

		try {
			thongKeSLTourTheoNam_Thang(year);
			rs = thongKeDoanhThuTheoNam_Qui(year);
			for(int i = 0; i < listTourInMonth_ma.size(); i++){
				String sql = "select p from ChiPhiThucTe p where p.Co = '0' and p.MaTour = '" + listTourInMonth_ma.get(i) + "'";
				Query query = session.createQuery(sql);
				List<Object> listobj = query.list();
				if(listobj.size() != 0){
					ChiPhiThucTe cptt = (ChiPhiThucTe)query.list().get(0);
					float chiphitt = Integer.parseInt(cptt.getChiPhiAnUong()) + Integer.parseInt(cptt.getChiPhiDiChuyen()) +
							Integer.parseInt(cptt.getChiPhiO()) + Integer.parseInt(cptt.getChiPhiPhatSinh());
					
					String ngVe = listTourInMonth_ngVe.get(i).split("/")[1];;
					int ngVeInt = Integer.parseInt(ngVe);
					int index = (ngVeInt == 1 || ngVeInt == 2 || ngVeInt == 3 )? 0 :
						(ngVeInt == 4 || ngVeInt == 5 || ngVeInt == 6 )? 1 :
						(ngVeInt == 7 || ngVeInt == 8 || ngVeInt == 9 )? 2 :3;
					rs.getTienGoc_qui()[index] += chiphitt;
				}
			}
			for(int i = 0; i < 4; i++){
				rs.getLoiNhuan_qui()[i] = rs.getDoanhThu_qui()[i] - rs.getTienGoc_qui()[i];
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	public StatictisForm thongKeTienGocTheoNhieuNam(String startYear, String endYear){
		StatictisForm rs = new StatictisForm();
		Session session = this.sessionFactory.getCurrentSession();
		
		int startY = Integer.parseInt(startYear);
		int endY = Integer.parseInt(endYear);
		
		for(int i = startY, j = 0; i < endY; i++, j++){
			StatictisForm temp = thongKeTienGocTheoNam_Thang(String.valueOf(i));
			long sum = 0;
			for (float k : temp.getLoiNhuan_thang())
			    sum += k;
			rs.getLoiNhuan_nhieunam()[j] = sum;
			rs.getNhieunam()[j] = "Năm " + String.valueOf(i);
		}
		
		return rs;
	}
	
	
	
	public List<Object> thongKeTourTheoNam_Thang(String month, String year){
		List<Object> rs = new ArrayList<Object>();
		Session session = this.sessionFactory.getCurrentSession();
		int month_int = Integer.parseInt(month);
		int year_int = Integer.parseInt(year);
		String date = month_int + "/" + year_int;
		try {
			for(int i = 0; i < listTourInMonth_ma.size(); i++){
				String ma = listTourInMonth_ma.get(i);
				String sql = "select count(*) as sl "
						+ "from Tour t, TourThucTe ttt "
						+ "where (t.ma = '" + ma + "' and t.ngay_ve like '%" + date + "%') or (ttt.ma_tour_goc = '" + ma + "' and ttt.ngay_ve like '%" + date + "%')";
				Query query = session.createQuery(sql);
				Object obj = query.list().get(0);
				if(!obj.toString().equals("0")){
					sql = "select sum(TongTien) from PhieuDangKi p  where p.MaTour = '" + ma + "' and p.Co = '0' and p.TrangThai = '3'";
					query = session.createQuery(sql);
					List<Object> _list = new ArrayList<Object>();
					_list = query.list();
					float tongtien = 0;
					if(_list.get(0) != null)
						tongtien = Integer.parseInt(_list.get(0).toString());
					Temp temp = new Temp();
					temp.ma = Integer.valueOf(ma);
					temp.ten = listTourInMonth_ten.get(i);
					temp.tongtien = tongtien;
					rs.add(temp);
				}
			}

			
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return rs;
	}
	
	private TourForm convertTourToTourForm(Tour t){
		TourForm tf = new TourForm();
		if(t != null){
			tf.setMa(t.getMa());
			tf.setMa_tour_goc(null);
			tf.setTen(t.getTen());
			tf.setNgay_di(t.getNgay_di());
			tf.setNgay_ve(t.getNgay_ve());
			tf.setSo_ve(t.getSo_ve());
			tf.setSo_ve_con_lai(t.getSo_ve_con_lai());
			tf.setGioi_thieu(t.getGioi_thieu());
			tf.setChuong_trinh(t.getChuong_trinh());
			tf.setPhuong_tien(t.getPhuong_tien());
			tf.setHinh_anh(t.getHinh_anh());
			tf.setGia(t.getGia());			
			tf.setMa_huong_dan_vien(t.getHuong_dan_vien() == null ? null : t.getHuong_dan_vien().getMa());
			tf.setTen_huong_dan_vien(t.getHuong_dan_vien() == null ? "" : t.getHuong_dan_vien().getHoTen());
			
		}
		return tf;
	}
	
	private TourForm convertTourTTToTourForm(TourThucTe t){
		TourForm tf = new TourForm();
		if(t != null){
			tf.setMa(null);
			tf.setMa_tour_goc(t.getMa_tour_goc());
			tf.setTen(t.getTen());
			tf.setNgay_di(t.getNgay_di());
			tf.setNgay_ve(t.getNgay_ve());
			tf.setSo_ve(t.getSo_ve());
			tf.setSo_ve_con_lai(t.getSo_ve_con_lai());
			tf.setGioi_thieu(t.getGioi_thieu());
			tf.setChuong_trinh(t.getChuong_trinh());
			tf.setPhuong_tien(t.getPhuong_tien());
			//tf.setHinh_anh(t.getHinh_anh());
			tf.setGia(t.getGia());
			tf.setMa_huong_dan_vien(t.getHuong_dan_vien() == null ? null : t.getHuong_dan_vien().getMa());
			tf.setTen_huong_dan_vien(t.getHuong_dan_vien() == null ? "" : t.getHuong_dan_vien().getHoTen());
		}
		return tf;
	}
	
	private StatictisForm getThongKeSLTourTheoNam_Thang(int index, String month, List<Tour> listTour, StatictisForm rs){
		rs.getThang()[index - 1] = "Tháng " + month;
		for(int i = 0; i < listTour.size(); i++){
			if(listTour.get(i).getNgay_ve().contains("/" + month + "/")){
				rs.getSLTour_thang()[index-1] = rs.getSLTour_thang()[index-1] + 1;
			}
		}
		return rs;
	}
	
	private StatictisForm getThongKeSLTourTheoNam_Qui(int index, String month, List<Tour> listTour, StatictisForm rs){
		int qui = index;
		rs.getQui()[qui -1] = "Quí " + qui;
		for(int i = 0; i < listTour.size(); i++){
			if(qui == 1 &&(
					listTour.get(i).getNgay_ve().contains("/" + "01" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "02" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "03" + "/"))){
				rs.getSLTour_qui()[qui - 1] = rs.getSLTour_qui()[qui - 1] + 1;
			}
			if(qui == 2&&(
					listTour.get(i).getNgay_ve().contains("/" + "04" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "05" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "06" + "/"))){
				rs.getSLTour_qui()[qui - 1] = rs.getSLTour_qui()[qui - 1] + 1;
			}
			if(qui == 3&&(
					listTour.get(i).getNgay_ve().contains("/" + "07" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "08" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "09" + "/"))){
				rs.getSLTour_qui()[qui - 1] = rs.getSLTour_qui()[qui - 1] + 1;
			}
			if(qui == 4&&(
					listTour.get(i).getNgay_ve().contains("/" + "10" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "11" + "/") ||
					listTour.get(i).getNgay_ve().contains("/" + "12" + "/"))){
				rs.getSLTour_qui()[qui - 1] = rs.getSLTour_qui()[qui - 1] + 1;
			}
		}
		return rs;
	}
	
	private StatictisForm getThongKeSLTourTheoNhieuNam(int index, String year, List<Tour> listTour, StatictisForm rs){
		rs.getNhieunam()[index] = year;
		for(int i = 0; i < listTour.size(); i++){
			if(listTour.get(i).getNgay_ve().contains("/" + year )){
				rs.getSLTour_nhieunam()[index] = rs.getSLTour_nhieunam()[index] + 1;
			}
		}
		return rs;
	}
	
	class Temp{
		public int ma;
		public String ten;
		public float tongtien;
	}
	//////////////////////////////////DuyetTour////////////////////////////////////////////////////////////
	public List<TourForm> getListTour(TourForm tourform){
		 System.out.println("FUCKING ERROR");
		List<TourForm> result = new ArrayList<TourForm>();
		if(tourform != null){
			Session session = this.sessionFactory.getCurrentSession();

			try {

				String sql = "SELECT p "
						+ "FROM Tour p " 
						+ "Where p.co = 0 " ;
				String sql_tt = "SELECT p "
						+ "FROM TourThucTe p " 
						+ "Where p.co = 0 " ;
				
				if(tourform.getMa() !=  null){
					sql += "and " + "p.ma = " + tourform.getMa() + " ";
					sql_tt += "and " + "p.ma_tour_goc = " + tourform.getMa() + " ";
				}
				if(tourform.getTen() != null && !tourform.getTen().equals("")){
					sql += "and " + "p.ten like '%" + tourform.getTen() + "%' ";
					sql_tt += "and " + "p.ten like '%" + tourform.getTen() + "%' ";
				}
				if(tourform.getMa_huong_dan_vien() !=  null){
					sql += "and " + "p.getHuong_dan_vien().getMa() = " + tourform.getMa_huong_dan_vien() + " ";
					sql_tt += "and " + "p.getHuong_dan_vien().getMa() = " + tourform.getMa_huong_dan_vien() + " ";
				}
				if(tourform.getTen_huong_dan_vien() != null && !tourform.getTen_huong_dan_vien().equals("")){
					sql += "and " + "p.getHuong_dan_vien().getHoTen()  like '%" + tourform.getTen_huong_dan_vien() + "%' ";
					sql_tt += "and " + "p.getHuong_dan_vien().getHoTen()  like '%" + tourform.getTen_huong_dan_vien() + "%' ";
				}
				
				List<Tour> listTour = session.createQuery(sql).list();
				List<TourThucTe> listTourTT = session.createQuery(sql_tt).list();
				result = new ArrayList<TourForm>();
				result = this.convertPojoToForm(listTour, listTourTT, tourform.getTrang_thai());

			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return result;
	}
	
	/*public void duyetTour(int id_tour, int id_HDV, int status){
		Session session = this.sessionFactory.getCurrentSession();
		try {

			String sql = "SELECT p "
					+ "FROM PhieuDangKi p " 
					+ "Where p.Co = 0 and p.MaTour = " + id_tour ;
			
			
			List<PhieuDangKi> listPDK = session.createQuery(sql).list();
			for(int i = 0; i < listPDK.size(); i++){
				PhieuDangKi pdk = (PhieuDangKi) session.get(PhieuDangKi.class, listPDK.get(0).getMa());
				if(pdk.isTrangThai() == 2){
					pdk.setTrangThai(status);
					session.update(pdk);
				}
			}
			if(status == 3){
				Tour t = (Tour) session.get(Tour.class, id_tour);
				User u = (User) session.get(User.class, id_HDV);
				t.setHuong_dan_vien(u);
				session.update(t);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
	}*/
	
	public void duyetTour(int id_tour, int id_HDV, int status){
		Session session = this.sessionFactory.getCurrentSession();
		try {

			String sql = "SELECT p "
					+ "FROM PhieuDangKi p " 
					+ "Where p.Co = 0 and p.MaTour = " + id_tour ;
			
			
			List<PhieuDangKi> listPDK = session.createQuery(sql).list();
			boolean chinhsua = false;
			for(int i = 0; i < listPDK.size(); i++){
				PhieuDangKi pdk = (PhieuDangKi) session.get(PhieuDangKi.class, listPDK.get(0).getMa());
				if(pdk.isTrangThai() == 2){
					pdk.setTrangThai(status);
					session.update(pdk);
				}
				chinhsua = pdk.isChinhSua();
			}
			if(status == 3){
				
				
				 
				if(chinhsua){
					sql = "SELECT p "
							+ "FROM TourThucTe p " 
							+ "Where p.co = 0 and p.ma_tour_goc = " + id_tour ;
					
					
					TourThucTe tt = (TourThucTe)session.createQuery(sql).list().get(0);
					//TourThucTe tt = (TourThucTe) session.get(TourThucTe.class, id_tour);
					User u = (User) session.get(User.class, id_HDV);
					tt.setHuong_dan_vien(u);
					session.update(tt);
				}else{
					Tour t = (Tour) session.get(Tour.class, id_tour);
					User u = (User) session.get(User.class, id_HDV);
					t.setHuong_dan_vien(u);
					session.update(t);
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
	}
	
	public void huyTour(int id_tour){
		Session session = this.sessionFactory.getCurrentSession();
		PhieuDangKiDAO pdkDAO = new PhieuDangKiDAOImpl();
		try {

			String sql = "SELECT p "
					+ "FROM Tour p " 
					+ "Where p.co = 0 and p.ma = " + id_tour ;
			
			
			List<Tour> listTour = session.createQuery(sql).list();
			listTour.get(0).setCo(1);
			session.update(listTour.get(0));
			
			sql = "SELECT p "
					+ "FROM PhieuDangKi p " 
					+ "Where p.Co = 0 and p.MaTour = " + id_tour ;
			List<PhieuDangKi> listPDK = session.createQuery(sql).list();
			for(int i = 0; i < listPDK.size(); i++){
				PhieuDangKi pdk = (PhieuDangKi) session.get(PhieuDangKi.class, listPDK.get(i).getMa());
				pdk.setTrangThai(5);
				pdk.setCo(false);
				session.update(pdk);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
	}
	
	private List<TourForm> convertPojoToForm(List<Tour> listTour, List<TourThucTe> listTourTT, int trangthai){
		List<TourForm> result = new ArrayList<TourForm>();
		for(int i = 0; i < listTourTT.size(); i++){
			//List<PhieuDangKi> list = new ArrayList<PhieuDangKi>(listTourTT.get(i).getListPDK());
			List<PhieuDangKi> list = getListPDKByID(listTourTT.get(i).getMa_tour_goc().toString(), true);
			if(list.size() == 0){
				continue;
			}
			if(trangthai != 6 && !this.checkTour(list, trangthai)){
				continue;
			}
			if(trangthai == 6 && !this.checkTour(list, 2) && !this.checkTour(list, 3)){
				continue;
			}
			TourForm tf = new TourForm();
			tf.setMa(listTourTT.get(i).getMa_tour_goc());
			tf.setMa_huong_dan_vien(listTour.get(i).getHuong_dan_vien() == null ? null : listTour.get(i).getHuong_dan_vien().getMa());
			tf.setTen_huong_dan_vien(listTour.get(i).getHuong_dan_vien() == null ? "" : listTour.get(i).getHuong_dan_vien().getHoTen());
			tf.setTen(listTourTT.get(i).getTen());
			tf.setNgay_di(listTourTT.get(i).getNgay_di());
			tf.setNgay_ve(listTourTT.get(i).getNgay_ve());
			tf.setSo_ve(listTourTT.get(i).getSo_ve());
			tf.setSo_ve_con_lai(listTourTT.get(i).getSo_ve_con_lai());
			tf.setGioi_thieu(listTourTT.get(i).getGioi_thieu());
			tf.setChuong_trinh(listTourTT.get(i).getChuong_trinh());
			tf.setPhuong_tien(listTourTT.get(i).getPhuong_tien());
//			tf.setHinh_anh(listTourTT.get(i).getHinh_anh());
			tf.setGia(listTourTT.get(i).getGia());
			tf.setTrang_thai(this.checkTour(list, 2) == true? 2 : 3);
			result.add(tf);
		}
		for(int i = 0; i < listTour.size(); i++){
			if(this.checkItem(listTourTT, listTour.get(i).getMa()) == false){
				//List<PhieuDangKi> list = new ArrayList<PhieuDangKi>(listTour.get(i).getListPDK());
				List<PhieuDangKi> list = getListPDKByID(listTour.get(i).getMa().toString(), false);
				if(list.size() == 0){
					continue;
				}
				if(trangthai != 6 && !this.checkTour(list, trangthai)){
					continue;
				}
				if(trangthai == 6 && !this.checkTour(list, 2) && !this.checkTour(list, 3)){
					continue;
				}
				TourForm tf = new TourForm();
				tf.setMa(listTour.get(i).getMa());
				tf.setMa_huong_dan_vien(listTour.get(i).getHuong_dan_vien() == null ? null : listTour.get(i).getHuong_dan_vien().getMa());
				tf.setTen_huong_dan_vien(listTour.get(i).getHuong_dan_vien() == null ? "" : listTour.get(i).getHuong_dan_vien().getHoTen());
				tf.setTen(listTour.get(i).getTen());
				tf.setNgay_di(listTour.get(i).getNgay_di());
				tf.setNgay_ve(listTour.get(i).getNgay_ve());
				tf.setSo_ve(listTour.get(i).getSo_ve());
				tf.setSo_ve_con_lai(listTour.get(i).getSo_ve_con_lai());
				tf.setGioi_thieu(listTour.get(i).getGioi_thieu());
				tf.setChuong_trinh(listTour.get(i).getChuong_trinh());
				tf.setPhuong_tien(listTour.get(i).getPhuong_tien());
				tf.setHinh_anh(listTour.get(i).getHinh_anh());
				tf.setGia(listTour.get(i).getGia());
				tf.setTrang_thai(this.checkTour(list, 2) == true? 2 : 3);
				result.add(tf);
			}
		}
		return result;
	}
	
	private boolean checkItem(List<TourThucTe> listTourTT, int maTour){
		for(int i = 0; i < listTourTT.size(); i++){
			if(listTourTT.get(i).getMa() == maTour){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkTour(List<PhieuDangKi> listPDK, int status){
		for(int i = 0; i < listPDK.size(); i++){
			if(listPDK.get(i).isTrangThai() != status)
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Tour getTourByKey(String id_tour) {
		Tour uresult = null;
		if(!id_tour.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				
				String sql = "select tour from Tour tour where co = '0' and ma = '" + id_tour + "'" ;
				List<Tour> list = session.createQuery(sql).list();
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	public int addTour(TourForm tourForm,MultipartFile file) {
		int rs = -1;
		Session session = this.sessionFactory.getCurrentSession();
		try {

			Tour tour = new Tour();
			tour.setNgay_ve(tourForm.getNgay_ve());
			tour.setNgay_di(tourForm.getNgay_di());
			tour.setPhuong_tien(tourForm.getPhuong_tien());
			tour.setHinh_anh(file.getBytes());
			tour.setGioi_thieu(tourForm.getGioi_thieu());
			tour.setTen(tourForm.getTen());
			tour.setSo_ve(tourForm.getSo_ve());
			tour.setSo_ve_con_lai(tourForm.getSo_ve());
			tour.setChuong_trinh(tourForm.getChuong_trinh());
			tour.setGia(tourForm.getGia());
			tour.setCo(0);
			session.save(tour);
			
			System.out.println(tour.getMa());
			session.flush();
			rs = tour.getMa();
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return -1;
		} 
		return rs;
	}
	
	public List<Tour> getListTour_Web() throws ParseException {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Tour> tours = null;
		try {
			String sql = "select tour from Tour tour where tour.co = 0 and tour.so_ve_con_lai > 0";
			Query query = session.createQuery(sql);
			tours = query.list();
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		 
		List<Tour> result = new ArrayList();;
		for (Tour tour : tours) {
			Date date = sdf.parse(tour.getNgay_di());
			cal.setTime(date);
			if (cal.getTime().getTime() < new Date().getTime())
				continue;
			result.add(tour);
		}

	    return result;
	}

	public Tour getTourById_Web(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		Tour result = null;
		try {
			String sql = "select tour from Tour tour where tour.ma = " + id;
			Query query = session.createQuery(sql);
			result = (Tour) query.list().get(0);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

		return result;
	}

	public void updateTourBySoVeConLai_Web(Tour tour) {
		Session session = this.sessionFactory.getCurrentSession();
			
		try {
			session.update(tour);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
	}
	
	public List<Tour> getListTourByHDV(String id) {
		Session session = this.sessionFactory.getCurrentSession();
		
		List<Tour> result = null;
		try {
			String sql = "select tour from Tour tour where tour.co = 0 and tour.huong_dan_vien.Ma = " + id;
			Query query = session.createQuery(sql);
			result = query.list();
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

		return result;
	}
	
	public ChiPhiThucTe getChiPhiThucTe(String id) {
		Session session = this.sessionFactory.getCurrentSession();
		
		ChiPhiThucTe result = null;
		try {
			String sql = "select cp from ChiPhiThucTe cp where cp.Co = 0 and cp.MaTour = " + id;
			Query query = session.createQuery(sql);
			if (query.list().size() > 0)
				result = (ChiPhiThucTe) query.list().get(0);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

		return result;
	}
	
	public void addChiPhiThucTe(ChiPhiThucTeForm chiphiForm) {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			
			session.save(new ChiPhiThucTe(Integer.valueOf(chiphiForm.getMa_tour()), chiphiForm.getChi_phi_di_chuyen(), 
					chiphiForm.getChi_phi_an_uong(), chiphiForm.getChi_phi_o(), chiphiForm.getChi_phi_phat_sinh(), false));
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
	}
	
	public Boolean updateTourOrigion(TourForm tourForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Tour tourGoc = (Tour) session.get(Tour.class, tourForm.getMa());
			if(tourGoc.getCo() == 0){
				//System.out.println(tourForm.getProgram());
				tourGoc.setNgay_di(tourForm.getNgay_di());
				tourGoc.setNgay_ve(tourForm.getNgay_ve());
				tourGoc.setChuong_trinh(tourForm.getChuong_trinh());
				tourGoc.setGia(tourForm.getGia());
				tourGoc.setGioi_thieu(tourForm.getGioi_thieu());
				tourGoc.setPhuong_tien(tourForm.getPhuong_tien());
				tourGoc.setTen(tourForm.getTen());
				tourGoc.setSo_ve(tourForm.getSo_ve());
				session.update(tourGoc);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
    public List<Tour> getListTourbyString(String name){
        Session session = this.sessionFactory.getCurrentSession();
        
        List<Tour> result = null;
        try {
            String sql = "select tour from Tour tour where co = '0' and  ten like '%"+ name +"%'";
            Query query = session.createQuery(sql);
            result = query.list();
        }
        catch (Exception ex) {
            System.out.println(ex);
            session.getTransaction().rollback();
        }

        return result;
    }

}
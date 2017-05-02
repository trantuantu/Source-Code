package com.dulich.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import com.dulich.form.PhieuDangKiForm;
import com.dulich.model.pojo.KhachHang;
import com.dulich.model.pojo.PhieuDangKi;
import com.dulich.model.pojo.User;


@Transactional
public class PhieuDangKiDAOImpl implements PhieuDangKiDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<PhieuDangKiForm> getListPhieuDangKi(PhieuDangKiForm phieuDangKiForm){
		List<PhieuDangKiForm> rs = null;
		if(phieuDangKiForm != null){
			Session session = this.sessionFactory.getCurrentSession();

			try {
				String sql = "SELECT p, k "
						+ "FROM PhieuDangKi p, KhachHang k " 
						+ "where p.MaKhachHang = k.Ma and p.Co = 0 and k.Co = 0 " ;
				
				if(phieuDangKiForm.getMaTour() !=  ""){
					sql += "and " + "p.MaTour = " + phieuDangKiForm.getMaTour() + " ";
				}
				if(phieuDangKiForm.getMa() !=  ""){
					sql += "and " + "p.Ma = " + phieuDangKiForm.getMa() + " ";
				}
				if(phieuDangKiForm.getMaKhachHang() !=  ""){
					sql += "and " + "p.MaKhachHang = " + phieuDangKiForm.getMaKhachHang() + " ";
				}
				
				if(phieuDangKiForm.getTenKhachHang() != null && !phieuDangKiForm.getTenKhachHang().equals("")){
					sql += "and " + "k.Ten like '%" + phieuDangKiForm.getTenKhachHang() + "%' ";
				}
				
				
				List<Object[]> temp = session.createQuery(sql).list();
				rs = new ArrayList<PhieuDangKiForm>();
				for (Object[] item : temp) {
					
					PhieuDangKi p = (PhieuDangKi)item[0];
					KhachHang k = (KhachHang)item[1];
					rs.add(new PhieuDangKiForm(String.valueOf(p.getMa()), String.valueOf(p.getMaTour()), String.valueOf(p.getMaKhachHang()), "", k.getTen(),
							 p.getTongTien(), p.getTienCoc(), p.getSLNguoi(), p.getGhiChu(), p.isChinhSua(), p.isTrangThai()));
				}
				return rs;
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return rs;
	}

	public boolean duyetPhieuDangKi(int id, int status){
		
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			PhieuDangKi pdk = (PhieuDangKi) session.get(PhieuDangKi.class, id);
			pdk.setTrangThai(status);
			session.update(pdk);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public void create_Web(PhieuDangKi booking) {
		Session session = this.sessionFactory.getCurrentSession();

		try {
			session.save(booking);	
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
	}

}

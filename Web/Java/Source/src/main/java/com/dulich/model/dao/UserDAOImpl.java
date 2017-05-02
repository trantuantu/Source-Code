package com.dulich.model.dao;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dulich.form.NghiPhepForm;
import com.dulich.form.UserForm;
import com.dulich.model.pojo.NghiPhep;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.User;


@Transactional
public class UserDAOImpl implements UserDAO{

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getListUser(UserForm userForm) {
		List<User> list = null;
		if(userForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select user from User user where co = '0' ";
				if(String.valueOf(userForm.getLoai()) !=  "" && !String.valueOf(userForm.getLoai()).equals("3")){
					sql += "and " + "user.Loai = '" + String.valueOf(userForm.getLoai()) + "' ";
				}
				if(userForm.getMa() != null && !userForm.getMa().toString().equals("")){
					sql += "and " + "user.Ma = '" + userForm.getMa().toString() + "	' ";
				}
				if(userForm.getTaiKhoan() != null && !userForm.getTaiKhoan().equals("")){
					sql += "and " + "user.TaiKhoan like '%" + userForm.getTaiKhoan() + "%' ";
				}
				if(userForm.getHoTen()!= null && !userForm.getHoTen().equals("")){
					sql += "and " + "user.HoTen like '%" + userForm.getHoTen() + "%' ";
				}
				if(userForm.getDiaChi() != null && !userForm.getDiaChi().equals("")){
					sql += "and " + "user.DiaChi like '%" + userForm.getDiaChi() + "%' ";
				}
				if(userForm.getNamSinh() != null && !userForm.getNamSinh().equals("")){
					sql += "and " + "user.NamSinh like '%" + userForm.getNamSinh() + "%' ";
				}
				if(userForm.getGioiTinh() != null && !userForm.getGioiTinh().equals("") && !userForm.getGioiTinh().equals("2")){
					sql += "and " + "user.GioiTinh = '" + userForm.getGioiTinh() + "' ";
				}
				
				list = session.createQuery(sql).list();
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getListUserDuyetNghiPhep(UserForm userForm) {
		List<User> list = null;
		if(userForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select DISTINCT  user from User user, NghiPhep p where user.Co = '0' ";
				if(String.valueOf(userForm.getLoai()) !=  "" && !String.valueOf(userForm.getLoai()).equals("3")){
					sql += "and " + "user.Loai = '" + String.valueOf(userForm.getLoai()) + "' ";
				}
				if(userForm.getMa() != null && !userForm.getMa().toString().equals("")){
					sql += "and " + "user.Ma = '" + userForm.getMa().toString() + "	' ";
				}
				if(userForm.getTaiKhoan() != null && !userForm.getTaiKhoan().equals("")){
					sql += "and " + "user.TaiKhoan like '%" + userForm.getTaiKhoan() + "%' ";
				}
				if(userForm.getHoTen()!= null && !userForm.getHoTen().equals("")){
					sql += "and " + "user.HoTen like '%" + userForm.getHoTen() + "%' ";
				}
				if(userForm.getDiaChi() != null && !userForm.getDiaChi().equals("")){
					sql += "and " + "user.DiaChi like '%" + userForm.getDiaChi() + "%' ";
				}
				if(userForm.getNamSinh() != null && !userForm.getNamSinh().equals("")){
					sql += "and " + "user.NamSinh like '%" + userForm.getNamSinh() + "%' ";
				}
				if(userForm.getGioiTinh() != null && !userForm.getGioiTinh().equals("") && !userForm.getGioiTinh().equals("2")){
					sql += "and " + "user.GioiTinh = '" + userForm.getGioiTinh() + "' ";
				}
				if(userForm.getNghiPhep() == 1){
					sql += "and " + "user.Ma = p.MaNV and p.XacNhan = 0 and p.Co = 0 ";
				}
				
				list = session.createQuery(sql).list();
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean deleteUser(String idUser){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			User usser = (User) session.get(User.class, Integer.parseInt(idUser));
			usser.setCo(true);
			session.update(usser);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}

	public User getUserByMa(String idUer) {
		User uresult = null;
		if(idUer!= null && !idUer.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select user from User user where co = '0' and ma = '" + idUer + "'" ;
				List<User> list = session.createQuery(sql).list();
				
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}

	public Boolean updateUser(UserForm userForm){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			User user = (User) session.get(User.class, Integer.parseInt(userForm.getMa()));
			if(user.getCo() == false){
				user.setCMND(userForm.getCMND());
				user.setDiaChi(userForm.getDiaChi());
				user.setGioiTinh(userForm.getGioiTinh());
				user.setHoTen(userForm.getHoTen());
				user.setLoai(userForm.getLoai());
				user.setSoDienThoai(userForm.getSoDT());
				user.setTaiKhoan(userForm.getTaiKhoan());
				user.setNamSinh(userForm.getNamSinh());
				session.update(user);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}

	public String addUser(UserForm userForm) {
		String rs = "";
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(this.checkUser(userForm) == null){
				User user = new User();
				user.setCMND(userForm.getCMND());
				user.setDiaChi(userForm.getDiaChi());
				user.setGioiTinh(userForm.getGioiTinh() );
				user.setHoTen(userForm.getHoTen());
				user.setLoai( userForm.getLoai() );
				user.setMatKhau("123456");
				user.setNamSinh(userForm.getNamSinh());
				user.setSoDienThoai(userForm.getSoDT());
				user.setTaiKhoan(userForm.getTaiKhoan());
				session.save(user);
				session.flush();
				rs = "1";
			}else{
				rs = "Tài khoản/cmnd đã tồn tại";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return "Lỗi database";
		} 
		return rs;
	}

	public User checkUser(UserForm userForm) {
		User uresult = null;
		if(userForm != null){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select user from User user where co = '0' and ( tai_khoan = '" + userForm.getTaiKhoan() + "' or"
						+ " cmnd = '" + userForm.getCMND() + "')";
				List<User> list = session.createQuery(sql).list();
				
				uresult = (list.size() == 0)? null : list.get(0);
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	@SuppressWarnings("unchecked")
	public List<NghiPhep> getNNPByIdNV(String idUer, int type){
		List<NghiPhep> uresult = null;
		if(idUer!= null && !idUer.isEmpty()){
			Session session = this.sessionFactory.getCurrentSession();
			
			try {
				String sql = "select p from NghiPhep p where co = '0' and xac_nhan = '" + type + "' and ma_nhan_vien = '" + idUer + "'" ;
				uresult = (List<NghiPhep>)session.createQuery(sql).list();
			} catch (Exception e) {
				e.printStackTrace();
		        session.getTransaction().rollback();
			}
		}
		return uresult;
	}
	
	
	public Boolean duyetNNPByListID(String[] listId){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			for (String item : listId) {
				NghiPhep nghiPhep = (NghiPhep) session.get(NghiPhep.class, Integer.parseInt(item));
				if(nghiPhep.isCo() == false){
					nghiPhep.setXacNhan(true);
					session.update(nghiPhep);
					rs = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	public Boolean xoaNNPByListID(String[] listId){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			for (String item : listId) {
				NghiPhep nghiPhep = (NghiPhep) session.get(NghiPhep.class, Integer.parseInt(item));
				if(nghiPhep.isCo() == false){
					nghiPhep.setCo(true);
					session.update(nghiPhep);
					rs = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public Boolean suaNNP(String id, String ngNghi, String ngLamLai){
		Boolean rs = false;
		Session session = this.sessionFactory.getCurrentSession();
		try {
				NghiPhep nghiPhep = (NghiPhep) session.get(NghiPhep.class, Integer.parseInt(id));
				if(nghiPhep.isCo() == false){
					nghiPhep.setNgayDiLam(ngLamLai);
					nghiPhep.setNgayNghi(ngNghi);
					session.update(nghiPhep);
					rs = true;
				}
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	}
	
	public Boolean themNNP(String userId, String ngNghi, String ngLam) {
		String rs = "";
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
				NghiPhep np = new NghiPhep();
				np.setMaNV(userId);
				np.setNgayNghi(ngNghi);
				np.setNgayDiLam(ngLam);
				session.save(np);
				session.flush();
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return true;
	}
	
	 public Boolean resetPassword(String id) {
		 Boolean rs = false;
		 Session session = this.sessionFactory.getCurrentSession();
		 try {
			User user = (User) session.get(User.class, Integer.parseInt(id));
			if(user.getCo() == false){
				user.setMatKhau("123456789");
				session.update(user);
				rs = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
	        return false;
		} 
		return rs;
	 }
	 
	 public List<NghiPhep> getListNghiPhepByIdUser(String id) {
	 	Session session = this.sessionFactory.getCurrentSession();
	 	
	 	List<NghiPhep> result = null;
		try {
			String sql = "select np from NghiPhep np where np.Co = 0 and np.MaNV = " + id;
			Query query = session.createQuery(sql);
			result = query.list();
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

		return result;
	 }
	 
	 public void deleteNghiPhepById(String idNghiPhep) {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		try {
			String sql = "select np from NghiPhep np where np.Id =  " + idNghiPhep;
			Query query = session.createQuery(sql);
			if (query.list().size() == 0)
				return;
			NghiPhep np = (NghiPhep)query.list().get(0);
			np.setCo(true);
			session.update(np);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
	 }
	 
	 public User getUserByTaiKhoan(String username) {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 User result = null;
		try {
			String sql = "select user from User user where user.Co = 0 and user.TaiKhoan = '" + username + "'";
			Query query = session.createQuery(sql);
			if (query.list().size() > 0)
				result = (User) query.list().get(0);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}
		return result;
	 }
	
}

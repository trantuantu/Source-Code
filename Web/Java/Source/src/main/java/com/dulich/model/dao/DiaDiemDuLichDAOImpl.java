package com.dulich.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dulich.form.PlaceForm;
import com.dulich.model.pojo.DiaDiemDuLich;
import com.dulich.model.pojo.RegistrationForm;

@Transactional
public class DiaDiemDuLichDAOImpl implements DiaDiemDuLichDAO{
	
	private SessionFactory sessionFactory;
	 
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DiaDiemDuLich getById_Web(String id) {
		Session session = this.sessionFactory.getCurrentSession();
		DiaDiemDuLich result = null;
		try {
			String sql = "select dddl from DiaDiemDuLich dddl where dddl.ma = " + id;
			Query query = session.createQuery(sql);
			result = (DiaDiemDuLich) query.list().get(0);
		}
		catch (Exception ex) {
			System.out.println(ex);
			session.getTransaction().rollback();
		}

		return result;
	}
	@SuppressWarnings("unchecked")
	public List<DiaDiemDuLich> getAllDiadiemdulich(){
		List<DiaDiemDuLich> list = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			String sql = "select dddl from DiaDiemDuLich dddl where co = '0' ";
			list = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<DiaDiemDuLich> getDiadiemdulich(String ten){
		List<DiaDiemDuLich> list = null;
		Session session = this.sessionFactory.getCurrentSession();
		try {
			String sql = "select dddl from DiaDiemDuLich dddl where co = '0' and  ten like '%"+ ten +"%'";
			list = session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
	        session.getTransaction().rollback();
		}
		return list;	
	}
    
    public Boolean addPlace(PlaceForm placeForm,MultipartFile file){
        Boolean rs = false;
        Session session = this.sessionFactory.getCurrentSession();
        
        try {

            DiaDiemDuLich diaDiemDuLich = new DiaDiemDuLich();
            diaDiemDuLich.setDia_diem(placeForm.getDia_diem());
            diaDiemDuLich.setGia(placeForm.getGia());
            diaDiemDuLich.setTen(placeForm.getTen());
            diaDiemDuLich.setThong_tin(placeForm.getThong_tin());
            diaDiemDuLich.setHinh_anh(file.getBytes());
            diaDiemDuLich.setCo(0);
            session.save(diaDiemDuLich);
            session.flush();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            rs = false;
        } 
        return rs;
    }
    
    @SuppressWarnings("unchecked")
    public DiaDiemDuLich getDiadiemdulichbyTen(String ten){
        DiaDiemDuLich diaDiemDuLich = null;
        Session session = this.sessionFactory.getCurrentSession();
        try {
            String sql = "select dddl from DiaDiemDuLich dddl where co = '0' and  ten='"+ten + "'";
            List<DiaDiemDuLich> list = session.createQuery(sql).list();
            diaDiemDuLich = (list.size() == 0)? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return diaDiemDuLich;
    }
    
    public Boolean updatePlace(PlaceForm placeForm,MultipartFile file){
        Boolean rs = false;
        Session session = this.sessionFactory.getCurrentSession();
        try {
            DiaDiemDuLich diaDiemDuLich = (DiaDiemDuLich) session.get(DiaDiemDuLich.class, Integer.parseInt(placeForm.getMa()));
            if(diaDiemDuLich.getCo() == 0){
                diaDiemDuLich.setDia_diem(placeForm.getDia_diem());
                diaDiemDuLich.setGia(placeForm.getGia());
                diaDiemDuLich.setTen(placeForm.getTen());
                diaDiemDuLich.setThong_tin(placeForm.getThong_tin());
                if(file.getSize()>0)
                    diaDiemDuLich.setHinh_anh(file.getBytes());
                session.update(diaDiemDuLich);
                rs = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } 
        return rs;
    }
    public List<DiaDiemDuLich> getListDiaDiemDuLich(PlaceForm placeForm){
        List<DiaDiemDuLich> list = null;
        if(placeForm != null){
            Session session = this.sessionFactory.getCurrentSession();
            try {
                String sql = "select dddl from DiaDiemDuLich dddl where co = '0' ";
                
                if(placeForm.getTen() != null && !placeForm.getTen().equals("")){
                    sql += "and " + "dddl.ten like '%" + placeForm.getTen() + "%' ";
                }
                if(placeForm.getDia_diem()!= null && !placeForm.getDia_diem().equals("")){
                    sql += "and " + "dddl.dia_diem like '%" + placeForm.getDia_diem() + "%' ";
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
    public Boolean deletePlace(String id){
        Boolean rs = false;
        Session session = this.sessionFactory.getCurrentSession();
        try {
            DiaDiemDuLich diaDiemDuLich= (DiaDiemDuLich) session.get(DiaDiemDuLich.class, Integer.parseInt(id));
            diaDiemDuLich.setCo(1);
            session.update(diaDiemDuLich);
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } 
        return rs;
    }

}

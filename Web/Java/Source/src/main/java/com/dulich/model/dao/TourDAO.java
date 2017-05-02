package com.dulich.model.dao;

import java.text.ParseException;
import java.util.List;

import com.dulich.form.ChiPhiThucTeForm;
import org.springframework.web.multipart.MultipartFile;

import com.dulich.form.StatictisForm;
import com.dulich.form.TourForm;
import com.dulich.model.pojo.ChiPhiThucTe;
import com.dulich.model.pojo.Tour;
import com.dulich.model.pojo.TourThucTe;

public interface TourDAO {
	public List<Tour> getListTour() ;
	
	public TourForm getTourById(String id);
	public TourForm getTourByIDOfPDK(String id);
	
	public StatictisForm thongKeSLTourTheoNam_Thang(String year);
	public StatictisForm thongKeSLTourTheoNam_Qui(String year);
	public StatictisForm thongKeSLTourTheoNhieuNam(String startYear, String endYear);
	
	public StatictisForm thongKeDoanhThuTheoNam_Thang(String year);
	public StatictisForm thongKeDoanhThuTheoNam_Qui(String year);
	public StatictisForm thongKeDoanhThuTheoNhieuNam(String startYear, String endYear);
	
	public StatictisForm thongKeKhachTheoNam_Thang(String year);
	public StatictisForm thongKeKhachTheoNam_Qui(String year);
	public StatictisForm thongKeKhachTheoNhieuNam(String startYear, String endYear);
	
	public StatictisForm thongKeTienGocTheoNam_Thang(String year);
	public StatictisForm thongKeTienGocTheoNam_Qui(String year);
	public StatictisForm thongKeTienGocTheoNhieuNam(String startYear, String endYear);
	
	public List<Object> thongKeTourTheoNam_Thang(String month, String year);
	
	
	public List<TourForm> getListTour(TourForm tourform);
	
	public void duyetTour(int id_tour, int id_HDV, int status);
	public void huyTour(int id_tour);
	public Tour getTourByKey(String id_tour) ;
	public int addTour(TourForm tourForm,MultipartFile file) ; 
	
	public List<Tour> getListTour_Web() throws ParseException;
	
	public Tour getTourById_Web(Integer id);
	
	public void updateTourBySoVeConLai_Web(Tour tour);

	public List<Tour> getListTourByHDV(String id);

	public ChiPhiThucTe getChiPhiThucTe(String id);

	public void addChiPhiThucTe(ChiPhiThucTeForm chiphiForm);
	
	public Boolean updateTourOrigion(TourForm tourForm);

    public List<Tour> getListTourbyString(String name);

}


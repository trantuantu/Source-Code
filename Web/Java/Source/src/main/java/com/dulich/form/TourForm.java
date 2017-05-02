package com.dulich.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class TourForm {
	private Integer ma;
	private Integer ma_tour_goc;
	private Integer ma_huong_dan_vien;
	private String ma_phieu_dang_ki;
	private String ten_huong_dan_vien;
    @NotEmpty
	private String ten;
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private String ngay_di;
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private String ngay_ve;
    @NotNull
    @Min(value=10, message="Số vé lớn hơn 10")
	private Integer so_ve;
	private Integer so_ve_con_lai;
    @NotEmpty
	private String gioi_thieu;
    @NotEmpty
	private String chuong_trinh;
    @NotEmpty
	private String phuong_tien;
    
	private byte[] hinh_anh;

    @NotNull
    @Min(value=500000, message="Giá Tiền lớn hơn > 500000")
	private Integer gia;
	private Integer trang_thai;
	private Integer co;
	
	public TourForm() {
		super();
	}
	public TourForm(int trangthai) {
		super();
		trang_thai = trangthai;
	}
	
	public TourForm(Integer ma, Integer ma_tour_goc, Integer ma_huong_dan_vien, String ten_huong_dan_vien, String ten,
			String ngay_di, String ngay_ve, Integer so_ve, Integer so_ve_con_lai, String gioi_thieu,
			String chuong_trinh, String phuong_tien, Integer gia, Integer trang_thai, Integer co) {
		super();
		this.ma = ma;
		this.ma_tour_goc = ma_tour_goc;
		this.ma_huong_dan_vien = ma_huong_dan_vien;
		this.ten_huong_dan_vien = ten_huong_dan_vien;
		this.ten = ten;
		this.ngay_di = ngay_di;
		this.ngay_ve = ngay_ve;
		this.so_ve = so_ve;
		this.so_ve_con_lai = so_ve_con_lai;
		this.gioi_thieu = gioi_thieu;
		this.chuong_trinh = chuong_trinh;
		this.phuong_tien = phuong_tien;
		this.gia = gia;
		this.trang_thai = trang_thai;
		this.co = co;
	}
	
	
	public String getMa_phieu_dang_ki() {
		return ma_phieu_dang_ki;
	}
	public void setMa_phieu_dang_ki(String ma_phieu_dang_ki) {
		this.ma_phieu_dang_ki = ma_phieu_dang_ki;
	}
	public Integer getMa() {
		return ma;
	}
	public void setMa(Integer ma) {
		this.ma = ma;
	}
	public Integer getMa_tour_goc() {
		return ma_tour_goc;
	}
	public void setMa_tour_goc(Integer ma_tour_goc) {
		this.ma_tour_goc = ma_tour_goc;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNgay_di() {
		return ngay_di;
	}
	public void setNgay_di(String ngay_di) {
		this.ngay_di = ngay_di;
	}
	public String getNgay_ve() {
		return ngay_ve;
	}
	public void setNgay_ve(String ngay_ve) {
		this.ngay_ve = ngay_ve;
	}
	public Integer getSo_ve() {
		return so_ve;
	}
	public void setSo_ve(Integer so_ve) {
		this.so_ve = so_ve;
	}
	public Integer getSo_ve_con_lai() {
		return so_ve_con_lai;
	}
	public void setSo_ve_con_lai(Integer so_ve_con_lai) {
		this.so_ve_con_lai = so_ve_con_lai;
	}
	public String getGioi_thieu() {
		return gioi_thieu;
	}
	public void setGioi_thieu(String gioi_thieu) {
		this.gioi_thieu = gioi_thieu;
	}
	public String getChuong_trinh() {
		return chuong_trinh;
	}
	public void setChuong_trinh(String chuong_trinh) {
		this.chuong_trinh = chuong_trinh;
	}
	public String getPhuong_tien() {
		return phuong_tien;
	}
	public void setPhuong_tien(String phuong_tien) {
		this.phuong_tien = phuong_tien;
	}
	public byte[] getHinh_anh() {
		return hinh_anh;
	}
	public void setHinh_anh(byte[] hinh_anh) {
		this.hinh_anh = hinh_anh;
	}
	public Integer getGia() {
		return gia;
	}
	public void setGia(Integer gia) {
		this.gia = gia;
	}
	public Integer getCo() {
		return co;
	}
	public void setCo(Integer co) {
		this.co = co;
	}
	public Integer getMa_huong_dan_vien() {
		return ma_huong_dan_vien;
	}
	public void setMa_huong_dan_vien(Integer ma_huong_dan_vien) {
		this.ma_huong_dan_vien = ma_huong_dan_vien;
	}
	public String getTen_huong_dan_vien() {
		return ten_huong_dan_vien;
	}
	public void setTen_huong_dan_vien(String ten_huong_dan_vien) {
		this.ten_huong_dan_vien = ten_huong_dan_vien;
	}
	public Integer getTrang_thai() {
		return trang_thai;
	}
	public void setTrang_thai(Integer trang_thai) {
		this.trang_thai = trang_thai;
	}	
}

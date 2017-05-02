package com.dulich.form;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class PhieuDangKiForm {

	private String Ma;
	private String MaTour;
	private String MaKhachHang;
	
	@Size(min=3, max=50)
	private String TenTour;
	
	@Size(min=3, max=50)
	private String TenKhachHang;
	
	
	@Min(value=0, message="Tổng tiền sai")
	@Max(value=999999999, message="Tổng tiềnsai")
	private int TongTien;
	
	@Min(value=0, message="Tiền cọc sai")
	@Max(value=999999999, message="Tiền cọc sai")
	private int TienCoc;
	
	@Min(value=1, message="Số lượng người sai")
	@Max(value=1000, message="Số lượng người sai")
	private int SLNguoi;

	@Size(min=0, max=10000)
	private String GhiChu;
	
	
	@Column(name="chinh_sua", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ChinhSua;

	@Min(value=0, message="Trạng thái sai")
	@Max(value=4, message="Trạng thái sai")
	private int TrangThai;
	
	
	public PhieuDangKiForm() {
		super();
	}
	
	public PhieuDangKiForm(String ma, String maTour, String maKhachHang, String tenTour,
			String tenKhachHang, int tongTien, int tienCoc, int sLNguoi, String ghiChu, boolean chinhSua,
			int trangThai) {
		super();
		Ma = ma;
		MaTour = maTour;
		MaKhachHang = maKhachHang;
		TenTour = tenTour;
		TenKhachHang = tenKhachHang;
		TongTien = tongTien;
		TienCoc = tienCoc;
		SLNguoi = sLNguoi;
		GhiChu = ghiChu;
		ChinhSua = chinhSua;
		TrangThai = trangThai;
	}
	public PhieuDangKiForm getPDK(){
		return this;
	}
	
	public int isTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(int trangThai) {
		TrangThai = trangThai;
	}

	public String getMa() {
		return Ma;
	}


	public void setMa(String ma) {
		Ma = ma;
	}


	public String getMaTour() {
		return MaTour;
	}


	public void setMaTour(String maTour) {
		MaTour = maTour;
	}


	public String getMaKhachHang() {
		return MaKhachHang;
	}


	public void setMaKhachHang(String maKhachHang) {
		MaKhachHang = maKhachHang;
	}



	public String getTenTour() {
		return TenTour;
	}


	public void setTenTour(String tenTour) {
		TenTour = tenTour;
	}


	public String getTenKhachHang() {
		return TenKhachHang;
	}


	public void setTenKhachHang(String tenKhachHang) {
		TenKhachHang = tenKhachHang;
	}



	public int getTongTien() {
		return TongTien;
	}


	public void setTongTien(int tongTien) {
		TongTien = tongTien;
	}


	public int getTienCoc() {
		return TienCoc;
	}


	public void setTienCoc(int tienCoc) {
		TienCoc = tienCoc;
	}


	public int getSLNguoi() {
		return SLNguoi;
	}


	public void setSLNguoi(int sLNguoi) {
		SLNguoi = sLNguoi;
	}


	public String getGhiChu() {
		return GhiChu;
	}


	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}


	public boolean getChinhSua() {
		return ChinhSua;
	}


	public void setChinhSua(boolean chinhSua) {
		ChinhSua = chinhSua;
	}
	

	
	
}

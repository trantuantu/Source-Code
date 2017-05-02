package com.dulich.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class UserForm{
		
	private String Ma;
	
	@NotNull
	@Min(value=0, message="Chọn sai loại nhân viên")
	@Max(value=2, message="Chọn sai loại nhân viên")
	private int Loai;
	
	@NotEmpty
	@Pattern(regexp="^[a-z0-9_-]{3,15}$")  
	private String TaiKhoan;

	@NotEmpty
	@Size(min=5, max=50)
	private String HoTen;
	
	
	private String DiaChi;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private String NamSinh;
	
	@Size(min=1,max=1)
	private String GioiTinh;
	

	private int NghiPhep;
	
	@Pattern(regexp="^[0-9]{9,12}|^$")
	private String SoDT;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{9,12}|^$")
	private String CMND;
	
	
	
	
	public UserForm(String ma, String taiKhoan, String hoTen, String diaChi, String namSinh, String gioiTinh) {
		super();
		TaiKhoan = taiKhoan;
		HoTen = hoTen;
		DiaChi = diaChi;
		NamSinh = namSinh;
		GioiTinh = gioiTinh;
		Ma = ma;
	}
	public int getNghiPhep() {
		return NghiPhep;
	}
	public void setNghiPhep(int nghiPhep) {
		NghiPhep = nghiPhep;
	}
	public UserForm(int loai) {
		super();
		Loai = loai;
	}
	public UserForm(int loai, int nghiphep) {
		super();
		Loai = loai;
		NghiPhep = nghiphep;
	}
	public UserForm() {
		super();
	}
	
	public String getSoDT() {
		return SoDT;
	}
	public void setSoDT(String soDT) {
		SoDT = soDT;
	}
	public String getCMND() {
		return CMND;
	}
	public void setCMND(String cMND) {
		CMND = cMND;
	}
	public String getMa() {
		return Ma;
	}
	public void setMa(String ma) {
		Ma = ma;
	}
	public int  getLoai() {
		return Loai;
	}
	public void setLoai(int  loai) {
		Loai = loai;
	}
	public String getTaiKhoan() {
		return TaiKhoan;
	}
	public void setTaiKhoan(String taiKhoan) {
		TaiKhoan = taiKhoan;
	}
	public String getHoTen() {
		return HoTen;
	}
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	public String getDiaChi() {
		return DiaChi;
	}
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
	public String getNamSinh() {
		return NamSinh;
	}
	public void setNamSinh(String namSinh) {
		NamSinh = namSinh;
	}
	public String getGioiTinh() {
		return GioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}
	
}

package com.dulich.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class KhachHangForm {
	
	private String Ma;

	@NotEmpty
	@Size(min=5, max=50)
	private String HoTen;
	
	
	private String DiaChi;
	
//	@DateTimeFormat(pattern = "dd/MM/yyyy") 
//	private String NamSinh;
//	
//	@Size(min=1,max=1)
//	private String GioiTinh;
	
	@Pattern(regexp="^[0-9]{9,12}|^$")
	private String SoDT;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{9,12}|^$")
	private String CMND;

	public String getMa() {
		return Ma;
	}

	public void setMa(String ma) {
		Ma = ma;
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
	
	
}

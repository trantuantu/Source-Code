package com.dulich.form;

import javax.persistence.Column;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

public class NghiPhepForm {
	
	private String Ma;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private String NgayNghi;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private String NgayDiLam;
	
	private boolean XacNhan;

	public String getMa() {
		return Ma;
	}

	public void setMa(String ma) {
		Ma = ma;
	}

	public String getNgayNghi() {
		return NgayNghi;
	}

	public void setNgayNghi(String ngayNghi) {
		NgayNghi = ngayNghi;
	}

	public String getNgayDiLam() {
		return NgayDiLam;
	}

	public void setNgayDiLam(String ngayDiLam) {
		NgayDiLam = ngayDiLam;
	}

	public boolean isXacNhan() {
		return XacNhan;
	}

	public void setXacNhan(boolean xacNhan) {
		XacNhan = xacNhan;
	}
	
	
}

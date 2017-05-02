package com.dulich.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class RestaurantForm {
	
	private String Ma;

	@NotNull
	@Min(value=0, message="Chọn sai loại khách sạn")
	@Max(value=5, message="Chọn sai loại khách sạn")
	private int Loai;
	
	@NotEmpty
	@Size(min=5, max=50)
	private String Ten;
	
	@NotEmpty
	@Size(min=5, max=100)
	private String DiaChi;
	
	public RestaurantForm(int loai) {
		super();
		Loai = loai;
	}
	public RestaurantForm() {
		super();
	}
	public String getMa() {
		return Ma;
	}
	public void setMa(String ma) {
		Ma = ma;
	}
	public int getLoai() {
		return Loai;
	}
	public void setLoai(int loai) {
		Loai = loai;
	}
	public String getTen() {
		return Ten;
	}
	public void setTen(String ten) {
		Ten = ten;
	}
	public String getDiaChi() {
		return DiaChi;
	}
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
}

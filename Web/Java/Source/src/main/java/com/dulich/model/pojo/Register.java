package com.dulich.model.pojo;

public class Register {
	private String ma_tour;
	private String ten_tour;
	private String so_luong;
	private String ten_khach_hang;
	private String sdt;
	private String cmnd;
	private String dia_chi;
	private String ghi_chu;
	private String chinh_sua;
	
	public Register() {
		super();
	}

	public Register(String ma_tour, String ten_tour, String so_luong, String ten_khach_hang, String sdt, String cmnd,
			String dia_chi, String ghi_chu, String chinh_sua) {
		super();
		this.ma_tour = ma_tour;
		this.ten_tour = ten_tour;
		this.so_luong = so_luong;
		this.ten_khach_hang = ten_khach_hang;
		this.sdt = sdt;
		this.cmnd = cmnd;
		this.dia_chi = dia_chi;
		this.ghi_chu = ghi_chu;
		this.chinh_sua = chinh_sua;
	}

	public String getMa_tour() {
		return ma_tour;
	}

	public void setMa_tour(String ma_tour) {
		this.ma_tour = ma_tour;
	}

	public String getTen_tour() {
		return ten_tour;
	}

	public void setTen_tour(String ten_tour) {
		this.ten_tour = ten_tour;
	}

	public String getSo_luong() {
		return so_luong;
	}

	public void setSo_luong(String so_luong) {
		this.so_luong = so_luong;
	}

	public String getTen_khach_hang() {
		return ten_khach_hang;
	}

	public void setTen_khach_hang(String ten_khach_hang) {
		this.ten_khach_hang = ten_khach_hang;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getDia_chi() {
		return dia_chi;
	}

	public void setDia_chi(String dia_chi) {
		this.dia_chi = dia_chi;
	}

	public String getGhi_chu() {
		return ghi_chu;
	}

	public void setGhi_chu(String ghi_chu) {
		this.ghi_chu = ghi_chu;
	}
	
	public String getChinh_sua() {
		return chinh_sua;
	}

	public void setChinh_sua(String chinh_sua) {
		this.chinh_sua = chinh_sua;
	}
}

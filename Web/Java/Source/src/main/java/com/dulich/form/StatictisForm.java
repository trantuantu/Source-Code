package com.dulich.form;

import java.util.List;

public class StatictisForm {
	
	private int[] SLTour_thang = new int[12];
	private int[] Khach_thang = new int[12];
	private float[] DoanhThu_thang = new float[12];
	private float[] TienGoc_thang = new float[12];
	private float[] LoiNhuan_thang = new float[12];
	private String[] thang = new String[12];
	
	private int[] SLTour_qui = new int[4];
	private int[] Khach_qui = new int[4];
	private float[] DoanhThu_qui = new float[4];
	private float[] TienGoc_qui = new float[4];
	private float[] LoiNhuan_qui = new float[4];
	private String[] qui = new String[4];
	
	private int[] SLTour_nhieunam = new int[10];
	private int[] Khach_nhieunam = new int[10];
	private float[] DoanhThu_nhieunam  = new float[10];
	private float[] TienGoc_nhieunam  = new float[10];
	private float[] LoiNhuan_nhieunam = new float[10];
	private String[] nhieunam  = new String[10];
		
	public StatictisForm() {
		super();
		for(int i = 0; i < 12; i++){
			this.thang[i] = "Tháng " + (i + 1);
		}
		for(int i = 0; i < 4; i++){
			this.qui[i] = "Quí " + (i + 1);
		}
	}
	
	
	public float[] getLoiNhuan_thang() {
		return LoiNhuan_thang;
	}


	public void setLoiNhuan_thang(float[] loiNhuan_thang) {
		LoiNhuan_thang = loiNhuan_thang;
	}


	public float[] getLoiNhuan_qui() {
		return LoiNhuan_qui;
	}


	public void setLoiNhuan_qui(float[] loiNhuan_qui) {
		LoiNhuan_qui = loiNhuan_qui;
	}


	public float[] getLoiNhuan_nhieunam() {
		return LoiNhuan_nhieunam;
	}


	public void setLoiNhuan_nhieunam(float[] loiNhuan_nhieunam) {
		LoiNhuan_nhieunam = loiNhuan_nhieunam;
	}


	public float[] getTienGoc_thang() {
		return TienGoc_thang;
	}


	public void setTienGoc_thang(float[] tienGoc_thang) {
		TienGoc_thang = tienGoc_thang;
	}


	public float[] getTienGoc_qui() {
		return TienGoc_qui;
	}


	public void setTienGoc_qui(float[] tienGoc_qui) {
		TienGoc_qui = tienGoc_qui;
	}


	public float[] getTienGoc_nhieunam() {
		return TienGoc_nhieunam;
	}


	public void setTienGoc_nhieunam(float[] tienGoc_nhieunam) {
		TienGoc_nhieunam = tienGoc_nhieunam;
	}


	public int[] getSLTour_thang() {
		return SLTour_thang;
	}
	public void setSLTour_thang(int[] sLTour_thang) {
		SLTour_thang = sLTour_thang;
	}
	public float[] getDoanhThu_thang() {
		return DoanhThu_thang;
	}
	public void setDoanhThu_thang(float[] doanhThu_thang) {
		DoanhThu_thang = doanhThu_thang;
	}
	public String[] getThang() {
		return thang;
	}
	public void setThang(String[] thang) {
		this.thang = thang;
	}
	public int[] getSLTour_qui() {
		return SLTour_qui;
	}
	public void setSLTour_qui(int[] sLTour_qui) {
		SLTour_qui = sLTour_qui;
	}
	public float[] getDoanhThu_qui() {
		return DoanhThu_qui;
	}
	public void setDoanhThu_qui(float[] doanhThu_qui) {
		DoanhThu_qui = doanhThu_qui;
	}
	public String[] getQui() {
		return qui;
	}
	public void setQui(String[] qui) {
		this.qui = qui;
	}
	public int[] getSLTour_nhieunam() {
		return SLTour_nhieunam;
	}
	public void setSLTour_nhieunam(int[] sLTour_nhieunam) {
		SLTour_nhieunam = sLTour_nhieunam;
	}
	public float[] getDoanhThu_nhieunam() {
		return DoanhThu_nhieunam;
	}
	public void setDoanhThu_nhieunam(float[] doanhThu_nhieunam) {
		DoanhThu_nhieunam = doanhThu_nhieunam;
	}
	public String[] getNhieunam() {
		return nhieunam;
	}
	public void setNhieunam(String[] nhieunam) {
		this.nhieunam = nhieunam;
	}
	public int[] getKhach_thang() {
		return Khach_thang;
	}
	public void setKhach_thang(int[] khach_thang) {
		Khach_thang = khach_thang;
	}
	public int[] getKhach_qui() {
		return Khach_qui;
	}
	public void setKhach_qui(int[] khach_qui) {
		Khach_qui = khach_qui;
	}
	public int[] getKhach_nhieunam() {
		return Khach_nhieunam;
	}
	public void setKhach_nhieunam(int[] khach_nhieunam) {
		Khach_nhieunam = khach_nhieunam;
	}
	
	
}

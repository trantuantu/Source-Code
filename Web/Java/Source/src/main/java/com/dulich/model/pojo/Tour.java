package com.dulich.model.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "tour", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class Tour {
	private Integer ma;
	private String ten;
	private String ngay_di;
	private String ngay_ve;
	private Integer so_ve;
	private Integer so_ve_con_lai;
	private String gioi_thieu;
	private String chuong_trinh;
	private String phuong_tien;
	private byte[] hinh_anh;
	private Integer gia;
	private Integer co;
	private User huong_dan_vien;
    private Set<PhieuDangKi> listPDK = new HashSet<com.dulich.model.pojo.PhieuDangKi>(0);
	
	public Tour() {
		super();
	}



	public Tour(Integer ma, String ten, String ngay_di, String ngay_ve, Integer so_ve, Integer so_ve_con_lai,
			String gioi_thieu, String chuong_trinh, String phuong_tien, byte[] hinh_anh, Integer gia, Integer co,
			User user) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.ngay_di = ngay_di;
		this.ngay_ve = ngay_ve;
		this.so_ve = so_ve;
		this.so_ve_con_lai = so_ve_con_lai;
		this.gioi_thieu = gioi_thieu;
		this.chuong_trinh = chuong_trinh;
		this.phuong_tien = phuong_tien;
		this.hinh_anh = hinh_anh;
		this.gia = gia;
		this.co = co;
		this.huong_dan_vien = user;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ma_huong_dan_vien", nullable=true)
	public User getHuong_dan_vien() {
		return huong_dan_vien;
	}



	public void setHuong_dan_vien(User ma_huong_dan_vien) {
		this.huong_dan_vien = ma_huong_dan_vien;
	}



	@Id
	@Column(name = "ma")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getMa() {
		return ma;
	}




	public void setMa(Integer ma) {
		this.ma = ma;
	}
	
	@Column(name = "ten", nullable = false)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
	

	@Column(name = "ngay_di", nullable = false)
	public String getNgay_di() {
		return ngay_di;
	}

	public void setNgay_di(String ngay_di) {
		this.ngay_di = ngay_di;
	}

	@Column(name = "ngay_ve", nullable = false)
	public String getNgay_ve() {
		return ngay_ve;
	}

	public void setNgay_ve(String ngay_ve) {
		this.ngay_ve = ngay_ve;
	}

	@Column(name = "so_ve", nullable = false)
	public Integer getSo_ve() {
		return so_ve;
	}

	public void setSo_ve(Integer so_ve) {
		this.so_ve = so_ve;
	}

	@Column(name = "so_ve_con_lai", nullable = false)
	public Integer getSo_ve_con_lai() {
		return so_ve_con_lai;
	}

	public void setSo_ve_con_lai(Integer so_ve_con_lai) {
		this.so_ve_con_lai = so_ve_con_lai;
	}

	@Column(name = "gioi_thieu", nullable = false)
	public String getGioi_thieu() {
		return gioi_thieu;
	}

	public void setGioi_thieu(String gioi_thieu) {
		this.gioi_thieu = gioi_thieu;
	}

	@Column(name = "chuong_trinh", nullable = false)
	public String getChuong_trinh() {
		return chuong_trinh;
	}

	public void setChuong_trinh(String chuong_trinh) {
		this.chuong_trinh = chuong_trinh;
	}

	@Column(name = "phuong_tien", nullable = false)
	public String getPhuong_tien() {
		return phuong_tien;
	}

	public void setPhuong_tien(String phuong_tien) {
		this.phuong_tien = phuong_tien;
	}

	
	
	
	public byte[] getHinh_anh() {
		return hinh_anh;
	}


	@Column(name = "hinh_anh", nullable = false)
	public void setHinh_anh(byte[] hinh_anh) {
		this.hinh_anh = hinh_anh;
	}



	@Column(name = "gia", nullable = false)
	public Integer getGia() {
		return gia;
	}

	public void setGia(Integer gia) {
		this.gia = gia;
	}

	@Column(columnDefinition = "TINYINT")
	public Integer getCo() {
		return co;
	}

	public void setCo(Integer co) {
		this.co = co;
	}


    @OneToMany(mappedBy="MaTour",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
	public Set<PhieuDangKi> getListPDK() {
		return listPDK;
	}



	public void setListPDK(Set<PhieuDangKi> listPDK) {
		this.listPDK = listPDK;
	}
}

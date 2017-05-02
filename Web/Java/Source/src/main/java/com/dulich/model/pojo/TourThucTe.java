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

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "tourthucte", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class TourThucTe {
	private Integer ma;
	private Integer ma_tour_goc;
	//private Integer ma_dang_ky;
	private String ten;
	private String ngay_di;
	private String ngay_ve;
	private String gioi_thieu;
	private String chuong_trinh;
	private String phuong_tien;
	private Integer gia;
	
	@Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean co;
	
	private User huong_dan_vien;
	private Integer so_ve;
	private Integer so_ve_con_lai;
	
	private Set<PhieuDangKi> listPDK = new HashSet<com.dulich.model.pojo.PhieuDangKi>(0);

	public TourThucTe() {
	}
	
	public TourThucTe(Integer ma, Integer ma_tour_goc, String ten, String ngay_di, String ngay_ve, Integer so_ve,
			Integer so_ve_con_lai, String chuong_trinh,String gioi_thieu, String phuong_tien,
			Integer gia, boolean co, User huong_dan_vien, Set<PhieuDangKi> listPDK) {
		super();
		this.ma = ma;
		this.ma_tour_goc = ma_tour_goc;
		this.ten = ten;
		this.ngay_di = ngay_di;
		this.ngay_ve = ngay_ve;
		
		this.gioi_thieu = gioi_thieu;
		this.chuong_trinh = chuong_trinh;
		this.phuong_tien = phuong_tien;
		
		this.gia = gia;
		this.co = co;
		this.huong_dan_vien = huong_dan_vien;
		this.listPDK = listPDK;
		//this.ma_dang_ky = ma_dang_ky;
	}




	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ma_huong_dan_vien", nullable=true)
	public User getHuong_dan_vien() {
		return huong_dan_vien;
	}



	public void setHuong_dan_vien(User ma_huong_dan_vien) {
		this.huong_dan_vien = ma_huong_dan_vien;
	}
	
	public Integer getMa_tour_goc() {
		return ma_tour_goc;
	}

	public void setMa_tour_goc(Integer ma_tour_goc) {
		this.ma_tour_goc = ma_tour_goc;
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

	
//	@Column(name = "ma_dang_ky", nullable=false)
//	public Integer getMa_dang_ky() {
//		return ma_dang_ky;
//	}
//
//	public void setMa_dang_ky(Integer ma_dang_ky) {
//		this.ma_dang_ky = ma_dang_ky;
//	}

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

	@Column(name = "gia", nullable = false)
	public Integer getGia() {
		return gia;
	}

	public void setGia(Integer gia) {
		this.gia = gia;
	}

	
	public boolean getCo() {
		return co;
	}

	public void setCo(boolean co) {
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

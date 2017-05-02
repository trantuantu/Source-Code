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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "phieudangky", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class PhieuDangKi {

	@Id
    @Column(name = "ma")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Ma;
	
	@NotNull
    @Column(name = "ma_tour",  nullable = false)
	private Integer MaTour;
	
	@NotNull
    @Column(name = "ma_khach_hang",  nullable = false)
	private Integer MaKhachHang;
	
	
	
    @Column(name = "tong_tien",  nullable = true)
	private Integer TongTien;
	
    @Column(name = "tien_coc",  nullable = true)
	private Integer TienCoc;
	
	@NotNull
    @Column(name = "so_luong_nguoi",  nullable = false)
	private Integer SLNguoi;
	
    @Column(name = "ghi_chu",  nullable = true)
	private String GhiChu;
	
	@Column(name="trang_thai")
	private Integer TrangThai;
	
	@Column(name="co", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;
	
	@Column(name="chinh_sua", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ChinhSua;
		
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="MaTour")
//	 private Tour Tour;
	
	public PhieuDangKi() {
		super();
	}

	public PhieuDangKi(Integer ma, Integer maTour, Integer maKhachHang, Integer tongTien, Integer tienCoc,
			Integer sLNguoi, String ghiChu, Integer trangThai, boolean co, boolean chinhSua) {
		super();
		Ma = ma;
		MaTour = maTour;
		MaKhachHang = maKhachHang;
		TongTien = tongTien;
		TienCoc = tienCoc;
		SLNguoi = sLNguoi;
		GhiChu = ghiChu;
		TrangThai = trangThai;
		Co = co;
		ChinhSua = chinhSua;
	}

	public Integer getMa() {
		return Ma;
	}

	public void setMa(Integer ma) {
		Ma = ma;
	}

	public Integer getMaTour() {
		return MaTour;
	}

	public void setMaTour(Integer maTour) {
		MaTour = maTour;
	}

	public Integer getMaKhachHang() {
		return MaKhachHang;
	}

	public void setMaKhachHang(Integer maKhachHang) {
		MaKhachHang = maKhachHang;
	}


	public Integer getTongTien() {
		return TongTien;
	}

	public void setTongTien(Integer tongTien) {
		TongTien = tongTien;
	}

	public Integer getTienCoc() {
		return TienCoc;
	}

	public void setTienCoc(Integer tienCoc) {
		TienCoc = tienCoc;
	}

	public Integer getSLNguoi() {
		return SLNguoi;
	}

	public void setSLNguoi(Integer sLNguoi) {
		SLNguoi = sLNguoi;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public Integer isTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(Integer trangThai) {
		TrangThai = trangThai;
	}

	public boolean isCo() {
		return Co;
	}

	public void setCo(boolean co) {
		Co = co;
	}

	public boolean isChinhSua() {
		return ChinhSua;
	}

	public void setChinhSua(boolean chinhSua) {
		ChinhSua = chinhSua;
	}
	
	
	
}

package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "lichnghiphep", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) }	)
public class NghiPhep {

	public NghiPhep() {
		super();
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	
	@NotNull
    @Column(name = "ma_nhan_vien", nullable = false)
	private String MaNV;
	
	@NotNull
    @Column(name = "ngay_nghi",  nullable = false)
	private String NgayNghi;
	
	@NotNull
    @Column(name = "ngay_di_lam",  nullable = false)
	private String NgayDiLam;
	
	@Column(name="xac_nhan", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean XacNhan;
	
	@Column(name="co", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public NghiPhep(int id, String maNV, String ngayNghi, String ngayDiLam, boolean xacNhan, boolean co) {
		super();
		Id = id;
		MaNV = maNV;
		NgayNghi = ngayNghi;
		NgayDiLam = ngayDiLam;
		XacNhan = xacNhan;
		Co = co;
	}

	public String getMaNV() {
		return MaNV;
	}

	public void setMaNV(String maNV) {
		MaNV = maNV;
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

	public boolean isCo() {
		return Co;
	}

	public void setCo(boolean co) {
		Co = co;
	}
	
	
}

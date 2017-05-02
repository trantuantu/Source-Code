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
@Table(name = "khachhang", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class KhachHang {
	
	@Id
    @Column(name = "ma")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Ma;
	
	@NotNull
    @Column(name = "ten",  nullable = false)
	private String Ten;
	
	@NotNull
    @Column(name = "sdt",  nullable = false)
	private String SDT;
	
	@NotNull
    @Column(name = "cmnd",  nullable = false)
	private String CMND;
	
	@NotNull
	@Column(name = "dia_chi",  nullable = true)
	private String DiaChi;
	
	@NotNull
	@Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;
	
	

	public KhachHang(int ma, String ten, String sDT, String cMND, String diaChi, boolean co) {
		super();
		Ma = ma;
		Ten = ten;
		SDT = sDT;
		CMND = cMND;
		DiaChi = diaChi;
		Co = co;
	}

	public int getMa() {
		return Ma;
	}

	public void setMa(int ma) {
		Ma = ma;
	}

	public String getTen() {
		return Ten;
	}

	public void setTen(String ten) {
		Ten = ten;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		CMND = cMND;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public boolean isCo() {
		return Co;
	}

	public void setCo(boolean co) {
		Co = co;
	}
	
	
	
}

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
@Table(name = "khachsan", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class Hotel {
	@Id
    @Column(name = "ma")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Ma;
	
	@NotNull
    @Column(name = "loai",  nullable = false)
	private String Loai;
	
    @NotNull
    @Column(name = "ten",  nullable = false)
	private String Ten;
    
    @NotNull
    @Column(name = "dia_chi",  nullable = true)
	private String DiaChi;
    
    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;

	public int getMa() {
		return Ma;
	}

	public void setMa(int ma) {
		Ma = ma;
	}

	public String getLoai() {
		return Loai;
	}

	public void setLoai(String loai) {
		Loai = loai;
	}

	public String getTen() {
		return Ten;
	}

	public void setTen(String _Ten) {
		Ten = _Ten;
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

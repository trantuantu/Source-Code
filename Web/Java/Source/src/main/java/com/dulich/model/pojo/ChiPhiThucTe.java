package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "chiphithucte", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma_tour" }) })
public class ChiPhiThucTe {

	@Id
    @Column(name = "ma_tour")
	private int MaTour;
	
	@Column(name = "chi_phi_di_chuyen",  nullable = false)
	private String ChiPhiDiChuyen;
	
	@Column(name = "chi_phi_an_uong",  nullable = false)
	private String ChiPhiAnUong;
	
	@Column(name = "chi_phi_o",  nullable = false)
	private String ChiPhiO;
	
	@Column(name = "chi_phi_phat_sinh",  nullable = false)
	private String ChiPhiPhatSinh;
	
	@Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;

	
	
	public ChiPhiThucTe() {
		super();
	}

	public ChiPhiThucTe(int maTour, String chiPhiDiChuyen, String chiPhiAnUong, String chiPhiO, String chiPhiPhatSinh,
			boolean co) {
		super();
		MaTour = maTour;
		ChiPhiDiChuyen = chiPhiDiChuyen;
		ChiPhiAnUong = chiPhiAnUong;
		ChiPhiO = chiPhiO;
		ChiPhiPhatSinh = chiPhiPhatSinh;
		Co = co;
	}

	public int getMaTour() {
		return MaTour;
	}

	public void setMaTour(int maTour) {
		MaTour = maTour;
	}

	public String getChiPhiDiChuyen() {
		return ChiPhiDiChuyen;
	}

	public void setChiPhiDiChuyen(String chiPhiDiChuyen) {
		ChiPhiDiChuyen = chiPhiDiChuyen;
	}

	public String getChiPhiAnUong() {
		return ChiPhiAnUong;
	}

	public void setChiPhiAnUong(String chiPhiAnUong) {
		ChiPhiAnUong = chiPhiAnUong;
	}

	public String getChiPhiO() {
		return ChiPhiO;
	}

	public void setChiPhiO(String chiPhiO) {
		ChiPhiO = chiPhiO;
	}

	public String getChiPhiPhatSinh() {
		return ChiPhiPhatSinh;
	}

	public void setChiPhiPhatSinh(String chiPhiPhatSinh) {
		ChiPhiPhatSinh = chiPhiPhatSinh;
	}

	public boolean isCo() {
		return Co;
	}

	public void setCo(boolean co) {
		Co = co;
	}
	
	
}

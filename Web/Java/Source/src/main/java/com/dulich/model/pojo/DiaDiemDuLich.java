package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "diadiemdulich", uniqueConstraints = { @UniqueConstraint(columnNames = { "ten" }) })
public class DiaDiemDuLich {
	private Integer ma;
	private String ten;
	private Integer gia;
	private String dia_diem;
	private String thong_tin;
    private byte[] hinh_anh;
	private Integer co;
	
	public DiaDiemDuLich() {
		super();
	}
	
    public DiaDiemDuLich(Integer ma, String ten, Integer gia, String dia_diem, String thong_tin,  byte[] hinh_anh,
			Integer co) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.gia = gia;
		this.dia_diem = dia_diem;
		this.thong_tin = thong_tin;
		this.hinh_anh = hinh_anh;
		this.co = co;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma")
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

	@Column(name = "gia", nullable = false)
	public Integer getGia() {
		return gia;
	}

	public void setGia(Integer gia) {
		this.gia = gia;
	}

	@Column(name = "dia_diem", nullable = false)
	public String getDia_diem() {
		return dia_diem;
	}

	public void setDia_diem(String dia_diem) {
		this.dia_diem = dia_diem;
	}

	@Column(name = "thong_tin", nullable = false)
	public String getThong_tin() {
		return thong_tin;
	}

	public void setThong_tin(String thong_tin) {
		this.thong_tin = thong_tin;
	}

	@Column(name = "hinh_anh", nullable = false)
    public  byte[] getHinh_anh() {
		return hinh_anh;
	}

    public void setHinh_anh( byte[] hinh_anh) {
		this.hinh_anh = hinh_anh;
	}

	@Column(name = "co", nullable = false)
	public Integer getCo() {
		return co;
	}

	public void setCo(Integer co) {
		this.co = co;
	}
}

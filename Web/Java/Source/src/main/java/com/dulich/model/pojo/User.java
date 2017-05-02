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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "nhanvien", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class User {
	
    @Id
    @Column(name = "ma")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Ma;
    
    @NotNull
    @Column(name = "loai",  nullable = false)
	private int Loai;
    
    @NotNull
    @Column(name = "tai_khoan",  nullable = false)
    private String TaiKhoan;
    
    @NotNull
    @Column(name = "ten",  nullable = false)
	private String HoTen;
    
    @Column(name = "dia_chi",  nullable = true)
	private String DiaChi;
    
    @DateTimeFormat(pattern="dd/MM/yyyy") 
    @Column(name = "namsinh",  nullable = true)
	private String NamSinh;
    
    @Column(name = "gioitinh",  nullable = true)
	private String GioiTinh;
    
    @NotNull
    @Column(name = "mat_khau",  nullable = false)
	private String MatKhau;
    
    @NotNull
    @Column(name = "cmnd",  nullable = true)
    private String CMND;
    
    @Column(name = "sdt",  nullable = true)
	private String SoDienThoai;
    
    @Column(name = "co",columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean Co;
	
//    @OneToMany(mappedBy="ma_huong_dan_vien",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
    // private Set<Tour> Tour = new HashSet<com.dulich.model.pojo.Tour>();

    
    public User() {
    }
	
    public User(int ma, int loai, String taiKhoan, String hoTen, String diaChi, String namSinh, String gioiTinh,
			String matKhau, String soDienThoai, boolean co, String cMND) {
		super();
		Ma = ma;
		Loai = loai;
		TaiKhoan = taiKhoan;
		HoTen = hoTen;
		DiaChi = diaChi;
		NamSinh = namSinh;
		GioiTinh = gioiTinh;
		MatKhau = matKhau;
		SoDienThoai = soDienThoai;
		Co = co;
		CMND = cMND;
	}



	public int getMa() {
		return Ma;
	}



	public void setMa(Integer ma) {
		Ma = ma;
	}



	public Integer getLoai() {
		return Loai;
	}



	public void setLoai(Integer loai) {
		Loai = loai;
	}



	public String getTaiKhoan() {
		return TaiKhoan;
	}



	public void setTaiKhoan(String taiKhoan) {
		TaiKhoan = taiKhoan;
	}



	public String getHoTen() {
		return HoTen;
	}



	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}



	public String getDiaChi() {
		return DiaChi;
	}



	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}



	public String getNamSinh() {
		return NamSinh;
	}



	public void setNamSinh(String namSinh) {
		NamSinh = namSinh;
	}



	public String getGioiTinh() {
		return GioiTinh;
	}



	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}



	public String getMatKhau() {
		return MatKhau;
	}



	public void setMatKhau(String matKhau) {
		MatKhau = matKhau;
	}



	public String getSoDienThoai() {
		return SoDienThoai;
	}



	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
	}



	public boolean getCo() {
		return Co;
	}



	public void setCo(boolean co) {
		Co = co;
	}



	public String getCMND() {
		return CMND;
	}



	public void setCMND(String cMND) {
		CMND = cMND;
	}


	public void setMa(int ma) {
		Ma = ma;
	}

	public void setLoai(int loai) {
		Loai = loai;
	}

    

}

package com.dulich.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PlaceForm {
    
    private String ma;
    
    @NotEmpty
    @Size(min=3, max=100)
    private String ten;
    @NotNull
    @Min(value=10000, message="Giá Tiền lớn hơn > 10000")
    private int gia;
    
    @NotEmpty
    @Size(min=3, max=100)
    private String dia_diem;
    
    @NotEmpty
    @Size(min=10, max=1000)
    private String thong_tin;
    
    public PlaceForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    public PlaceForm(String ma, String ten, int gia, String dia_diem, String thong_tin) {
        super();
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
        this.dia_diem = dia_diem;
        this.thong_tin = thong_tin;
    }
    public String getMa() {
        return ma;
    }
    public void setMa(String ma) {
        this.ma = ma;
    }
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }
    public int getGia() {
        return gia;
    }
    public void setGia(int gia) {
        this.gia = gia;
    }
    public String getDia_diem() {
        return dia_diem;
    }
    public void setDia_diem(String dia_diem) {
        this.dia_diem = dia_diem;
    }
    public String getThong_tin() {
        return thong_tin;
    }
    public void setThong_tin(String thong_tin) {
        this.thong_tin = thong_tin;
    }

}

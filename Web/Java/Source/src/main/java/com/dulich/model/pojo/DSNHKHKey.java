package com.dulich.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class DSNHKHKey  implements Serializable{

    @Column(name = "ma_dia_diem")
    private Integer ma_dia_diem;
    
    @Column(name = "ma_khach_san")
    private Integer ma_khach_san;
    
    public DSNHKHKey() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public DSNHKHKey(Integer ma_dia_diem, Integer ma_khach_san,Integer ma_nha_hang) {
        super();
        this.ma_khach_san = ma_khach_san;
        this.ma_dia_diem = ma_dia_diem;
        this.ma_nha_hang = ma_nha_hang;
    }


    @Column(name = "ma_nha_hang")
    private Integer ma_nha_hang;


    public Integer getMa_dia_diem() {
        return ma_dia_diem;
    }

    public void setMa_dia_diem(Integer ma_dia_diem) {
        this.ma_dia_diem = ma_dia_diem;
    }

    public Integer getMa_khach_san() {
        return ma_khach_san;
    }

    public void setMa_khach_san(Integer ma_khach_san) {
        this.ma_khach_san = ma_khach_san;
    }

    public Integer getMa_nha_hang() {
        return ma_nha_hang;
    }

    public void setMa_nha_hang(Integer ma_nha_hang) {
        this.ma_nha_hang = ma_nha_hang;
    }
    
    

}

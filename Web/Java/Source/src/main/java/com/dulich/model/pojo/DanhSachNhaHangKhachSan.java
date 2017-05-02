package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "danhsachnhahangkhachsan")
public class DanhSachNhaHangKhachSan {

    private DSNHKHKey key;
    
    private Integer co;
    
     @Id
     @Column(name = "ma")
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private int ma;

    
    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public DanhSachNhaHangKhachSan() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DanhSachNhaHangKhachSan(DSNHKHKey key, Integer co, int ma) {
        super();
        this.key = key;
        this.co = co;
        this.ma = ma;
    }

    public DSNHKHKey getKey() {
        return key;
    }

    public void setKey(DSNHKHKey key) {
        this.key = key;
    }

    @Column(name = "co")
    public Integer getCo() {
        return co;
    }

    public void setCo(Integer co) {
        this.co = co;
    }
    
    
    

}
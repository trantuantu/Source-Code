package com.dulich.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;

@Embeddable
public class DSDDKey implements Serializable{
	
	@Column(name = "ma_tour", nullable = false)
    private Integer ma_tour;
	
	@Column(name = "ma_dia_diem", nullable = false)
    private Integer ma_dia_diem;

	public DSDDKey() {
		super();
	}

	public DSDDKey(Integer ma_tour, Integer ma_dia_diem) {
		super();
		this.ma_tour = ma_tour;
		this.ma_dia_diem = ma_dia_diem;
	}

	public Integer getMa_tour() {
		return ma_tour;
	}

	public void setMa_tour(Integer ma_tour) {
		this.ma_tour = ma_tour;
	}

	public Integer getMa_dia_diem() {
		return ma_dia_diem;
	}

	public void setMa_dia_diem(Integer ma_dia_diem) {
		this.ma_dia_diem = ma_dia_diem;
	}
	
	
}

package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "danhsachdiadiem")
public class DanhSachDiaDiem {
	
	private DSDDKey key;
	private Integer thu_tu;
	private Integer co;
	
	public DanhSachDiaDiem() {
		super();
	}

	public DanhSachDiaDiem(DSDDKey key, Integer thu_tu, Integer co) {
		super();
		this.key = key;
		this.thu_tu = thu_tu;
		this.co = co;
	}

	@EmbeddedId
	public DSDDKey getKey() {
		return key;
	}

	public void setKey(DSDDKey key) {
		this.key = key;
	}
	
	@Column(name = "thu_tu", nullable = false)
	public Integer getThu_tu() {
		return thu_tu;
	}

	public void setThu_tu(Integer thu_tu) {
		this.thu_tu = thu_tu;
	}
	
	@Column(name = "co", nullable = false)
	public Integer getCo() {
		return co;
	}

	public void setCo(Integer co) {
		this.co = co;
	}
}

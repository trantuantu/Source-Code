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
public class Customer {
	 @Id
	 @Column(name = "ma")
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private int key;
	 
	 @NotNull
	 @Column(name = "ten",  nullable = false)
	 private String name;
	 
	 @Column(name = "dia_chi",  nullable = true)
	 private String address;
	 
	 @NotNull
	 @Column(name = "cmnd",  nullable = true)
	 private String CMND;
	    
	 @Column(name = "sdt",  nullable = false)
	 private String phone;
	 
	 @Column(name = "co",columnDefinition = "TINYINT")
	 @Type(type = "org.hibernate.type.NumericBooleanType")
	 private boolean flag;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCMND() {
		return CMND;
	}

	public void setCMND(String cMND) {
		CMND = cMND;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	 
}

package com.dulich.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "phieudangky", uniqueConstraints = { @UniqueConstraint(columnNames = { "ma" }) })
public class RegistrationForm {
	@Id
	@Column(name = "ma")
	private int key;
	@Column (name = "ma_tour")
	private int key_tour;
	@Column(name = "ma_khach_hang")
	private int key_user;

	@Column(name = "tong_tien")
	private int total_money;
	@Column(name= "tien_coc")
	private int deposit;
	@Column(name= "so_luong_nguoi")
	private int count_people;
	@Column(name = "ghi_chu")
	private String note;
	@Column(name = "trang_thai")
	private int status;
	
	@Column(name = "co")
	private int is_flag;
	
	public int getIs_flag() {
		return is_flag;
	}
	public void setIs_flag(int is_flag) {
		this.is_flag = is_flag;
	}
	@Column(name = "chinh_sua")
	private int is_edit_tour;
	
	public RegistrationForm(){
		
	}
	public int getIs_edit_tour() {
		return is_edit_tour;
	}
	public void setIs_edit_tour(int is_edit_tour) {
		this.is_edit_tour = is_edit_tour;
	}
	public RegistrationForm(int key, int key_tour, int key_user, int key_guide, int total_money,
			int deposit, int count_people, String note, int status,int is_edit_tour) {
		super();
		this.key = key;
		this.key_tour = key_tour;
		this.key_user = key_user;
		
		this.total_money = total_money;
		this.deposit = deposit;
		this.count_people = count_people;
		this.note = note;
		this.status = status;
		this.is_edit_tour = is_edit_tour;
		
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getKey_tour() {
		return key_tour;
	}
	public void setKey_tour(int key_tour) {
		this.key_tour = key_tour;
	}
	public int getKey_user() {
		return key_user;
	}
	public void setKey_user(int key_user) {
		this.key_user = key_user;
	}
	
	public int getTotal_money() {
		return total_money;
	}
	public void setTotal_money(int total_money) {
		this.total_money = total_money;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public int getCount_people() {
		return count_people;
	}
	public void setCount_people(int count_people) {
		this.count_people = count_people;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}

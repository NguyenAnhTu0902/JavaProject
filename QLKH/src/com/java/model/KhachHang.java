package com.java.model;

import java.io.Serializable;

public class KhachHang implements Serializable, Comparable<KhachHang>{
	private String ma;
	private String ten;
	private String phone;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public KhachHang(String ma, String ten, String phone) {
		super();
		this.ma = ma;
		this.ten = ten;
		this.phone = phone;
	}
	public KhachHang() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.ma + "\t"+this.ten+"\t"+this.phone;
	}
	@Override
	public int compareTo(KhachHang o) {
		// TODO Auto-generated method stub
		return this.ten.compareToIgnoreCase(o.ten);
	}
	
}

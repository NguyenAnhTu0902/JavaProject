package com.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class PhongBan implements Serializable{
	private String maPhong;
	private String tenPhong;
	private Vector<NhanVien>nhanViens;
	
	public void themNhanVien(NhanVien nv) {
		this.nhanViens.add(nv);
		nv.setPhong(this);
	}
	
	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public Vector<NhanVien> getNhanViens() {
		return nhanViens;
	}
	public void setNhanViens(Vector<NhanVien> nhanViens) {
		this.nhanViens = nhanViens;
	}
	public PhongBan() {
		super();
		this.nhanViens = new Vector<NhanVien>();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.tenPhong;
	}
	 
}

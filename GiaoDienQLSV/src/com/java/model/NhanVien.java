package com.java.model;

import java.io.Serializable;
import java.sql.Date;

public class NhanVien implements Serializable{
	private String maNhanVien;
	private String tenNhanVien;
	private Date namSinh;
	private Date ngayVaoLamViec;
	public NhanVien(String maNhanVien, String tenNhanVien, Date namSinh, Date ngayVaoLamViec) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.namSinh = namSinh;
		this.ngayVaoLamViec = ngayVaoLamViec;
	}

	public Date getNamSinh() {
		return namSinh;
	}
	public void setNamSinh(Date namSinh) {
		this.namSinh = namSinh;
	}

	private PhongBan phong;
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}
	public Date getNgayVaoLamViec() {
		return ngayVaoLamViec;
	}
	public void setNgayVaoLamViec(Date ngayVaoLamViec) {
		this.ngayVaoLamViec = ngayVaoLamViec;
	}
	public PhongBan getPhong() {
		return phong;
	}
	public void setPhong(PhongBan phong) {
		this.phong = phong;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.tenNhanVien;
	}
	
}

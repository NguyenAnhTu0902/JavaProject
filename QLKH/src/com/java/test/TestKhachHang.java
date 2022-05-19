package com.java.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.java.io.SerializeTextFactory;
import com.java.model.KhachHang;

public class TestKhachHang {
	static ArrayList<KhachHang>dsKH = new ArrayList<KhachHang>();
	public static void menu() {
		System.out.println("**************MENU******************");
		System.out.println("1.Nhập khách hàng");
		System.out.println("2.Xuất khách hàng");
		System.out.println("3.Tìm kiếm khách hàng");
		System.out.println("4.Sắp xếp khách hàng");
		System.out.println("5.Lưu khách hàng");
		System.out.println("6.Đọc khách hàng");
		System.out.println("7.Thống kê theo nhà mạng");
		System.out.println("8.Thoát");
		System.out.println("Lựa chọn của bạn là: ");
		int chon = new Scanner(System.in).nextInt();
		switch(chon){
		case 1: {
			xuLyNhap();
			break;
		}
		case 2:{
			xuLyXuat();
			break;
		}
		case 3: {
			xuLyTim();
			break;
		}
		case 4: {
			xuLyXep();
			break;
		}
		case 5:{
			xuLyLuu();
			break;
		}
		case 6: {
			xuLyDoc();
			break;
		}
		case 7: {
			xuLyThongKe();
			break;
		}
		case 8:{
			xuLyThoat();
			break;
		}
		}
	}
	private static void xuLyThoat() {
		System.out.println("Cảm ơn bạn đã sử dụng dịch vụ :))");
		System.exit(0);
	}
	private static void xuLyThongKe() {
		int n=0;
		String phone ="098";
		for(KhachHang kh : dsKH ) {
			if(kh.getPhone().startsWith(phone)) {
				n++;
			}
			
		}
		System.out.println("Có "+n+" khách hàng Viettel là: ");
		System.out.println("Mã\tTên\t\tPhone");
		for(KhachHang kh : dsKH) {
			if(kh.getPhone().startsWith(phone)) {
				System.out.println(kh);
			}
		}
		
	}
	private static void xuLyDoc() {
		dsKH = SerializeTextFactory.docFile("f:\\danhsachkhachhang.db");
		System.out.println("Đã đọc xong!!!!!!");
	}
	private static void xuLyLuu() {
		boolean kt = SerializeTextFactory.luuFile(dsKH,"f:\\danhsachkhachhang.db");
		if(kt==true) {
			System.out.println("Đã lưu file thành công");
		}else {
			System.out.println("Lưu file thất bại");
		}
	}
	private static void xuLyXep() {
		Collections.sort(dsKH);
		System.out.println("Đã sắp xếp xong");
	}
	private static void xuLyTim() {
		int n= 0;
		System.out.println("Vui lòng nhập số điện thoại cần tìm: ");
		String phone = new Scanner(System.in).nextLine();
		System.out.println("================================");
		System.out.println("Mã\tTên\t\tSĐT");
		for(KhachHang kh : dsKH) {
			if(kh.getPhone().startsWith(phone)) {
				n++;
				System.out.println(kh);
			}
		}
		if(n == 0) {
			System.out.println("Không tìm thấy khách hàng");
		}
	}
	private static void xuLyXuat() {
		System.out.println("=============================");
		System.out.println("Bạn đã nhập danh sách: ");
		System.out.println("Mã\tTên\t\tPhone");
		for(KhachHang kh :dsKH) {
			System.out.println(kh);
		}
		System.out.println("==============================");
	}
	private static void xuLyNhap() {
		KhachHang kh = new KhachHang();
		System.out.println("Xin mời bạn nhập mã: ");
		String ma = new Scanner(System.in).nextLine();
		System.out.println("Xin mời nhập tên: ");
		String ten = new Scanner(System.in).nextLine();
		System.out.println("Xin mời nhập số điện thoại: ");
		String phone = new Scanner(System.in).nextLine();
		kh.setMa(ma);
		kh.setTen(ten);
		kh.setPhone(phone);
		dsKH.add(kh);
	}
	public static void main(String[] args) {
		while(true) {
			menu();
		}
	}
}

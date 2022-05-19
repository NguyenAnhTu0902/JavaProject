package com.java.io;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.java.model.NhanVien;
import com.java.model.PhongBan;

public class QLNV extends JFrame{
	JComboBox<PhongBan>cboPhongBan;
	JList<NhanVien>listNhanVien;
	JTextField txtMa,txtTen,txtNgayVaoLam,txtNamSinh;
	JButton btnLuu,btnXoa,btnThoat;
	
	ArrayList<PhongBan>dsPhongBan;
	ArrayList<NhanVien>dsNhanVien;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
	
	PhongBan pbSelected = null;
	NhanVien nvSelected = null;
	public QLNV(String tieude) {
		super(tieude);
		addControls();
		fakeData();
		addEvents();
	}
	public void addControls() {
		Container con = getContentPane();
		JPanel pnMain = new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		JPanel pnPhongBan = new JPanel();
		pnPhongBan.setLayout(new FlowLayout());
		pnMain.add(pnPhongBan);
		JLabel lblPhongBan = new JLabel("Chọn Phòng Ban:");
		cboPhongBan = new JComboBox<PhongBan>();
		cboPhongBan.setPreferredSize(new Dimension(200, 25));
		pnPhongBan.add(lblPhongBan);
		pnPhongBan.add(cboPhongBan);
		con.add(pnMain);
		
		JPanel pnDanhSachVaChiTiet = new JPanel();
		pnDanhSachVaChiTiet.setLayout(new GridLayout(1,2));
		pnMain.add(pnDanhSachVaChiTiet);
		JPanel pnDanhSach = new JPanel();
		pnDanhSach.setLayout(new BorderLayout());
		
		Border borderDanhSach = BorderFactory.createLineBorder(Color.RED);
		TitledBorder titleBoderDanhsach = new TitledBorder(borderDanhSach, "Danh sách");
		titleBoderDanhsach.setTitleColor(Color.BLACK);
		titleBoderDanhsach.setTitleJustification(titleBoderDanhsach.CENTER);
		pnDanhSach.setBorder(titleBoderDanhsach);
		
		
		listNhanVien = new JList<NhanVien>();
		JScrollPane sc = new JScrollPane(listNhanVien, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnDanhSach.add(sc,BorderLayout.CENTER);
		pnDanhSachVaChiTiet.add(pnDanhSach);
		
		JPanel pnChiTiet = new JPanel();
		pnChiTiet.setLayout(new BoxLayout(pnChiTiet, BoxLayout.Y_AXIS));
		pnDanhSachVaChiTiet.add(pnChiTiet);
		
		Border boderChitiet = BorderFactory.createLineBorder(Color.RED);
		TitledBorder titleBorderChitiet = new TitledBorder(boderChitiet, "Chi tiết");
		titleBorderChitiet.setTitleJustification(titleBorderChitiet.CENTER);
		pnChiTiet.setBorder(titleBorderChitiet);
		
		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout());
		JLabel lblMa = new JLabel("Mã: ");
		txtMa = new JTextField(15);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnChiTiet.add(pnMa);
		
		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout());
		JLabel lblTen = new JLabel("Tên: ");
		txtTen = new JTextField(15);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnChiTiet.add(pnTen);
		
		JPanel pnNgayVao = new JPanel();
		pnNgayVao.setLayout(new FlowLayout());
		JLabel lblNgayVao = new JLabel("Ngày Vào: ");
		txtNgayVaoLam = new JTextField(15);
		pnNgayVao.add(lblNgayVao);
		pnNgayVao.add(txtNgayVaoLam);
		pnChiTiet.add(pnNgayVao);
		
		JPanel pnNamSinh = new JPanel();
		pnNamSinh.setLayout(new FlowLayout());
		JLabel lblNamSinh = new JLabel("Năm Sinh: ");
		txtNamSinh = new JTextField(15);
		pnNamSinh.add(lblNamSinh);
		pnNamSinh.add(txtNamSinh);
		pnChiTiet.add(pnNamSinh);
		
		JPanel pnButton = new JPanel();
		Border borderButton = BorderFactory.createLineBorder(Color.RED);
		TitledBorder titleBorderButton = new TitledBorder(borderButton, "Chọn chức năng");
		titleBorderButton.setTitleJustification(titleBorderButton.CENTER);
		pnButton.setBorder(titleBorderButton);
		
		pnButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnLuu = new JButton("Lưu");
		btnXoa = new JButton("Xóa");
		btnThoat = new JButton("Thoát");
		pnButton.add(btnLuu);
		pnButton.add(btnXoa);
		pnButton.add(btnThoat);
		pnMain.add(pnButton);
		
		lblMa.setPreferredSize(lblNamSinh.getPreferredSize());
		lblTen.setPreferredSize(lblNamSinh.getPreferredSize());
		lblNgayVao.setPreferredSize(lblNamSinh.getPreferredSize());
		
	}
	
	public void fakeData() {
		dsPhongBan = new ArrayList<PhongBan>();
		
		PhongBan phongMaketting = new PhongBan();
		phongMaketting.setMaPhong("Phòng 1");
		phongMaketting.setTenPhong("Phòng Maketting");
		
		PhongBan phongKeToan = new PhongBan();
		phongKeToan.setMaPhong("Phòng 2");
		phongKeToan.setTenPhong("Phòng Kế Toán");
		
		PhongBan phongKinhDoanh = new PhongBan();
		phongKinhDoanh.setMaPhong("Phòng 3");
		phongKinhDoanh.setTenPhong("Phòng Kinh Doanh");
		
		PhongBan phongTuyenDung = new PhongBan();
		phongTuyenDung.setMaPhong("Phòng 4");
		phongTuyenDung.setTenPhong("Phòng Tuyển Dụng");
		
		phongKeToan.themNhanVien(new NhanVien("NV1","Nguyễn Anh Tú",new Date(2001-1900,4,12), new Date(2016-1900,1,1)));
		phongKeToan.themNhanVien(new NhanVien("NV2","Nguyễn Bá Hồng Sơn",new Date(2001-1900, 7, 1), new Date(2016-1900, 12, 4)));
		phongKeToan.themNhanVien(new NhanVien("NV3","Phạm Thị Thu Trang",new Date(2001-1900,7,4), new Date(2016-1900, 2, 7)));
		
		
		dsPhongBan.add(phongTuyenDung);
		dsPhongBan.add(phongKeToan);
		dsPhongBan.add(phongMaketting);
		dsPhongBan.add(phongKinhDoanh);
		
		for(PhongBan pb : dsPhongBan) {
			cboPhongBan.addItem(pb);
	}
	}
	public void addEvents() {
		cboPhongBan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			if(cboPhongBan.getSelectedIndex() == -1) return;
			
			pbSelected = (PhongBan) cboPhongBan.getSelectedItem();
			listNhanVien.setListData(pbSelected.getNhanViens());
			
			}
		});
		listNhanVien.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listNhanVien.getSelectedIndex() == -1)return;
				
				nvSelected = listNhanVien.getSelectedValue();
				txtMa.setText(nvSelected.getMaNhanVien());
				txtTen.setText(nvSelected.getTenNhanVien());
				txtNgayVaoLam.setText(sdf.format(nvSelected.getNgayVaoLamViec()));
				txtNamSinh.setText(sdf.format(nvSelected.getNamSinh()));
				
			}
		});
		btnLuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xuLyLuu();
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xuLyXoa();
			}
		});
		btnThoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	}
	protected void xuLyXoa() {
		if(nvSelected != null) {
			pbSelected.getNhanViens().remove(nvSelected);
			nvSelected=null;
			listNhanVien.setListData(pbSelected.getNhanViens());
		}
	}
	protected void xuLyLuu() {

	}
	public void showWindow() {
		this.setSize(550, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}

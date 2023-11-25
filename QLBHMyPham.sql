CREATE DATABASE QLBHMyPham;
GO

USE QLBHMyPham;
GO

-- Bảng Danh mục sản phẩm
CREATE TABLE DanhMucSanPham (
    MaDM NVARCHAR(30) PRIMARY KEY,
    TenDM NVARCHAR(255) NOT NULL
);
GO

-- Bảng Sản phẩm
CREATE TABLE SanPham (
    MaSP NVARCHAR(30) PRIMARY KEY,
    MaDM NVARCHAR(30),
    TenSP NVARCHAR(255) NOT NULL,
	ThuongHieu NVARCHAR(255) NOT NULL,
    DonGia FLOAT NOT NULL,
    MoTa NVARCHAR(255) NULL,
    HinhSP NVARCHAR(30) NOT NULL,
    SLTonKho INT NOT NULL,
	TrangThai NVARCHAR(15) NOT NULL,
    FOREIGN KEY (MaDM) REFERENCES DanhMucSanPham(MaDM)
);
GO

-- Bảng Nhân viên
CREATE TABLE NhanVien (
    MaNV NVARCHAR(30) PRIMARY KEY,
    TenNV NVARCHAR(255) NOT NULL,
    Email NVARCHAR(255) NOT NULL,
    SDT NVARCHAR(11) NOT NULL,
    NgaySinh DATE NOT NULL,
    ChucVu NVARCHAR(30) NOT NULL,
    TrangThai BIT NOT NULL,
    TenTK NVARCHAR(30) NOT NULL,
    MatKhau NVARCHAR(30) NOT NULL
);
GO

-- Bảng Khuyến mãi
CREATE TABLE KhuyenMai (
    MaKM NVARCHAR(30) PRIMARY KEY,
    TenKM NVARCHAR(255) NOT NULL,
    MoTa NVARCHAR(255) NULL,
    NgayBD DATE NOT NULL,
    NgayKT DATE NOT NULL,
    PhanTramGG FLOAT NOT NULL
);
GO

-- Bảng Đơn hàng
CREATE TABLE HoaDon (
    MaHD NVARCHAR(30) primary key,
    MaNV NVARCHAR(30) NOT NULL,
	MaKM NVARCHAR(30) NULL,
    NgayLap DATE NOT NULL,
	TongSoTien FLOAT NOT NULL,
    TrangThai NVARCHAR(30) NOT NULL,
	PhuongThucTT NVARCHAR(30) NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (MaKM) REFERENCES KhuyenMai(MaKM)
);
GO

-- Bảng Chi tiết đơn hàng
CREATE TABLE ChiTietHoaDon (
    MaCTHD NVARCHAR(30) PRIMARY KEY,
    MaHD NVARCHAR(30),
    MaSP NVARCHAR(30),
    SoLuong INT NOT NULL,
    DonGia FLOAT NOT NULL,
    FOREIGN KEY (MaHD) REFERENCES HOADON(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);
GO

-- Bảng Nhập hàng
CREATE TABLE NhapHang (
    MaNH NVARCHAR(30) PRIMARY KEY,
	MaNV NVARCHAR(30),
    NhaCC NVARCHAR(255) NOT NULL,
    NgayNhap DATE NOT NULL,
    TongTien FLOAT NOT NULL,
);
GO

-- Bảng Chi tiết phiếu nhập hàng
CREATE TABLE ChiTietPhieuNhapHang (
    MaCTNH NVARCHAR(30) PRIMARY KEY,
    MaNH NVARCHAR(30),
    MaSP NVARCHAR(30),
    SoLuong INT NOT NULL,
    DonGia FLOAT NOT NULL,
    FOREIGN KEY (MaNH) REFERENCES NhapHang(MaNH),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- Bảng Phiếu trả hàng
CREATE TABLE PhieuTraHang (
    MaTH NVARCHAR(30) PRIMARY KEY,
    MaNV NVARCHAR(30) ,
    NgayTra DATE NOT NULL,
    Lydo NVARCHAR(255) NOT NULL,
	FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

-- Bảng Phiếu chi tiet trả hàng
CREATE TABLE ChiTietPhieuTraHang (
    MaCTTH NVARCHAR(30) PRIMARY KEY,
    MaTH NVARCHAR(30),
	MaSP NVARCHAR(30),
	SoLuong int NOT NULL,
	DonGia Float NOT NULL,
    FOREIGN KEY (MaTH) REFERENCES PhieuTraHang(MaTH)
);
GO

CREATE OR ALTER TRIGGER CapNhatTonKhoPhieuNhap
ON ChiTietPhieuNhapHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật số lượng tồn kho cho từng chi tiết hóa đơn trong phiếu nhập hàng
    UPDATE SanPham
    SET SLTonKho = SLTonKho + i.SoLuong
    FROM SanPham
    JOIN inserted i ON SanPham.MaSP = i.MaSP;
END;
GO

CREATE OR ALTER TRIGGER CapNhatTonKhoHoaDon
ON ChiTietHoaDon
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật số lượng tồn kho cho từng chi tiết hóa đơn mới
    UPDATE SanPham
    SET SLTonKho = SLTonKho - i.SoLuong
    FROM SanPham
    JOIN inserted i ON SanPham.MaSP = i.MaSP;
END;
GO

CREATE OR ALTER TRIGGER UpdateTongSoTien
ON ChiTietHoaDon
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật TongSoTien trong bảng HoaDon dựa trên ChiTietHoaDon
    UPDATE HoaDon
    SET TongSoTien = (
        SELECT SUM(DonGia * SoLuong)
        FROM ChiTietHoaDon
        WHERE MaHD = HoaDon.MaHD
    )
    FROM HoaDon
    INNER JOIN inserted ON HoaDon.MaHD = inserted.MaHD;

    -- Cập nhật giảm giá nếu có
    UPDATE HoaDon
    SET TongSoTien = 
        CASE 
            WHEN KhuyenMai.MaKM IS NOT NULL THEN TongSoTien - (TongSoTien * PhanTramGG)
            ELSE TongSoTien
        END
    FROM HoaDon
    INNER JOIN inserted ON HoaDon.MaHD = inserted.MaHD
    LEFT JOIN KhuyenMai ON HoaDon.MaKM = KhuyenMai.MaKM;

END;
GO

CREATE OR ALTER TRIGGER UpdateTongTienNhapHang
ON ChiTietPhieuNhapHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật tổng tiền cho tất cả các phiếu nhập hàng mới được chèn vào
    UPDATE NhapHang
    SET TongTien = (
        SELECT SUM(DonGia * SoLuong)
        FROM ChiTietPhieuNhapHang
        WHERE MaNH = NhapHang.MaNH
    )
    FROM NhapHang
    INNER JOIN inserted ON NhapHang.MaNH = inserted.MaNH;
END;


-- Bảng Danh mục sản phẩm
INSERT INTO DanhMucSanPham (MaDM, TenDM) VALUES
	('DM001', N'Chăm sóc da mặt'),
	('DM002', N'Trang điểm'),
	('DM003', N'Chăm sóc tóc'),
	('DM004', N'Nước hoa'),
	('DM005', N'Dụng cụ trang điểm');

-- Bảng Sản phẩm
INSERT INTO SanPham (MaSP, MaDM, TenSP, ThuongHieu, DonGia, MoTa, HinhSP, SLTonKho, TrangThai) VALUES
	('SP001', 'DM001', N'Kem dưỡng ẩm', N'Nivea', 15000, N'Dưỡng ẩm cho da khô', 'kem_duong_am.jpg', 100, N'Còn hàng'),
	('SP002', 'DM002', N'Son môi', N'Maybelline', 200000, N'Màu hồng cam đậm', 'son_moi.jpg', 50, N'Còn hàng'),
	('SP003', 'DM003', N'Dầu gội', N'Pantene', 50000, N'Dành cho tóc khô và hư tổn', 'dau_goi.jpg', 120, N'Còn hàng'),
	('SP004', 'DM004', N'Nước hoa nam', N'Dior', 120000, N'Hương thơm nam tính', 'nuoc_hoa_nam.jpg', 30, N'Còn hàng'),
	('SP005', 'DM005', N'Bộ cọ trang điểm', N'Real Techniques', 180000, N'Bộ 10 cọ chất lượng cao', 'bo_co_trang_diem.jpg', 80, N'Còn hàng'),
	('SP006', 'DM001', N'Sữa rửa mặt', N'Cetaphil', 35000, N'Dành cho da nhạy cảm', 'sua_rua_mat.jpg', 60, N'Còn hàng'),
	('SP007', 'DM002', N'Phấn má hồng', N'L''Oreal', 25000, N'Màu hồng phấn nhẹ nhàng', 'phan_ma_hong.jpg', 40, N'Còn hàng'),
	('SP008', 'DM003', N'Dầu xả', N'Herbal Essences', 55000, N'Dưỡng ẩm và làm mềm tóc', 'dau_xa.jpg', 90, N'Còn hàng'),
	('SP009', 'DM004', N'Nước hoa nữ', N'Chanel', 180000, N'Hương thơm ngọt ngào', 'nuoc_hoa_nu.jpg', 25, N'Còn hàng'),
	('SP010', 'DM005', N'Bút kẻ mắt', N'Maybelline', 120000, N'Đen sâu, không lem', 'but_ke_mat.jpg', 60, N'Còn hàng'),
	('SP011', 'DM001', N'Mặt nạ dưỡng da', N'The Face Shop', 75000, N'Dưỡng ẩm và làm trắng da', 'mat_na.jpg', 75, N'Còn hàng'),
	('SP012', 'DM002', N'Son nước', N'Revlon', 90000, N'Màu đỏ thắm tươi tắn', 'son_nuoc.jpg', 45, N'Còn hàng'),
	('SP013', 'DM003', N'Kem duỗi tóc', N'TRESemmé', 65000, N'Giữ tóc mềm mại và suôn mượt', 'kem_duoi_toc.jpg', 70, N'Còn hàng'),
	('SP014', 'DM004', N'Deodorant nữ', N'Dove', 30000, N'Kiểm soát mùi và giữ khô thoáng', 'deodorant_nu.jpg', 55, N'Còn hàng'),
	('SP015', 'DM005', N'Bàn chải đánh răng', N'Oral-B', 5000, N'Bàn chải chăm sóc răng toàn diện', 'ban_chai_danh_rang.jpg', 100, N'Còn hàng');

-- Bảng nhân viên
INSERT INTO NhanVien (MaNV, TenNV, Email, SDT, NgaySinh, ChucVu, TrangThai, TenTK, MatKhau)
VALUES 
    ('NV001', N'Nguyễn Thị Hương', 'nguyenthihuong@gmail.com', '0123456789', '1990-01-01', N'Nhân viên', 1, 'huongnt', '123'),
    ('NV002', N'Trần Văn Nam', 'tranvannam@gmail.com', '0123456789', '1992-05-15', N'Nhân viên', 1, 'namtv', '123'),
    ('NV003', N'Phạm Minh Tuấn', 'phamminhtuan@gmail.com', '0123456789', '1988-09-30', N'Nhân viên', 1, 'tuanpm', '123'),
    ('NV004', N'Lê Thị Ngọc', 'lengoc@gmail.com', '0123456789', '1995-12-20', N'Nhân viên', 1, 'ngoclt', '123'),
    ('NV005', N'Hồ Anh Dũng', 'hoanhdung@gmail.com', '0123456789', '1993-03-10', N'Quản lý', 1, 'dungha', '123');

-- Bảng khuyến mãi
INSERT INTO KhuyenMai (MaKM, TenKM, MoTa, NgayBD, NgayKT, PhanTramGG)
VALUES 
    ('KM001', N'Ưu đãi mùa hè', N'Giảm giá cho các sản phẩm mùa hè', '2023-06-01', '2023-08-31', 0.1),
    ('KM002', N'Tặng quà cho đơn hàng lớn', N'Tặng quà hấp dẫn cho đơn hàng từ 1.000.000 VND trở lên', '2023-09-01', '2023-12-31', 0.05),
    ('KM003', N'Khuyến mãi đặc biệt', N'Giảm giá và tặng quà cho những sản phẩm đặc biệt', '2023-11-01', '2023-11-30', 0.15);

-- Thêm vào bảng HoaDon
INSERT INTO HoaDon (MaHD, MaNV, MaKM, NgayLap, TongSoTien, TrangThai, PhuongThucTT)
VALUES 
    ('HD001', 'NV001', 'KM001', GETDATE(),0,N'Đã thanh toán', N'Tiền mặt'),
    ('HD002', 'NV002', 'KM002', GETDATE(),0,N'Đã thanh toán', N'Momo'),
    ('HD003', 'NV002', NULL, GETDATE(),0,N'Đã thanh toán', N'Thẻ ngân hàng');

-- Thêm vào bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (MaCTHD, MaHD, MaSP, SoLuong, DonGia)
VALUES 
	('CTHD001', 'HD001', 'SP001', 4, 15000),
    ('CTHD002', 'HD001', 'SP002', 1, 200000),
    ('CTHD003', 'HD001', 'SP003', 3, 50000),
    ('CTHD004', 'HD002', 'SP004', 1, 120000),
    ('CTHD005', 'HD002', 'SP001', 2, 15000),
    ('CTHD006', 'HD002', 'SP002', 1, 200000),
    ('CTHD007', 'HD003', 'SP003', 2, 50000),
    ('CTHD008', 'HD003', 'SP004', 5, 120000),
    ('CTHD009', 'HD003', 'SP001', 3, 15000);

-- Bảng Nhập hàng
INSERT INTO NhapHang (MaNH, MaNV, NhaCC, NgayNhap, TongTien)
VALUES
    ('NH001', 'NV001', N'Nhà cung cấp X', GETDATE(), 0),
    ('NH002', 'NV002', N'Nhà cung cấp Y', GETDATE(), 0),
    ('NH003', 'NV003', N'Nhà cung cấp Z', GETDATE(), 0);

-- Bảng Chi tiết phiếu nhập hàng
INSERT INTO ChiTietPhieuNhapHang (MaCTNH, MaNH, MaSP, SoLuong, DonGia)
VALUES
    ('CTNH001', 'NH001', 'SP001', 50, 15000),
    ('CTNH002', 'NH002', 'SP002', 30, 200000),
    ('CTNH003', 'NH003', 'SP003', 20, 50000);

-- Bảng Phiếu trả hàng
INSERT INTO PhieuTraHang (MaTH, MaNV, NgayTra, Lydo)
VALUES
    ('TH001', 'NV001', '2023-07-05', 'Sản phẩm lỗi'),
    ('TH002', 'NV002', '2023-09-01', 'Khách hàng đổi sản phẩm'),
    ('TH003', 'NV003', '2023-11-20', 'Không hài lòng với chất lượng');

-- Bảng Chi tiết phiếu trả hàng
INSERT INTO ChiTietPhieuTraHang (MaCTTH, MaTH, MaSP, SoLuong, DonGia)
VALUES
    ('CTTH001', 'TH001', 'SP001', 5, 150000),
    ('CTTH002', 'TH001', 'SP002', 2, 220000),
    ('CTTH003', 'TH002', 'SP003', 1, 480000);
	select * from SanPham
-- Bảng Nhập hàng
INSERT INTO NhapHang (MaNH, MaNV, NhaCC, NgayNhap, TongTien)
VALUES
    ('NH001', 'NV001', 'Nhà cung cấp X', '2023-06-15', 3000000),
    ('NH002', 'NV002', 'Nhà cung cấp Y', '2023-08-20', 5000000),
    ('NH003', 'NV003', 'Nhà cung cấp Z', '2023-11-12', 8000000);

-- Bảng Chi tiết phiếu nhập hàng
INSERT INTO ChiTietPhieuNhapHang (MaCTNH, MaNH, MaSP, SoLuong, DonGia)
VALUES
    ('CTNH001', 'NH001', 'SP001', 50, 120000),
    ('CTNH002', 'NH002', 'SP002', 30, 180000),
    ('CTNH003', 'NH003', 'SP003', 20, 400000);

-- Bảng Phiếu trả hàng
INSERT INTO PhieuTraHang (MaTH, MaNV, NgayTra, Lydo)
VALUES
    ('TH001', 'NV001', '2023-07-05', 'Sản phẩm lỗi'),
    ('TH002', 'NV002', '2023-09-01', 'Khách hàng đổi sản phẩm'),
    ('TH003', 'NV003', '2023-11-20', 'Không hài lòng với chất lượng');

-- Bảng Chi tiết phiếu trả hàng
INSERT INTO ChiTietPhieuTraHang (MaCTTH, MaTH, MaSP, SoLuong, DonGia)
VALUES
    ('CTTH001', 'TH001', 'SP001', 5, 150000),
    ('CTTH002', 'TH001', 'SP002', 2, 220000),
    ('CTTH003', 'TH002', 'SP003', 1, 480000);

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
	TrangThai BIT NOT NULL,
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
    ChucVu BIT NOT NULL,
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
	MaHD NVARCHAR(30),
    NgayTra DATE NOT NULL,
    Lydo NVARCHAR(255) NOT NULL,
	FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (MAHD) REFERENCES HoaDon(MaHD)
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
    ('SP001', 'DM001', N'Kem dưỡng ẩm', N'Nivea', 15000, N'Dưỡng ẩm cho da khô', 'kem_duong_am.jpg', 100, 0),
    ('SP002', 'DM002', N'Son môi', N'Maybelline', 200000, N'Màu hồng cam đậm', 'son_moi.jpg', 50, 0),
    ('SP003', 'DM003', N'Dầu gội', N'Pantene', 50000, N'Dành cho tóc khô và hư tổn', 'dau_goi.jpg', 120, 0),
    ('SP004', 'DM004', N'Nước hoa nam', N'Dior', 120000, N'Hương thơm nam tính', 'nuoc_hoa_nam.jpg', 30, 0),
    ('SP005', 'DM005', N'Bộ cọ trang điểm', N'Real Techniques', 180000, N'Bộ 10 cọ chất lượng cao', 'bo_co_trang_diem.jpg', 80, 0),
    ('SP006', 'DM001', N'Sữa rửa mặt', N'Cetaphil', 35000, N'Dành cho da nhạy cảm', 'sua_rua_mat.jpg', 60, 0),
    ('SP007', 'DM002', N'Phấn má hồng', N'L''Oreal', 25000, N'Màu hồng phấn nhẹ nhàng', 'phan_ma_hong.jpg', 40, 0),
    ('SP008', 'DM003', N'Dầu xả', N'Herbal Essences', 55000, N'Dưỡng ẩm và làm mềm tóc', 'dau_xa.jpg', 90, 0),
    ('SP009', 'DM004', N'Nước hoa nữ', N'Chanel', 180000, N'Hương thơm ngọt ngào', 'nuoc_hoa_nu.jpg', 25, 0),
    ('SP010', 'DM005', N'Bút kẻ mắt', N'Maybelline', 120000, N'Đen sâu, không lem', 'but_ke_mat.jpg', 60, 0),
    ('SP011', 'DM001', N'Mặt nạ dưỡng da', N'The Face Shop', 75000, N'Dưỡng ẩm và làm trắng da', 'mat_na.jpg', 75, 0),
    ('SP012', 'DM002', N'Son nước', N'Revlon', 90000, N'Màu đỏ thắm tươi tắn', 'son_nuoc.jpg', 45, 0),
    ('SP013', 'DM003', N'Kem duỗi tóc', N'TRESemmé', 65000, N'Giữ tóc mềm mại và suôn mượt', 'kem_duoi_toc.jpg', 70, 0),
    ('SP014', 'DM004', N'Deodorant nữ', N'Dove', 30000, N'Kiểm soát mùi và giữ khô thoáng', 'deodorant_nu.jpg', 55, 0),
    ('SP015', 'DM005', N'Bàn chải đánh răng', N'Oral-B', 5000, N'Bàn chải chăm sóc răng toàn diện', 'ban_chai_danh_rang.jpg', 100, 0);

-- Bảng nhân viên
INSERT INTO NhanVien (MaNV, TenNV, Email, SDT, NgaySinh, ChucVu, TrangThai, TenTK, MatKhau)
VALUES 
    ('NV001', N'Nguyễn Thị Hương', 'nguyenthihuong@gmail.com', '0123456789', '1990-01-01', 0, 1, 'huongnt', '123'),
    ('NV002', N'Trần Văn Nam', 'tranvannam@gmail.com', '0123456789', '1992-05-15', 0, 1, 'namtv', '123'),
    ('NV003', N'Phạm Minh Tuấn', 'phamminhtuan@gmail.com', '0123456789', '1988-09-30', 0, 1, 'tuanpm', '123'),
    ('NV004', N'Lê Thị Ngọc', 'lengoc@gmail.com', '0123456789', '1995-12-20', 0, 1, 'ngoclt', '123'),
    ('NV005', N'Hồ Anh Dũng', 'hoanhdung@gmail.com', '0123456789', '1993-03-10', 1, 1, 'dungha', '123');

-- Bảng khuyến mãi
INSERT INTO KhuyenMai (MaKM, TenKM, MoTa, NgayBD, NgayKT, PhanTramGG)
VALUES 
    ('KM001', N'Ưu đãi mùa hè', N'Giảm giá cho các sản phẩm mùa hè', '2023-06-01', '2023-08-31', 0.1),
    ('KM002', N'Tặng quà cho đơn hàng lớn', N'Tặng quà hấp dẫn cho đơn hàng từ 1.000.000 VND trở lên', '2023-09-01', '2023-12-31', 0.05),
    ('KM003', N'Khuyến mãi đặc biệt', N'Giảm giá và tặng quà cho những sản phẩm đặc biệt', '2023-11-01', '2023-11-30', 0.15);

-- Thêm vào bảng HoaDon
INSERT INTO HoaDon (MaHD, MaNV, MaKM, NgayLap, TongSoTien, TrangThai, PhuongThucTT)
VALUES 
    ('HD001', 'NV001', 'KM001', GETDATE(),0,N'Đã thanh toán', N'Tiền mặt');

-- Thêm vào bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (MaCTHD, MaHD, MaSP, SoLuong, DonGia)
VALUES 
    ('CTHD001', 'HD001', 'SP001', 4, 15000),
    ('CTHD002', 'HD001', 'SP004', 1, 120000);

-- Bảng Nhập hàng
INSERT INTO NhapHang (MaNH, MaNV, NhaCC, NgayNhap, TongTien)
VALUES
    ('NH001', 'NV001', N'Nhà cung cấp X', GETDATE(), 0);

-- Bảng Chi tiết phiếu nhập hàng
INSERT INTO ChiTietPhieuNhapHang (MaCTNH, MaNH, MaSP, SoLuong, DonGia)
VALUES
    ('CTNH001', 'NH001', 'SP001', 50, 15000),
    ('CTNH002', 'NH001', 'SP002', 30, 200000),
    ('CTNH003', 'NH001', 'SP003', 20, 50000);

-- Bảng Phiếu trả hàng
INSERT INTO PhieuTraHang (MaTH, MaNV, MaHD, NgayTra, Lydo)
VALUES
    ('TH001', 'NV001','HD001', '2023-07-05', 'Sản phẩm lỗi');

CREATE OR ALTER PROCEDURE ThongKe
    @Lich NVARCHAR(10)
AS
BEGIN
	DECLARE @NgayThangNam NVARCHAR(10) ;
	DECLARE @ThangNam NVARCHAR(7) ;
	DECLARE @Nam NVARCHAR(4) ;
		if LEN(@Lich)=7
			begin
				set @ThangNam = @Lich
				-- Thống kê theo Tháng Năm
SELECT
    NhanVien.MaNV as MaNV,
    COUNT(HoaDon.MaHD) AS SoLuongHoaDon,
    SUM(HoaDon.TongSoTien) AS TongDoanhThu
FROM
    NhanVien
INNER JOIN
    HoaDon ON NhanVien.MaNV = HoaDon.MaNV
INNER JOIN
    ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
WHERE
    FORMAT(HoaDon.NgayLap, 'yyyy/MM') = @ThangNam
GROUP BY
    NhanVien.MaNV
			end

		else if LEN(@Lich)=4
			begin	
				set @Nam = @Lich
				-- Thống kê theo Năm
SELECT
    NhanVien.MaNV as MaNV,
    COUNT(HoaDon.MaHD) AS SoLuongHoaDon,
    SUM(HoaDon.TongSoTien) AS TongDoanhThu
FROM
    NhanVien
INNER JOIN
    HoaDon ON NhanVien.MaNV = HoaDon.MaNV
INNER JOIN
    ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
WHERE
    FORMAT(HoaDon.NgayLap, 'yyyy') = @Nam
GROUP BY
    NhanVien.MaNV;
			end
		else if LEN(@Lich) = 10
			begin
				set @NgayThangNam = @Lich
				-- Thống kê theo Ngày Tháng Năm
SELECT
    NhanVien.MaNV as MaNV,
    COUNT(HoaDon.MaHD) AS SoLuongHoaDon,
    SUM(HoaDon.TongSoTien) AS TongDoanhThu
FROM
    NhanVien
INNER JOIN
    HoaDon ON NhanVien.MaNV = HoaDon.MaNV
INNER JOIN
    ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
WHERE
    CONVERT(DATE, HoaDon.NgayLap, 111) = CONVERT(DATE, @NgayThangNam, 111)
GROUP BY
    NhanVien.MaNV
			end
			end

CREATE OR ALTER PROCEDURE ThongKeSP
    @Lich NVARCHAR(10)
AS
BEGIN
    DECLARE @NgayThangNam NVARCHAR(10);
    DECLARE @ThangNam NVARCHAR(7);
    DECLARE @Nam NVARCHAR(4);

    IF LEN(@Lich) = 7
    BEGIN
        SET @ThangNam = @Lich;
    END
    ELSE IF LEN(@Lich) = 4
    BEGIN
        SET @Nam = @Lich;
    END
    ELSE IF LEN(@Lich) = 10
    BEGIN
        SET @NgayThangNam = @Lich;
    END

    -- Thống kê theo Tháng Năm
    IF @ThangNam IS NOT NULL
    BEGIN
        SELECT MaSP, COUNT(SoLuong) AS TongSL
        FROM HoaDon
            INNER JOIN ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
        WHERE FORMAT(NgayLap, 'yyyy/MM') = @ThangNam
        GROUP BY MaSP;
    END
    -- Thống kê theo Năm
    ELSE IF @Nam IS NOT NULL
    BEGIN
        SELECT MaSP, COUNT(SoLuong) AS TongSL
        FROM HoaDon
            INNER JOIN ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
        WHERE FORMAT(NgayLap, 'yyyy') = @Nam
        GROUP BY MaSP;
    END
    -- Thống kê theo Ngày Tháng Năm
    ELSE IF @NgayThangNam IS NOT NULL
    BEGIN
        SELECT MaSP, COUNT(SoLuong) AS TongSL
        FROM HoaDon
            INNER JOIN ChiTietHoaDon ON HoaDon.MaHD = ChiTietHoaDon.MaHD
        WHERE FORMAT(NgayLap, 'yyyy/MM/dd') = @NgayThangNam
        GROUP BY MaSP;
    END
END
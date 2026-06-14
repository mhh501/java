-- ============================================
-- 网上衣橱 (Wardrobe) 数据库建表脚本
-- MySQL 8.0+
-- ============================================

CREATE DATABASE IF NOT EXISTS wardrobe DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wardrobe;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（MD5加密）',
    phone VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    address VARCHAR(200) DEFAULT '' COMMENT '收货地址',
    role VARCHAR(10) NOT NULL DEFAULT 'user' COMMENT '角色：user普通用户 / admin管理员',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE clothes (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '服装ID',
    name VARCHAR(100) NOT NULL COMMENT '服装名称',
    style VARCHAR(50) NOT NULL COMMENT '风格（如：休闲、商务、运动、复古）',
    category VARCHAR(50) NOT NULL COMMENT '类别（如：上衣、裤子、裙装、外套）',
    size VARCHAR(100) NOT NULL COMMENT '可选尺码（逗号分隔，如：S,M,L,XL）',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    image VARCHAR(200) DEFAULT '' COMMENT '图片文件名',
    description TEXT COMMENT '服装描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服装表';

CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '购物车项ID',
    user_id INT NOT NULL COMMENT '用户ID',
    clothes_id INT NOT NULL COMMENT '服装ID',
    quantity INT NOT NULL DEFAULT 1 COMMENT '数量',
    size VARCHAR(10) NOT NULL COMMENT '选择尺码',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (clothes_id) REFERENCES clothes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    user_id INT NOT NULL COMMENT '用户ID',
    order_no VARCHAR(30) NOT NULL UNIQUE COMMENT '订单编号',
    total_price DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    status VARCHAR(20) NOT NULL DEFAULT 'unpaid' COMMENT '订单状态：unpaid未支付 / unshipped未发货 / shipped已发货 / received已收货',
    pay_time TIMESTAMP NULL COMMENT '支付时间',
    ship_time TIMESTAMP NULL COMMENT '发货时间',
    receive_time TIMESTAMP NULL COMMENT '收货时间',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '明细ID',
    order_id INT NOT NULL COMMENT '订单ID',
    clothes_id INT NOT NULL COMMENT '服装ID',
    clothes_name VARCHAR(100) NOT NULL COMMENT '服装名称',
    price DECIMAL(10,2) NOT NULL COMMENT '单价',
    quantity INT NOT NULL COMMENT '数量',
    size VARCHAR(10) NOT NULL COMMENT '尺码',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 管理员账号：admin / admin123（MD5加密）
INSERT INTO users (username, password, phone, address, role) VALUES
('admin', '0192023a7bbd73250516f069df18b500', '13800000000', '系统管理', 'admin');

-- 示例服装数据
INSERT INTO clothes (name, style, category, size, price, stock, description) VALUES
('经典白衬衫', '商务', '上衣', 'S,M,L,XL', 299.00, 100, '纯棉面料，修身版型，商务休闲两相宜'),
('水洗牛仔裤', '休闲', '裤子', 'S,M,L,XL,XXL', 399.00, 80, '经典直筒版型，舒适水洗面料'),
('百褶连衣裙', '甜美', '裙装', 'S,M,L', 599.00, 50, '轻盈雪纺面料，浪漫百褶设计'),
('连帽卫衣', '运动', '上衣', 'M,L,XL', 259.00, 120, '纯棉加绒面料，宽松舒适版型'),
('羊绒大衣', '商务', '外套', 'S,M,L,XL', 1299.00, 30, '精选羊绒面料，经典翻领设计');

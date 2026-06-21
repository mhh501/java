CREATE DATABASE IF NOT EXISTS wdb_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE wdb_db;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS users;

CREATE TABLE users(id INT PRIMARY KEY AUTO_INCREMENT,username VARCHAR(50) NOT NULL UNIQUE,password VARCHAR(100) NOT NULL,phone VARCHAR(20) NOT NULL UNIQUE,address VARCHAR(200) DEFAULT '',role VARCHAR(10) NOT NULL DEFAULT 'user',create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE clothes(id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(100) NOT NULL,style VARCHAR(50) NOT NULL,category VARCHAR(50) NOT NULL,size VARCHAR(100) NOT NULL,price DECIMAL(10,2) NOT NULL,stock INT NOT NULL DEFAULT 0,image_url VARCHAR(200) DEFAULT '',description TEXT,create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cart(id INT PRIMARY KEY AUTO_INCREMENT,user_id INT NOT NULL,clothes_id INT NOT NULL,quantity INT NOT NULL DEFAULT 1,price DECIMAL(10,2) NOT NULL DEFAULT 0,size VARCHAR(10) NOT NULL,FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,FOREIGN KEY(clothes_id) REFERENCES clothes(id) ON DELETE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders(id INT PRIMARY KEY AUTO_INCREMENT,user_id INT NOT NULL,order_no VARCHAR(30) NOT NULL UNIQUE,total_price DECIMAL(10,2) NOT NULL,status VARCHAR(20) NOT NULL DEFAULT 'unpaid',pay_time TIMESTAMP NULL,ship_time TIMESTAMP NULL,receive_time TIMESTAMP NULL,create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_items(id INT PRIMARY KEY AUTO_INCREMENT,order_id INT NOT NULL,clothes_id INT NOT NULL,clothes_name VARCHAR(100) NOT NULL,price DECIMAL(10,2) NOT NULL,quantity INT NOT NULL,size VARCHAR(10) NOT NULL,FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users(username,password,phone,address,role) VALUES('admin','admin123','13800003333','系统管理','admin');

INSERT INTO clothes(name,style,category,size,price,stock,description) VALUES
('法式茶歇裙','法式','裙装','S|179,M|179,L|199,XL|199',179,70,'V领收腰，碎花印花，浪漫法式田园风'),
('廓形风衣外套','通勤','外套','M|459,L|459,XL|489,XXL|489',459,35,'双面斜纹面料，经典卡其色，气质百搭'),
('绑带雪纺衬衫','甜美','上衣','S|129,M|129,L|149,XL|149',129,90,'轻盈雪纺，飘带系结，温柔知性OL风'),
('高弹瑜伽裤','运动','裤子','S|79,M|79,L|99,XL|99',79,200,'高弹力运动面料，透气速干，运动休闲'),
('波西米亚长裙','民族','裙装','M|249,L|249,XL|269',249,25,'棉麻混纺，民族风印花，度假旅游首选'),
('加厚羊羔绒外套','户外','外套','M|299,L|299,XL|339,XXL|339',299,40,'摇粒羊羔绒，柔软保暖，冬日暖阳感'),
('小香风马甲','优雅','上衣','S|189,M|189,L|209',189,50,'粗花呢面料，精致滚边，名媛风叠穿'),
('编织草帽','度假','配饰','均码|69',69,120,'天然麦秆编织，宽檐遮阳，海边度假感');

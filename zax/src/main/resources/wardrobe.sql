CREATE DATABASE IF NOT EXISTS zax_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE zax_db;

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

INSERT INTO users(username,password,phone,address,role) VALUES('admin','admin123','13800002222','系统管理','admin');

INSERT INTO clothes(name,style,category,size,price,stock,description) VALUES
('垂感西装外套','通勤','外套','S|329,M|329,L|359,XL|359',329,45,'微弹力TR面料，垂坠感极佳，职场气场款'),
('方领泡泡袖上衣','甜美','上衣','S|109,M|109,L|129,XL|129',109,85,'浪漫泡泡袖，方领修饰脸型，气质仙女风'),
('九分烟管裤','通勤','裤子','S|139,M|139,L|159,XL|159,XXL|159',139,110,'显瘦烟管版型，九分露踝，通勤百搭'),
('刺绣牛仔短裤','休闲','裤子','S|89,M|89,L|109,XL|109',89,150,'全棉牛仔面料，精致刺绣点缀，夏日必备'),
('赫本风小黑裙','优雅','裙装','S|199,M|199,L|219,XL|219',199,65,'经典赫本风，收腰A摆，永恒时尚单品'),
('灯芯绒背带裤','复古','裤子','M|179,L|179,XL|199',179,40,'柔软灯芯绒，可调节背带，复古文艺范'),
('真丝吊带衫','优雅','上衣','S|169,M|169,L|189',169,30,'桑蚕丝面料，丝滑亲肤，内搭外穿皆可'),
('格纹渔夫帽','文艺','配饰','均码|59',59,180,'棉麻混纺，格纹图案，文艺遮阳必备');

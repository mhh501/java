CREATE DATABASE IF NOT EXISTS pyh_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pyh_db;

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

INSERT INTO users(username,password,phone,address,role) VALUES('admin','admin123','13800001111','系统管理','admin');

INSERT INTO clothes(name,style,category,size,price,stock,description) VALUES
('复古牛仔夹克','复古','外套','M|259,L|259,XL|289,XXL|289',259,60,'水洗做旧工艺，经典复古廓形，百搭街头'),
('莫代尔吊带裙','甜美','裙装','S|119,M|119,L|139',119,95,'柔软莫代尔面料，垂坠感好，夏日清凉必备'),
('直筒阔腿裤','通勤','裤子','S|149,M|149,L|169,XL|169',149,130,'高腰直筒版型，显瘦显高，通勤休闲两穿'),
('亚麻短袖衬衫','文艺','上衣','M|139,L|139,XL|159',139,80,'天然亚麻面料，透气清爽，文艺复古风格'),
('渐变印花T恤','潮流','上衣','S|89,M|89,L|109,XL|109,XXL|109',89,180,'环保活性印染，渐变色彩，街头潮人单品'),
('针织开衫外套','温柔','外套','M|229,L|229,XL|249',229,55,'软糯针织面料，宽松慵懒风，春秋百搭'),
('机能工装裤','户外','裤子','M|189,L|189,XL|209,XXL|209',189,70,'防水防污面料，多口袋设计，城市机能风'),
('撞色棒球帽','休闲','配饰','均码|49',49,200,'纯棉面料，撞色设计，遮阳时尚百搭');

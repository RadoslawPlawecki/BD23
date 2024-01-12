DROP DATABASE IF EXISTS mac_menu;
CREATE DATABASE mac_menu;

USE mac_menu;

CREATE TABLE categories(
	cat_id CHAR(4) PRIMARY KEY,
	cat_name VARCHAR(30)
);

CREATE TABLE products(
	id INT AUTO_INCREMENT PRIMARY KEY,
	category CHAR(4),
	FOREIGN KEY (category) REFERENCES categories(cat_id),
	item_name VARCHAR(100),
	serving_size DECIMAL(5,2),
	calories INT, fat_calories INT, total_fat INT,
	saturated_fat INT, cholesterole INT, sodium INT,
	carbs INT, fiber INT, sugars INT, protein INT,
	vit_A INT, vit_C INT, calcium INT, iron INT
);

DROP VIEW my_view;
CREATE VIEW my_view AS 
SELECT
	p.category,
	p.item_name,
    p.vit_A,
    p.vit_C,
    p.calcium,
    p.iron
FROM mac_menu.products p
JOIN categories c ON p.category = category;
SELECT * FROM my_view;

USE toolmaster;
SET NAMES 'utf8';
-- Create brands table
CREATE TABLE brand (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	UNIQUE KEY unique_name_brand (name)
);
-- Create cities table
CREATE TABLE city (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	UNIQUE KEY unique_name_city (name)
);
-- Create tools table
CREATE TABLE tool (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	image VARCHAR(255),
	description VARCHAR(255),
	price DECIMAL(24, 12),
	units INT DEFAULT 0,
	brand_id INT,
	FOREIGN KEY (brand_id) REFERENCES brand(id)
);
-- Auxiliary table for tool to cities relationship
CREATE TABLE tool_cities (
	tool_id INT,
	cities_id INT,
	FOREIGN KEY (tool_id) REFERENCES tool(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (cities_id) REFERENCES city(id) ON DELETE CASCADE ON UPDATE CASCADE
);
-- Create user table
CREATE TABLE user (
	id BIGINT PRIMARY KEY,
	name VARCHAR(255),
	last_name VARCHAR(255),
	birth_date DATE,
	city_id INT,
	active BOOLEAN DEFAULT TRUE,
	FOREIGN KEY (city_id) REFERENCES city(id)
);
-- Create authentication table
CREATE TABLE auth (
	user_id BIGINT NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (user_id),
	UNIQUE KEY unique_username (username),
	FOREIGN KEY (user_id) REFERENCES user(id)
);
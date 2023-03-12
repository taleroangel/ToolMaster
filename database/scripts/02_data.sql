USE toolmaster;
SET NAMES 'utf8';

-- Brands
INSERT INTO brand (name)
VALUES ('Stanley'),
	('Bosch'),
	('Dewalt');
-- Cities
INSERT INTO city (name)
VALUES ('Bogotá'),
	('Medellin'),
	('Cali');
-- Users
INSERT INTO user (id, name, last_name, birth_date, city_id, active)
VALUES (1002382383, 'John', 'Doe', '1990-01-01', 1, true),
	(1192734759, 'Jane', 'Doe', '1992-05-12', 2, true),
	(
		1002937466,
		'Bob',
		'Smith',
		'1985-12-25',
		1,
		false
	),
	(
		9218274590,
		'Alice',
		'Johnson',
		'1998-06-30',
		3,
		true
	);
-- Tools
INSERT INTO tool (name, image, description, price, units, brand_id)
VALUES (
		'Martillo',
		'https://belltec.com.co/2060-large_default/martillo-con-mango-de-madera-cabeza-conica-stanley-51271.jpg',
		'Diseño de martillos con Tecnología de vanguardia para gran energia de impacto y eficiencia. Mínimas partes móviles -solo 2- para menos mantenimiento, mejor rendimiento y menos servicio.',
		30500,
		5,
		1
	),
	(
		'Llave',
		'https://http2.mlstatic.com/D_NQ_NP_722788-MCO51107227008_082022-O.jpg',
		'El diseño de llave de desplazamiento permite cambios fáciles de brocas de enrutador en routers que se instalan en tablas de enrutadores',
		205000,
		5,
		2
	),
	(
		'Taladro',
		'https://belltec.com.co/6906-large_default/taladro-de-rotacion-industrial-38-pulg-dewalt-dwd014-b3.jpg',
		'En esta gama de taladros encontrarás modelos con cable y sin cable, que proporcionarán a los usuarios la fuerza par y velocidad ideal para todo tipo de aplicaciones.',
		500000,
		10,
		3
	);
-- Tools and Cities
INSERT INTO tool_cities (tool_id, cities_id)
VALUES (1, 1),
	(2, 2),
	(3, 3);

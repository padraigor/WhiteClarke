DROP TABLE MAKE;
CREATE TABLE IF NOT EXISTS Make
(	id 			int 		PRIMARY KEY AUTO_INCREMENT,
	name 		varchar(20) NOT NULL	UNIQUE
);
DROP TABLE Model;
CREATE TABLE IF NOT EXISTS Model
(	id			int 		PRIMARY KEY AUTO_INCREMENT,
	name 		varchar(20) NOT NULL,
	make 		int			NOT NULL,
	FOREIGN KEY (make) REFERENCES public.Make (id) 
);
DROP TABLE Vehicle;
CREATE TABLE IF NOT EXISTS Vehicle
(	id 			int 		PRIMARY KEY AUTO_INCREMENT,
	reg 		varchar(20) NOT NULL	UNIQUE,	
	model 		int			NOT NULL,
	colour 		varchar(20),
	year 		int,
	engSizeCC 	int,
	FOREIGN KEY (model) REFERENCES public.Model(id)
);
SHOW COLUMNS FROM Make;
SHOW COLUMNS FROM Model;
SHOW COLUMNS FROM Vehicle;
DROP VIEW IF EXISTS NEEDED;
DROP VIEW IF EXISTS AVAILABILITY;
DROP VIEW IF EXISTS AVAILABLE;

DROP TRIGGER IF EXISTS calc_price_insert;
DROP TRIGGER IF EXISTS calc_price_update;
DROP TRIGGER IF EXISTS calc_price_delete;
DROP TRIGGER IF EXISTS update_component;

DROP TABLE IF EXISTS ORDER_ITEMS CASCADE;
DROP TABLE IF EXISTS ORDERS CASCADE;
DROP TABLE IF EXISTS COMPOSITION CASCADE;
DROP TABLE IF EXISTS BOOKS CASCADE;
DROP TABLE IF EXISTS AUTHORS CASCADE;
DROP TABLE IF EXISTS USER_ROLES CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;


CREATE TABLE AUTHORS(
	AUTHOR_ID INT NOT NULL AUTO_INCREMENT, 
	NAME VARCHAR(50),
	SURNAME VARCHAR(50),
	QTY INTEGER,
	C_PRICE INTEGER,
	PRIMARY KEY(AUTHOR_ID)
);



CREATE TABLE BOOKS(
	BOOK_ID INT  NOT NULL AUTO_INCREMENT,
	AUTHOR_ID INT,
	TITLE VARCHAR(200), 
	DESCRIPTION BLOB, 
	PRICE NUMERIC(10,2), 
	AVAILABLE INT,
	PAGES INT,
	PROFIT INTEGER,
	VISIBLE BOOLEAN,
	PRIMARY KEY(BOOK_ID)
);



CREATE TABLE COMPOSITION (
	EL_ID INTEGER,
	COM_ID INTEGER,
	QTY INTEGER,
	PRIMARY KEY(EL_ID, COM_ID),
	FOREIGN KEY (EL_ID) REFERENCES BOOKS(BOOK_ID),
	FOREIGN KEY (COM_ID) REFERENCES AUTHORS(AUTHOR_ID)
);



CREATE TABLE ORDERS(
	ORDER_ID INT NOT NULL AUTO_INCREMENT,
	BUYER_NAME VARCHAR(100),
	SHIPPING_ADRESS VARCHAR(100),
	SHIPPING_ZIPCODE VARCHAR(10),
	SHIPPING_CITY VARCHAR(30),
	PRIMARY KEY(ORDER_ID)
);



CREATE TABLE ORDER_ITEMS(
	ORDER_ITEM_ID INT NOT NULL AUTO_INCREMENT,
	ORDER_ID INT,
	BOOK_ID INT,
	QUANTITY INT,
	PRIMARY KEY(ORDER_ITEM_ID),
	FOREIGN KEY(ORDER_ID) REFERENCES ORDERS(ORDER_ID),
	FOREIGN KEY(BOOK_ID) REFERENCES BOOKS(BOOK_ID)
);



CREATE TABLE USERS(
       USER_NAME varchar(15) not null primary key,
       USER_PASS varchar(15) not null,
       NAME varchar(100) not null,
       STREET_ADDRESS varchar(100)not null,
       ZIP_CODE varchar(10) not null,
       CITY varchar(30) not null,
       COUNTRY varchar(30) not null
);

CREATE TABLE USER_ROLES(
	USER_NAME varchar(15) not null,
	ROLE_NAME varchar(15) not null,
	primary key (USER_NAME, ROLE_NAME)
);


CREATE VIEW NEEDED AS SELECT BOOKS.BOOK_ID,BOOKS.TITLE, BOOKS.DESCRIPTION, COMPOSITION.COM_ID, AUTHORS.NAME, AUTHORS.SURNAME, BOOKS.VISIBLE, COMPOSITION.QTY, AUTHORS.QTY AS IN_STOCK, AUTHORS.C_PRICE, BOOKS.PROFIT FROM BOOKS LEFT JOIN (COMPOSITION CROSS JOIN AUTHORS) ON (AUTHORS.AUTHOR_ID=COMPOSITION.COM_ID AND COMPOSITION.EL_ID = BOOKS.BOOK_ID);

CREATE VIEW AVAILABILITY AS SELECT BOOK_ID, TITLE, MIN(FLOOR(IN_STOCK / QTY)) AS AVAILABILITY,
								   IF((SUM(QTY*C_PRICE) + PROFIT) IS NULL, PROFIT, (SUM(QTY*C_PRICE) + PROFIT))  AS FINAL_PRICE
	FROM NEEDED GROUP BY BOOK_ID;


CREATE VIEW AVAILABLE AS
	SELECT BOOK_ID, MIN(FLOOR(A.QTY / C.QTY)) AS AVAILABLE,
			(SUM(C.QTY * C_PRICE) + PROFIT) AS FINAL_PRICE
	FROM BOOKS, COMPOSITION AS C, AUTHORS AS A
	WHERE BOOK_ID = EL_ID AND COM_ID = A.AUTHOR_ID
	GROUP BY BOOK_ID;


CREATE TRIGGER calc_price_insert
	AFTER INSERT ON COMPOSITION FOR EACH ROW
	UPDATE BOOKS AS B, AVAILABLE AS A
	SET B.PRICE = A.FINAL_PRICE, B.AVAILABLE = A.AVAILABLE
	WHERE B.BOOK_ID = NEW.EL_ID AND A.BOOK_ID = NEW.EL_ID;


CREATE TRIGGER calc_price_update
	AFTER UPDATE ON COMPOSITION FOR EACH ROW
	UPDATE BOOKS AS B, AVAILABLE AS A
	SET B.PRICE = A.FINAL_PRICE, B.AVAILABLE = A.AVAILABLE
	WHERE B.BOOK_ID = NEW.EL_ID AND A.BOOK_ID = NEW.EL_ID;


CREATE TRIGGER calc_price_delete
	AFTER DELETE ON COMPOSITION FOR EACH ROW
	UPDATE BOOKS AS B, AVAILABLE AS A
	SET B.PRICE = A.FINAL_PRICE, B.AVAILABLE = A.AVAILABLE
	WHERE B.BOOK_ID = OLD.EL_ID AND A.BOOK_ID = OLD.EL_ID;




INSERT INTO AUTHORS(NAME, SURNAME) VALUES('INTEL',    'CPU 8086');
INSERT INTO AUTHORS(NAME, SURNAME) VALUES('MACROMEDIA', 'ULTRADEV');
INSERT INTO AUTHORS(NAME, SURNAME) VALUES('SEGA',    'LOTUS 123');
INSERT INTO AUTHORS(NAME, SURNAME) VALUES('CREATIVE',     'SOUND BLASTER AWE 32');

INSERT INTO BOOKS(AUTHOR_ID, TITLE, DESCRIPTION, PRICE, PAGES) VALUES(
	1,
	'AI',
	'IT WILL ARGUE',
	600, 392);
INSERT INTO BOOKS(AUTHOR_ID, TITLE, DESCRIPTION, PRICE, PAGES) VALUES(
	2,
	'ENIAC',
	'OLD AND STABLE',
	550, 537);
INSERT INTO BOOKS(AUTHOR_ID, TITLE, DESCRIPTION, PRICE, PAGES) VALUES(
	2,
	'HOME COMPUTER',
	'PRETTY BUT NOT WORKING',
	650, 437);
INSERT INTO BOOKS(AUTHOR_ID, TITLE, DESCRIPTION, PRICE, PAGES) VALUES(
	3,
	'SUPER SERVER',
	'ALL YOU NEED',
	400, 351);
INSERT INTO BOOKS(AUTHOR_ID, TITLE, DESCRIPTION, PRICE, PAGES) VALUES(
	4,
	'COMMODORE 64',
	'GAMING CONSOLE',
	430, 800);

INSERT INTO USERS(USER_NAME, USER_PASS, NAME, STREET_ADDRESS, ZIP_CODE, CITY, COUNTRY) 
     VALUES('tomcat','tacmot','Tom Cat','Apache Road', '34 567', 'Petaluma', 'USA');
INSERT INTO USERS(USER_NAME, USER_PASS, NAME, STREET_ADDRESS, ZIP_CODE, CITY, COUNTRY) 
     VALUES('gyro','glurk','Gyro Gearloose','Duck Road', '78 901', 'Ducksbury', 'USA');
INSERT INTO USERS(USER_NAME, USER_PASS, NAME, STREET_ADDRESS, ZIP_CODE, CITY, COUNTRY) 
     VALUES('admin', 'glurk','System user', 'Polacksbacken', '752 37', 'Uppsala', 'Sweden');

INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('tomcat','tomcat');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('gyro',  'tomcat');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'manager');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'admin');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'tomcat');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'manager-script');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'manager-gui');
INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME) VALUES('admin', 'admin-gui');

UPDATE BOOKS SET PROFIT = 10 WHERE BOOK_ID > 0;
UPDATE BOOKS SET VISIBLE = TRUE WHERE BOOK_ID > 0;
UPDATE AUTHORS SET QTY = 100 WHERE AUTHOR_ID > 0;
UPDATE AUTHORS SET C_PRICE = 150 WHERE AUTHOR_ID > 0;


INSERT INTO COMPOSITION VALUES (1,1,2);
INSERT INTO COMPOSITION VALUES (1,2,3);
INSERT INTO COMPOSITION VALUES (2,3,3);
INSERT INTO COMPOSITION VALUES (3,1,3);
INSERT INTO COMPOSITION VALUES (3,2,2);
INSERT INTO COMPOSITION VALUES (3,3,3);
INSERT INTO COMPOSITION VALUES (4,3,3);
INSERT INTO COMPOSITION VALUES (4,2,1);
INSERT INTO COMPOSITION VALUES (4,1,10);
INSERT INTO COMPOSITION VALUES (5,1,10);

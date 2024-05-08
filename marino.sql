CREATE DATABASE  IF NOT EXISTS marino;
USE marino;

SELECT * from user;


 -- TABLE CREATION STATEMENTS

DROP TABLE IF EXISTS Admin;

CREATE TABLE Admin (
  emailid varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  PRIMARY KEY (emailid)
);

DROP TABLE IF EXISTS user;

CREATE TABLE user (
  userid int NOT NULL auto_increment,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  age varchar(45) DEFAULT NULL,
  phone_number varchar(45) DEFAULT NULL,
  emailid varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  PRIMARY KEY (userid)
);

DROP TABLE IF EXISTS staff;
CREATE TABLE staff (
  idstaff int NOT NULL auto_increment,
  staff_name varchar(45) NOT NULL,
  staff_age int NOT NULL,
  phone_number int NOT NULL,
  emailid varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  PRIMARY KEY (idstaff)
) ;

DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
  idpayment int NOT NULL auto_increment,
  date date DEFAULT NULL,
  amount int DEFAULT NULL,
  no_of_activities int DEFAULT NULL,
  user_id int DEFAULT NULL,
  PRIMARY KEY (idpayment),
  CONSTRAINT user_id
  FOREIGN KEY (user_id) 
  REFERENCES user (userid)
  on update restrict on delete cascade
) ;

CREATE TABLE locker (
  idlocker int NOT NULL auto_increment,
  type_of_locker varchar(45) NOT NULL,
  idstaff int NOT NULL,
  userid int NULL,
  PRIMARY KEY (idlocker),
  KEY userid_idx (userid),
  KEY idstaff_idx (idstaff),
  CONSTRAINT idstaff
  FOREIGN KEY (idstaff)
  REFERENCES staff (idstaff)
  on update restrict on delete cascade,
  CONSTRAINT userid 
  FOREIGN KEY (userid)
  REFERENCES user (userid)
  on update restrict on delete cascade
);


DROP TABLE IF EXISTS equipment;

CREATE TABLE equipment (
  idequipment int NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  idstaff int NOT NULL,
  idactivity int NOT NULL,
  PRIMARY KEY (idequipment),
  CONSTRAINT idstaff_fk_equipment
  FOREIGN KEY (idstaff)
  references staff (idstaff),
  -- on update restrict on delete cascade,
  CONSTRAINT idactivity_fk_equipment
  FOREIGN KEY (idactivity)
  References activity (idactivity)
  -- on update restrict on delete cascade
);

DROP TABLE IF EXISTS activity;

CREATE TABLE activity (
  idactivity int NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  room_no int NOT NULL,
  price INT NOT NULL,
  idtrainer int NOT NULL,
  PRIMARY KEY (idactivity),
  CONSTRAINT idtrainer_fk_activity
  FOREIGN KEY (idtrainer)
  REFERENCES trainer (idtrainer)
  ON UPDATE RESTRICT ON DELETE CASCADE
);


DROP TABLE IF EXISTS trainer;
CREATE TABLE trainer (
  idtrainer int NOT NULL auto_increment,
  Name varchar(45) NOT NULL,
  age int NOT NULL,
  phone_number int NOT NULL,
  PRIMARY KEY (idtrainer)
);
DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
  idpayment INT NOT NULL auto_increment,
  idactivity INT NOT NULL,
  userid INT NOT NULL,
  PRIMARY KEY (idpayment),
  FOREIGN KEY (idactivity) REFERENCES activity(idactivity)
);

ALTER TABLE marino.locker MODIFY userid int NULL;
ALTER TABLE marino.equipment MODIFY idactivity int NULL;
ALTER TABLE marino.activity MODIFY idtrainer int NULL;



 -- INSERT STATEMENTS AND ALTER STATEMENTS

INSERT INTO Admin VALUES 
('Arpan@gmail.com','arpan'),
('gagana@gmail.com','gagana');

INSERT INTO marino.staff VALUES ('601', 'Gagana A', '23', '980637282', 'gagana@gmail.com', 'gags#12345'),
 ('602', 'Wennie', '24', '986745382', 'wen@gmail.com', 'wen#12345'),
('603', 'Kiara', '25', '874827364', 'kiara@gmail.com', 'kiara#12345'),
('604','Neha', '23', '89983766', 'neha@gmail.com', 'neha#12345');


INSERT INTO marino.user
VALUES ('001', 'Arpan', 'Patel', '23', '7861319272', 'arpan@yahoo.com', 'arpan@12345'),
('002', 'Jin', 'Kim', '29', '7869462738', 'jin@gmail.com', 'jin@12345'),
('003', 'Hope', 'Mikalson', '19', '7289462782', 'hope@gmail.com', 'hope@12345');

INSERT INTO marino.locker VALUES ('901', 'Standard', '603', '3'),
('902', 'Personal', '601', '2'),('903', '2-tier', '602', '1');


INSERT INTO marino.equipment VALUES ('1101', 'soccer ball.', '603', '17001'),
('1102', 'Flying disc', '601', '17005'),('1103', 'Racquets', '601', '17003')
,('1104', 'Nets', '601', '17003'),('1105', 'Sticks', '602', '17006'),
('1106', 'Bats', '601', '17002');


INSERT INTO marino.equipment (idequipment, name, idstaff, idactivity) VALUES
(1101, 'soccer ball', 603, 17001),
(1102, 'Flying disc', 601, 17005),
(1103, 'Racquets', 601, 17003),
(1104, 'Nets', 601, 17003),
(1105, 'Sticks', 602, 17003),
(1106, 'Bats', 601, 17002);

INSERT INTO marino.activity (idactivity, name, room_no, price, idtrainer) VALUES
(17001, 'Soccer', 41, 2000, 301),
(17002, 'Cricket', 42, 1500, 303),
(17003, 'Tennis', 43, 850, 302),
(17004, 'Squash', 44, 900, 302),
(17005, 'Disc Golf', 46, 1875, 301);

 
INSERT INTO marino.trainer (idtrainer, name, age, phone_number) VALUES
(301, 'Chris', 25, 784637380),
(302, 'Felix', 24, 23432546),
(303, 'Alex', 25, 33452878);

INSERT INTO payment (idpayment, idactivity, userid) VALUES 
('1001', '17003', '1'), 
('1002', '17003', '2'), 
('1003', '17002', '3'), 
('1004', '17001', '4'), 
('1005', '17002', '5');

-- CHECK THE TABLES
SELECT * from locker;
SELECT* from user;
SELECT* from activity;
SELECT* from locker;
SELECT* from trainer;
SELECT* from equipment;
SELECT* from payment;

-- 

-- select p.idpayment,a.price,a.idactivity,a.name,u.userid,u.first_name from payment as p JOIN activity as a ON p.idactivity=a.idactivity JOIN user as u ON p.userid=u.userid;


-- STORED PROCEDURES

 delimiter //
 create procedure staff_reg(IN staff_name varchar(40),IN staff_age int, 
 IN phone_number VARCHAR(10),IN emailid varchar(46), IN password varchar(40) )
 begin
 insert into marino.staff(staff_name,staff_age,phone_number,emailid,password) 
 values(staff_name,staff_age,phone_number,emailid,password);
 end
 //
--    
-- call staff_reg('Christpher',29,235672876,'christopher@gmail.com','christopher');

delimiter $$
create procedure user_reg(IN first_name varchar(40),IN last_name varchar(40),IN age int, 
IN phone_number VARCHAR(10),IN emailid varchar(46), IN password varchar(40) )
begin
insert into marino.user(first_name,last_name,age,phone_number,emailid,password) 
values(first_name,last_name,age,phone_number,emailid,password);
end
$$
--    
 -- call user_reg('Christopher','Janet',25,675634876,'christopher@gmail.com','christopher');

 delimiter $$
 create procedure staff_del(IN email_id varchar(46), IN pwd varchar(40) )
 begin
 delete from staff WHERE emailid = email_id AND password = pwd;
 end
 $$

 -- SET SQL_SAFE_UPDATES = 0;

 -- call staff_del('christoper@gmail.com','christoper');

delimiter $$
create procedure user_del(IN email_id varchar(46), IN pwd varchar(40) )
begin
delete from user WHERE emailid = email_id AND password = pwd;
end
$$

DROP PROCEDURE IF EXISTS add_locker;

DELIMITER $$

CREATE PROCEDURE add_locker(
  IN type_of_locker VARCHAR(40), 
  IN idstaff INT, 
  IN userid INT
)
BEGIN
  -- Check if `userid` is provided (greater than 0); if not, set `NULL`
  INSERT INTO marino.locker (type_of_locker, idstaff, userid)
  VALUES (type_of_locker, idstaff, 
          IF(userid > 0, userid, NULL));
END $$

DELIMITER ;

-- CALL add_locker('Standard', 603,0);

delimiter $$
create procedure locker_reg(IN type_of_locker varchar(40),IN idstaff int, IN userid int )
begin
insert into marino.locker(type_of_locker,idstaff,userid) 
values(type_of_locker,idstaff,0);
end
$$


DROP PROCEDURE IF EXISTS locker_reg;

DELIMITER $$
CREATE PROCEDURE locker_reg(IN type_of_locker VARCHAR(40), IN idstaff INT, IN userid INT)
BEGIN
  INSERT INTO marino.locker (type_of_locker, idstaff, userid) 
  VALUES (type_of_locker, idstaff, userid);
END
$$
DELIMITER ;
   

-- CALL locker_reg('Standard', 603,5);

delimiter $$
create procedure locker_del(IN id_locker INT )
begin
Delete from locker where idlocker = id_locker;
end
$$

-- call locker_del(910);

delimiter $$
 create procedure equipment_reg(IN name varchar(40),IN idstaff int,IN idactivity INT )
   begin
   Insert into marino.equipment(name,idstaff,idactivity) values(name,idstaff,idactivity);
   end
   $$
   
-- call equipment_reg('hockey stick',603,17003);

delimiter $$
create procedure equipment_del(IN id_equipment INT )
begin
Delete from equipment where idequipment = id_equipment;
end
$$

-- call equipment_del(904);
 
DROP procedure IF exists activity_reg;

 delimiter $$
 create procedure activity_reg(IN name_ varchar(40),IN room_no_ int,IN price_ int, IN idtrainer varchar(40))
 begin 
 Insert into marino.activity (name,room_no,price,idtrainer) values (name_,room_no_,price_,idtrainer);
end
 $$
 
-- call activity_reg('foosball',97,10,301);


-- select p.idpayment,a.price,a.idactivity,a.name,u.userid,u.first_name 
-- from payment as p JOIN activity as a ON p.idactivity=a.idactivity JOIN user as u ON p.userid=u.userid where u.userid=3
-- GROUP BY p.idpayment ;

 delimiter $$
 create procedure activity_del(IN id_activity INT )
 begin
 Delete from activity where idactivity = id_activity;
end
$$
-- Call activity_del(17006);

delimiter $$
create procedure activity_Equip_table()
begin
SELECT a.idactivity,a.name,a.room_no,e.idequipment,e.name 
from activity as a JOIN equipment as e ON a.idactivity=e.idactivity;
end
$$
   
-- call activity_Equip_table()

delimiter $$
create procedure payment_del(in idactivity_ int)
begin
DELETE FROM payment where idactivity=idactivity_;
end
$$



-- DROP PROCEDURE IF EXISTS user_activity;
-- Error Code: 1054. Unknown column 'idactivity' in 'field list'

delimiter $$
create procedure user_activity(IN idactivity_ int, IN userid_ INT)
begin
Insert into payment(idactivity,userid) values (idactivity_,userid_);
end
$$

-- call user_activity(17002,3);
-- select* from payment;

-- DROP FUNCTION IF EXISTS marino.totalPayment;

 DELIMITER //
 CREATE FUNCTION totalPayment(id INT)
 RETURNS INT
DETERMINISTIC READS SQL DATA 
BEGIN
  DECLARE total_payment INT;
select SUM(a.price)
into total_payment
from payment as p JOIN activity as a ON p.idactivity=a.idactivity 
JOIN user as u ON p.userid=u.userid where u.userid = id;
  RETURN(total_payment);
END//


 -- SELECT  totalPayment(3) from payment LIMIT 1;



delimiter $$
create procedure trainer_reg(IN name_ varchar(100), IN age_ INT, IN phone_number_ int)
begin
Insert into marino.trainer(Name,age,phone_number) values (name_,age_,phone_number_);
end
$$


-- call trainer_reg('Jack',26,1825437);


-- SELECT COUNT(idtrainer) from activity where idtrainer=0;

DELIMITER //
CREATE FUNCTION Activity_without_trainers()
RETURNS INT
DETERMINISTIC READS SQL DATA 
BEGIN
DECLARE No_activity_without_trainers INT;
SELECT COUNT(idtrainer)
into No_activity_without_trainers
from activity where idtrainer=0;
RETURN(No_activity_without_trainers);
END//

SElect Activity_without_trainers() from activity;

SELECT COUNT(userid) from locker where userid=0;

DROP FUNCTION IF EXISTS function_name;


DELIMITER //
CREATE FUNCTION locker_without_users()
RETURNS INT
DETERMINISTIC READS SQL DATA 
BEGIN
DECLARE No_locker_without_users INT;
SELECT COUNT(userid)
into No_locker_without_users
from locker where userid=null limit 1;
RETURN(No_locker_without_users);
END//

SElect locker_without_users() from activity limit 1;

SELECT COUNT(idactivity) from equipment where idactivity=0;


DELIMITER //
CREATE FUNCTION equipment_without_activity()
RETURNS INT
DETERMINISTIC READS SQL DATA 
BEGIN
DECLARE No_equipment_without_activity INT;
SELECT COUNT(idactivity)
into No_equipment_without_activity
from equipment where idactivity=0;
RETURN(No_equipment_without_activity);
END//

-- SElect equipment_without_activity() from activity limit 1;


-- select p.idpayment,a.price,a.idactivity,a.name from payment as p JOIN activity as a ON p.idactivity=a.idactivity JOIN user as u ON p.userid=u.userid where u.userid= 10 GROUP BY p.idpayment



drop database if exists StudentDB;
create database StudentDB;
use StudentDB;

create table student(
	no varchar(6) not null primary key,
    name varchar(5) not null,
    kor tinyint not null,
    eng tinyint not null,
    math tinyint not null,
    total smallint null,
    avr decimal(7,2) null,
    grade char(1) null,
    rate tinyint null
);

insert into student values('123456', '김자바', 90, 100, 95, 285, 95, 'A', 1);
insert into student(no,name,kor,eng,math,total,avr,grade) values ('123458','김미니',100,100,100,300,100,'A');


drop procedure if exists procedure_insert_student;
delimiter $$
create procedure procedure_insert_student(
	In in_no char(6),
    In in_name varchar(10),
    in in_kor int,
    in in_eng int,
    in in_math int
   )
begin
    declare in_total int;
    declare in_avr double;
    declare in_grade varchar(2);
    SET in_total = in_kor + in_eng + in_math;
    SET in_avr = in_total / 3.0;
    SET in_grade =
		CASE
			when in_avr >= 90.0 then 'A'
            when in_avr >= 80.0 then 'B'
            when in_avr >= 70.0 then 'C'
            when in_avr >= 60.0 then 'D'
            else 'F'
            end;
            
     insert into student(no, name, kor, eng, math)
	 values(in_no, in_name, in_kor, in_eng, in_math);
        
     update student set total = in_total, avr = in_avr, grade = in_grade
     where no = in_no;
 
 end $$
 delimiter ;
call procedure_insert_student();

drop procedure if exists procedure_update_student;
DELIMITER $$
CREATE PROCEDURE procedure_update_student(
	In in_no char(6),
    in in_kor int,
    in in_eng int,
    in in_math int
)
BEGIN
	DECLARE in_total INT;
	DECLARE in_avr DOUBLE;
	DECLARE in_grade VARCHAR(2);
	SET in_total = in_kor  + in_eng  + in_math;
	SET in_avr = (in_total / 3.0);
	SET in_grade = 
		CASE
			WHEN in_avr >= 90.0 THEN 'A'
			WHEN in_avr >= 80.0 THEN 'B'
			WHEN in_avr >= 70.0 THEN 'C'
			WHEN in_avr >= 60.0 THEN 'D'
			ELSE 'F'
		END;
    update student set kor = in_kor, eng = in_eng, math = in_math, total = in_total, avr = in_avr, grade = in_grade where no = in_no;
END$$
DELIMITER ;


use studentdb;

create table deletestudent(
no char(6) not null,
name varchar(10) not null,
kor tinyint not null,
eng tinyint not null,
math tinyint not null,
total smallint null,
avr double null,
grade varchar(2) null,
rate int null,
deletedate datetime
);

create table updatestudent(
no char(6) not null,
name varchar(10) not null,
kor tinyint not null,
eng tinyint not null,
math tinyint not null,
total smallint null,
avr double null,
grade varchar(2) null,
rate int null,
updatedate datetime
);
describe student;

delimiter !!
create trigger trg_deleteStudent 
    after delete 
    on student
    for each row 
begin  
   INSERT INTO deletestudent VALUES
(old.no, old.name, old.kor, old.eng, old.math, old.total,
old.avr,old.grade,old.rate,now());

end !!
delimiter ;

select * from student;
select * from deletestudent;


delimiter !!
create trigger trg_updateStudent
	after update
    on student
    for each row
begin
	INSERT INTO updatestudent VALUES
(old.no, old.name, old.kor, old.eng, old.math, old.total,
old.avr,old.grade,old.rate,now());
end !!
delimiter ;

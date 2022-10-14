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
-- 삭제
-- delete from student where no = '123456';
-- update student set rate = 1 where name = '김미니';
-- 3) 수정
update student set kor = 88, eng = 88, math = 88, total = 264, avr = 88, grade = 'B' where no = '123458';
select * from student;
-- 4)정렬 : 학번, 이름, 총점
select * from student order by no asc;
-- 5)최대값, 최소값 구하기
select max(total) from student;
select min(total) from student;
-- 6)total = 300인 사람의 정보를 출력.(서브쿼리문)
select * from student where total = (select max(total) from student);
select * from student where total = (select min(total) from student);
delete from student where name = '김자바';
-- 프로시저 생성(합계, 평균, 등급 계산하는 함수)

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
	-- 총점, 평균, 등급 변수 선언
    declare in_total int;
    declare in_avr double;
    declare in_grade varchar(2);
    -- 총점계산
    SET in_total = in_kor + in_eng + in_math;
    -- 평균계산
    SET in_avr = in_total / 3.0;
    -- 등급계산
    SET in_grade =
		CASE
			when in_avr >= 90.0 then 'A'
            when in_avr >= 80.0 then 'B'
            when in_avr >= 70.0 then 'C'
            when in_avr >= 60.0 then 'D'
            else 'F'
            end;
            
     -- 삽입 insert into student() vlaues();
     insert into student(no, name, kor, eng, math)
	 values(in_no, in_name, in_kor, in_eng, in_math);
        
     -- 수정 update student set 총점, 평균, 등급 where id = 등록한 아이디;
     update student set total = in_total, avr = in_avr, grade = in_grade
     where no = in_no;
 
 end $$
 delimiter ;
call procedure_insert_student();

DELIMITER $$
CREATE PROCEDURE procedure_update_data(
	In in_no char(6),
    In in_name varchar(10),
    in in_kor int,
    in in_eng int,
    in in_math int
)
BEGIN
	DECLARE in_total INT;
	DECLARE in_avr DOUBLE;
	DECLARE in_grade VARCHAR(2);
	SET in_total = (in_kor  + in_eng  + in_math);
	SET in_avr = (in_total / 3.0);
	SET in_grade = 
CASE
	WHEN in_avr >= 90.0 THEN 'A'
	WHEN in_avr >= 80.0 THEN 'B'
	WHEN in_avr >= 70.0 THEN 'C'
	WHEN in_avr >= 60.0 THEN 'D'
	WHEN in_avr >= 50.0 THEN 'E'
	ELSE 'F'
END;
UPDATE student 
SET 
    kor = in_kor,
    eng = in_eng,
    math = in_math,
    total = in_total,
    avr = in_avr,
    grade = in_grade
WHERE
    no = in_no;
END$$
DELIMITER ;

-- drop table if exists student;
-- drop database if exists StudentDB;

use studentdb;

-- 삭제할 저장소 생성
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

--  트리거 생성
delimiter !!
create trigger trg_deleteStudent -- 트리거 이름
   after delete -- 삭제 후에 작동하게 지정
    on student -- 트리거를 부착할 테이블
    for each row -- 각 행마다 적용시킴
begin  -- old : 테이블의 내용을 백업테이블에 삽입
   INSERT INTO deletestudent VALUES
(old.no, old.name, old.kor, old.eng, old.math, old.total,
old.avr,old.grade,old.rate,now());

end !!
delimiter ;

select * from student;
select * from deletestudent;


delimiter !!
create trigger trg_updateStudent
	before update
    on student
    for each row
begin
	INSERT INTO updatestudent VALUES
(old.no, old.name, old.kor, old.eng, old.math, old.total,
old.avr,old.grade,old.rate,now());
end !!
delimiter ;

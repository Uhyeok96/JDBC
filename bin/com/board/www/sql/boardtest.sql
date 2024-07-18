drop table board; -- 기존 board 테이블 삭제

create table board(
bno number(5) primary key,
btitle nvarchar2(30) not null,
bcontent nvarchar2(1000) not null,
bwriter nvarchar2(10) not null,
bdate date not null
); -- board 테이블 생성

drop sequence board_seq; -- 기본 자동생성번호 삭제
create sequence board_seq increment by 1 start with 1 nocycle nocache;

insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '후기입니다.', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', to_timestamp('2024-07-01 17:55:10', 'yyyy-mm-dd hh24:mi:ss'));
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '싱글룸 후기남겨요', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '솔직 리뷰', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '잘 쉬다 갑니다', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '좋은 여행이었어요', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
	values (board_seq.nextval, '안녕히계세요.', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
	
select * from board;

-- member 테이블 용

create table member (
mno number(5) not null,
mid nvarchar2(10) primary key,
mpw nvarchar2(10) not null,
mnickname nvarchar2(10) unique not null,
mdate date not null
);


-- 더미데이터
insert into member (mno, mid, mpw, mnickname, mdate)
	values (board_seq.nextval, '김우혁', '1234', '별명1', sysdate);
insert into member (mno, mid, mpw, mnickname, mdate)
	values (board_seq.nextval, '김태희', '1234', '별명2', sysdate);
insert into member (mno, mid, mpw, mnickname, mdate)
	values (board_seq.nextval, '이현우', '1234', '별명3', sysdate);
insert into member (mno, mid, mpw, mnickname, mdate)
	values (board_seq.nextval, '김정하', '1234', '별명4', sysdate);
	
select * from member;

drop table member;




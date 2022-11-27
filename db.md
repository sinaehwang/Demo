```sql
#DB생성
DROP DATABASE IF EXISTS Demo_DB;
CREATE DATABASE Demo_DB;
USE  Demo_DB;

#게시판생성
CREATE TABLE article(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` CHAR(100) NOT NULL 
);

#게시글테이블에 memberId컬럼추가
ALTER TABLE
article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

#게시글테이블에 delStatus컬럼추가
ALTER TABLE
article ADD COLUMN delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부'

SELECT *FROM article


#회원테이블생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 회원, 테스트 데이터 생성(관리자)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNo = '01011111111',
email = 'godvv29756318@gmail.com';

# 회원, 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
nickname = '사용자1',
cellphoneNo = '01011111111',
email = 'godvv29756318@gmail.com';

# 회원, 테스트 데이터 생성(일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
nickname = '사용자2',
cellphoneNo = '01011111111',
email = 'godvv29756318@gmail.com';

# 로그인비번 칼럼의 길이를 100으로 늘림
ALTER TABLE `member` MODIFY COLUMN loginPw VARCHAR(100) NOT NULL;

# 기존 회원의 비밀번호를 암호화 해서 저장
UPDATE `member`
SET loginPw = SHA2(loginPw, 256);

SELECT*FROM `member`;
# 게시물 테이블에 회원번호 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

# 게시물 테이블에 삭제여부 칼럼 추가
ALTER TABLE article ADD COLUMN delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부'

#회원번호 2번으로 셋팅
UPDATE article
SET memberId = 2
WHERE memberId = 0;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '번호',
    regDate DATETIME NOT NULL COMMENT '작성날짜',
    updateDate DATETIME NOT NULL COMMENT '수정날짜',
    `code` CHAR(20) NOT NULL UNIQUE COMMENT '코드',
    `name` CHAR(20) NOT NULL UNIQUE COMMENT '이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=삭제 전,1=삭제 후)',
    delDate DATETIME COMMENT '삭제날짜',
    hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '조회수',
    repliesCount INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '댓글수',
    likeCount INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '좋아요수',
    dislikeCount INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '싫어요수'
);

# 기본게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';


# 기본게시판 생성
INSERT INTO board
SET regDate =NOW(),
updateDate = NOW(),
`code`= 'free1',
`name` = '커뮤니티게시판';


# 기본게시판 생성
INSERT INTO board
SET regDate =NOW(),
updateDate = NOW(),
`code`= 'share',
`name` = '레시피공유게시판';

#임시게시글갯수늘리기
INSERT INTO article
(
regDate,updateDate,memberId,boardId,title,`body`
)
SELECT NOW(),NOW(),FLOOR(RAND()*2)+1,FLOOR(RAND()*2)+1,CONCAT('제목_',RAND()),CONCAT('내용_',RAND())
FROM article;
SELECT FLOOR(RAND()*2)+1

# 게시판 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

# 파일 테이블 추가
CREATE TABLE genFile (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, # 번호
  regDate DATETIME DEFAULT NULL, # 작성날짜
  updateDate DATETIME DEFAULT NULL, # 갱신날짜
  delDate DATETIME DEFAULT NULL, # 삭제날짜
  delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, # 삭제상태(0:미삭제,1:삭제)
  relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입(article, member)
  relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 번호
  originFileName VARCHAR(100) NOT NULL, # 업로드 당시의 파일이름
  fileExt CHAR(10) NOT NULL, # 확장자
  typeCode CHAR(20) NOT NULL, # 종류코드 (common)
  type2Code CHAR(20) NOT NULL, # 종류2코드 (attatchment)
  fileSize INT(10) UNSIGNED NOT NULL, # 파일의 사이즈
  fileExtTypeCode CHAR(10) NOT NULL, # 파일규격코드(img, video)
  fileExtType2Code CHAR(10) NOT NULL, # 파일규격2코드(jpg, mp4)
  fileNo SMALLINT(2) UNSIGNED NOT NULL, # 파일번호 (1)
  fileDir CHAR(20) NOT NULL, # 파일이 저장되는 폴더명
  PRIMARY KEY (id),
  KEY relId (relId,relTypeCode,typeCode,type2Code,fileNo)
);

# 카테고리 테이블 생성
CREATE TABLE category (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '번호',
    regDate DATETIME NOT NULL COMMENT '작성날짜',
    updateDate DATETIME NOT NULL COMMENT '수정날짜',
    `code` CHAR(20) NOT NULL UNIQUE COMMENT '코드',
    `name` CHAR(20) NOT NULL UNIQUE COMMENT '이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=삭제 전,1=삭제 후)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 카테고리 코드 생성
INSERT INTO category
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'korea',
`name` = '한식';

INSERT INTO category
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'china',
`name` = '중식';

INSERT INTO category
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'Italy',
`name` = '양식';

# 게시판 테이블에 catergoryId 칼럼 추가
ALTER TABLE article ADD COLUMN catergoryId INT(10) UNSIGNED NOT NULL AFTER `boardId`;

UPDATE article SET
catergoryId = 1
WHERE catergoryId =0

# 댓글 테이블 생성
CREATE TABLE `reply` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '번호',
    regDate DATETIME NOT NULL COMMENT '작성날짜',
    updateDate DATETIME NOT NULL COMMENT '수정날짜',
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련 데이터 ID',
    memberId INT(10) UNSIGNED NOT NULL COMMENT '회원 ID',
    `body` TEXT NOT NULL COMMENT '내용'
);



# 부가정보테이블(모든메인테이블에서 컬럼으로 추가하기 애매한 컬럼들을 모아두는곳)
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL,
    `relId` INT(10) UNSIGNED NOT NULL,
    `typeCode` CHAR(30) NOT NULL,
    `type2Code` CHAR(70) NOT NULL,
    `value` TEXT NOT NULL
);

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`);

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`;




SELECT*FROM category
SELECT*FROM board
SELECT*FROM genFile
SELECT*FROM article
SELECT*FROM `member`
SELECT*FROM `reply`
SELECT*FROM attr





SELECT *FROM article AS caterforyname
LEFT JOIN category
WHERE article.id=category.id

		
		

		
		

```


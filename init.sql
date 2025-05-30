-- 연결 문자셋 설정
SET NAMES utf8mb4;

-- db 생성
CREATE
DATABASE IF NOT EXISTS itsm
    CHARACTER SET utf8mb4;
USE itsm;

-- user 생성
CREATE
USER IF NOT EXISTS 'itsmart'@'%' IDENTIFIED WITH caching_sha2_password BY 'dkdlxltmakxm1!';

-- 권한 부여
GRANT ALL PRIVILEGES ON `itsm`.* TO 'itsmart'@'%' with grant option;
flush privileges;

-- 테이블 생성
create table TB_USER_PROFILE_INFO
(
    user_id         varchar(10)      not null comment '직원 아이디'
        primary key,
    user_pw         varchar(120)     not null comment '비밀번호',
    user_nm         varchar(6)       not null comment '직원명',
    user_position   varchar(3)       not null comment '직급/직위',
    user_birth      varchar(8)       not null comment '직원 생년월일',
    user_department varchar(3)       not null comment '소속',
    hire_date       varchar(8)       not null comment '입사일',
    user_phone      varchar(11)      not null comment '휴대전화',
    user_address    varchar(50)      not null comment '주소',
    user_role       char default '1' not null comment '권한 (0: admin, 1: user)',
    use_yn          char default 'Y' not null comment '사용 여부',
    file_seq        int              null comment '파일 순번',
    created_date    timestamp        not null comment '생성일시',
    modified_date   timestamp        not null comment '수정일시',
    creator         varchar(10)      not null comment '생성자',
    modifier        varchar(10)      not null comment '수정자'
)
    comment '직원 프로필 정보 테이블' charset = utf8mb4;

create table TB_USER_PROFILE_INFO_HIST
(
    user_id         varchar(10) not null comment '직원 아이디',
    hist_seq        int         not null comment '이력 순번',
    user_nm         varchar(6)  null comment '직원명',
    user_position   varchar(3)  null comment '직급/직위',
    user_birth      varchar(8)  null comment '직원 생년월일',
    user_department varchar(3)  null comment '소속',
    hire_date       varchar(8)  null comment '입사일',
    user_phone      varchar(11) null comment '휴대전화',
    user_address    varchar(50) null comment '주소',
    file_seq        int         null comment '파일 순번',
    created_date    timestamp   not null comment '생성일시',
    creator         varchar(10) not null comment '생성자',
    primary key (user_id, hist_seq)
)
    comment '직원 프로필이력관리 테이블' charset = utf8mb4;

create table TB_USER_EDUCATION_INFO
(
    user_id           varchar(10)   not null comment '직원 아이디',
    school_seq        int           not null comment '학교 순번',
    school_gubun      varchar(3)    null comment '학력 구분',
    school_nm         varchar(20)   null comment '학교명',
    school_start_date varchar(8)    null comment '입학일자',
    school_end_date   varchar(8)    null comment '졸업일자',
    major             varchar(20)   null comment '주전공',
    double_major      varchar(20)   null comment '복수전공',
    total_grade       decimal(2, 1) null comment '졸업학점',
    standard_grade    decimal(2, 1) null comment '기준학점',
    grad_status       varchar(3)    null comment '졸업상태',
    created_date      timestamp     not null comment '생성일시',
    modified_date     timestamp     not null comment '수정일시',
    creator           varchar(10)   not null comment '생성자',
    modifier          varchar(10)   not null comment '수정자',
    primary key (user_id, school_seq)
)
    comment '직원 학력 정보테이블' charset = utf8mb4;

create table TB_USER_EDUCATION_INFO_HIST
(
    user_id           varchar(10)   not null comment '직원 아이디',
    school_seq        int           not null comment '학교 순번',
    hist_seq          int           not null comment '이력 순번',
    school_gubun      varchar(3)    null comment '학력 구분',
    school_nm         varchar(20)   null comment '학교명',
    school_start_date varchar(8)    null comment '입학일자',
    school_end_date   varchar(8)    null comment '졸업일자',
    major             varchar(20)   null comment '주전공',
    double_major      varchar(20)   null comment '복수전공',
    total_grade       decimal(2, 1) null comment '졸업학점',
    standard_grade    decimal(2, 1) null comment '기준학점',
    grad_status       varchar(3)    null comment '졸업상태',
    created_date      timestamp     not null comment '생성일시',
    creator           varchar(10)   not null comment '생성자',
    primary key (user_id, school_seq, hist_seq)
)
    comment '직원 학력 이력관리 테이블' charset = utf8mb4;

create table TB_PROJECT_INFO
(
    user_id            varchar(10)      not null comment '직원 아이디',
    project_seq        int              not null comment '사업 순번',
    project_nm         varchar(100)     not null comment '사업명',
    project_start_date varchar(8)       not null comment '투입시작일',
    project_end_date   varchar(8)       not null comment '투입종료일',
    project_role       varchar(3)       null comment '역할',
    project_client     varchar(20)      not null comment '발주처',
    assigned_task_lar  varchar(3)       not null comment '담당업무(대분류)',
    assigned_task_mid  varchar(3)       not null comment '담당업무(소분류)',
    master_id          varchar(6)       null comment '마스터 아이디',
    use_yn             char default 'Y' not null comment '사용 여부',
    created_date       timestamp        not null comment '생성일시',
    modified_date      timestamp        not null comment '수정일시',
    creator            varchar(10)      not null comment '생성자',
    modifier           varchar(10)      not null comment '수정자',
    primary key (user_id, project_seq)
)
    comment '참여사업 정보테이블' charset = utf8mb4;

-- 수행경력 계산을 위한 인덱스 추가
create index idx_start_end
    on TB_PROJECT_INFO (project_start_date, project_end_date);

create table TB_PROJECT_INFO_HIST
(
    user_id            varchar(10)  not null comment '직원 아이디',
    project_seq        int          not null comment '사업 순번',
    hist_seq           int          not null comment '이력 순번',
    project_nm         varchar(100) null comment '사업명',
    project_start_date varchar(8)   null comment '투입시작일',
    project_end_date   varchar(8)   null comment '투입종료일',
    project_role       varchar(3)   null comment '역할',
    project_client     varchar(20)  null comment '발주처',
    assigned_task_lar  varchar(3)   null comment '담당업무(대분류)',
    assigned_task_mid  varchar(3)   null comment '담당업무(소분류)',
    master_id          varchar(6)   null comment '마스터 아이디',
    created_date       timestamp    not null comment '생성일시',
    creator            varchar(10)  not null comment '생성자',
    primary key (user_id, project_seq, hist_seq)
)
    comment '참여사업 이력관리 테이블' charset = utf8mb4;

create table TB_USER_SKILL_INFO
(
    user_id       varchar(10)      not null comment '직원 아이디',
    project_seq   int              not null comment '사업 순번',
    skill_id      int              not null comment '기술 아이디',
    skill_nm      varchar(20)      not null comment '기술명',
    use_yn        char default 'Y' not null comment '사용여부',
    created_date  timestamp        not null comment '생성일시',
    modified_date timestamp        not null comment '수정일시',
    creator       varchar(10)      not null comment '생성자',
    modifier      varchar(10)      not null comment '수정자',
    primary key (user_id, project_seq, skill_id)
)
    comment '직원 기술 정보테이블' charset = utf8mb4;

create table TB_USER_SKILL_INFO_HIST
(
    user_id      varchar(10) not null comment '직원 아이디',
    project_seq  int         not null comment '사업 순번',
    skill_id     int         not null comment '기술 아이디',
    hist_seq     int         not null comment '이력 순번',
    skill_nm     varchar(20) null comment '기술명',
    created_date timestamp   not null comment '생성일시',
    creator      varchar(10) not null comment '생성자',
    primary key (user_id, project_seq, skill_id, hist_seq)
)
    comment '직원 기술 이력관리 테이블' charset = utf8mb4;

create table TB_USER_QUALIFICATION_INFO
(
    user_id           varchar(10)      not null comment '직원 아이디',
    qualification_seq int              not null comment '자격증 순번',
    qualification_nm  varchar(20)      not null comment '자격증 명',
    issuer            varchar(20)      not null comment '발행기관',
    acquisition_date  varchar(8)       not null comment '취득일자',
    expiry_date       varchar(8)       null comment '만기일자',
    is_expired        char default 'N' null comment '만기여부',
    use_yn            char default 'Y' not null comment '사용여부',
    created_date      timestamp        not null comment '생성일시',
    modified_date     timestamp        not null comment '수정일시',
    creator           varchar(10)      not null comment '생성자',
    modifier          varchar(10)      not null comment '수정자',
    primary key (user_id, qualification_seq)
)
    comment '직원 자격증 정보테이블' charset = utf8mb4;

create table TB_USER_QUALIFICATION_INFO_HIST
(
    user_id           varchar(10)      not null comment '직원 아이디',
    qualification_seq int              not null comment '자격증 순번',
    hist_seq          int              not null comment '이력 순번',
    qualification_nm  varchar(20)      null comment '자격증 명',
    issuer            varchar(20)      null comment '발행기관',
    acquisition_date  varchar(8)       null comment '취득일자',
    expiry_date       varchar(8)       null comment '만기일자',
    is_expired        char default 'N' null comment '만기여부',
    created_date      timestamp        not null comment '생성일시',
    creator           varchar(10)      not null comment '생성자',
    primary key (user_id, qualification_seq, hist_seq)
)
    comment '직원 자격증 이력관리 테이블' charset = utf8mb4;

create table TB_WORK_EXPERIENCE_INFO
(
    user_id            varchar(10)      not null comment '직원 아이디',
    work_seq           int              not null comment '근무지 순번',
    work_place         varchar(20)      not null comment '근무지',
    work_assigned_task varchar(20)      not null comment '담당업무',
    work_position      varchar(10)      not null comment '직급',
    work_start_date    varchar(8)       not null comment '입사일자',
    work_end_date      varchar(8)       not null comment '퇴사일자',
    use_yn             char default 'Y' not null comment '사용여부',
    created_date       timestamp        not null comment '생성일시',
    modified_date      timestamp        not null comment '수정일시',
    creator            varchar(10)      not null comment '생성자',
    modifier           varchar(10)      not null comment '수정자',
    primary key (user_id, work_seq)
)
    comment '직원 근무지 정보테이블' charset = utf8mb4;

create table TB_WORK_EXPERIENCE_INFO_HIST
(
    user_id            varchar(10) not null comment '직원 아이디',
    work_seq           int         not null comment '근무지 순번',
    hist_seq           int         not null comment '이력 순번',
    work_place         varchar(20) null comment '근무지',
    work_assigned_task varchar(20) null comment '담당업무',
    work_position      varchar(10) null comment '직급',
    work_start_date    varchar(8)  null comment '입사일자',
    work_end_date      varchar(8)  null comment '퇴사일자',
    created_date       timestamp   not null comment '생성일시',
    creator            varchar(10) not null comment '생성자',
    primary key (user_id, work_seq, hist_seq)
)
    comment '직원 근무지 이력관리 테이블' charset = utf8mb4;

create table TB_ATTACHMENT_INFO
(
    user_id        varchar(10)      not null comment '직원 아이디',
    file_seq       int              not null comment '파일 순번',
    file_se        varchar(30)      not null comment '파일 구분',
    file_ori_nm    varchar(20)      not null comment '로컬파일명',
    file_sver_nm   varchar(50)      not null comment '서버파일명',
    file_sver_path varchar(100)     not null comment '파일 서버경로',
    file_extension varchar(8)       not null comment '파일확장자',
    use_yn         char default 'Y' not null comment '사용여부',
    created_date   timestamp        not null comment '생성일시',
    creator        varchar(10)      not null comment '생성자',
    primary key (file_seq, file_se, user_id)
)
    comment '첨부파일 정보테이블' charset = utf8mb4;

create table TB_COMMON_CODE
(
    code_group_id varchar(5)       not null comment '코드그룹 아이디',
    code_id       varchar(3)       not null comment '코드상세 아이디',
    code_group_nm varchar(10)      not null comment '코드그룹 명',
    code_value    varchar(50)      not null comment '코드 값',
    parent_id     varchar(3)       null comment '상위코드 아이디',
    level         int              not null comment '계층 레벨',
    use_yn        char default 'Y' not null comment '사용여부',
    created_date  timestamp        not null comment '생성일시',
    creator       varchar(10)      not null comment '생성자',
    primary key (code_group_id, code_id)
)
    comment '공통코드 테이블' charset = utf8mb4;

-- parent_id 컬럼에 인덱스 추가
create index idx_tb_common_code_parent_id
    on TB_COMMON_CODE (parent_id);

create table TB_USER_REFRESH_TOKEN_INFO
(
    user_id      varchar(10)  not null comment '직원 아이디',
    token        varchar(256) not null comment 'REFRESH TOKEN',
    created_date timestamp    not null comment '생성일시'
)
    comment 'TOKEN 테이블' charset = utf8mb4;

create table TB_PROJECT_MMT
(
    master_id          varchar(6)       not null comment '마스터 아이디'
        primary key,
    project_nm         varchar(100)     not null comment '사업명',
    project_start_date varchar(8)       not null comment '투입시작일',
    project_end_date   varchar(8)       not null comment '투입종료일',
    project_client     varchar(20)      not null comment '발주처',
    use_yn             char default 'Y' not null comment '사용 여부',
    created_date       timestamp        not null comment '생성일시',
    modified_date      timestamp        not null comment '수정일시',
    creator            varchar(10)      not null comment '생성자',
    modifier           varchar(10)      not null comment '수정자'
)
    comment '참여사업 관리 테이블' charset = utf8mb4;

-- 수행경력 계산 함수
DELIMITER $$

CREATE FUNCTION calculate_total_months_v2(input_user_id VARCHAR(8))
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE done INT DEFAULT 0; -- 반복 종료 플래그
    DECLARE start_date DATE;
    DECLARE end_date DATE;
    DECLARE total_months INT;

    -- 병합된 결과를 저장할 임시 테이블 생성
    CREATE TEMPORARY TABLE temp_merged_periods
    (
        project_start_date DATE,
        project_end_date   DATE
    );

    -- 처리할 데이터를 저장할 임시 테이블 생성
    CREATE TEMPORARY TABLE temp_unmerged_periods AS
SELECT project_start_date, project_end_date
FROM TB_PROJECT_INFO
WHERE user_id = input_user_id
  AND use_yn = 'Y'
ORDER BY project_start_date;

-- 초기 시작일과 종료일 설정
SET start_date = (SELECT project_start_date FROM temp_unmerged_periods ORDER BY project_start_date LIMIT 1);
    SET end_date = (SELECT project_end_date FROM temp_unmerged_periods ORDER BY project_start_date LIMIT 1);

    -- 병합 반복문
    REPEAT
IF NOT done THEN
            -- 병합된 기간 삽입
            INSERT INTO temp_merged_periods
SELECT project_start_date, project_end_date
FROM (SELECT LEAST(start_date, t1.project_start_date) AS project_start_date,
             GREATEST(end_date, t1.project_end_date)  AS project_end_date
      FROM temp_unmerged_periods t1
      WHERE end_date >= t1.project_start_date
        AND start_date <= t1.project_end_date) a
ORDER BY project_end_date DESC
    LIMIT 1;

-- 병합된 데이터 삭제
DELETE
FROM temp_unmerged_periods
WHERE end_date >= project_start_date
  AND start_date <= project_end_date;

-- 다음 병합 대상 가져오기
IF EXISTS (SELECT 1 FROM temp_unmerged_periods) THEN
SELECT project_start_date, project_end_date
INTO start_date, end_date
FROM temp_unmerged_periods
ORDER BY project_start_date
    LIMIT 1;
ELSE
                SET done = 1;
END IF;
END IF;
    UNTIL done END REPEAT;

    -- 총 개월 수 계산
SELECT FLOOR(SUM(DATEDIFF(CASE WHEN project_end_date > NOW() THEN NOW() ELSE project_end_date END, project_start_date) + 1) / 30)
INTO total_months
FROM temp_merged_periods;

-- 임시 테이블 삭제
DROP TEMPORARY TABLE temp_merged_periods;
    DROP TEMPORARY TABLE temp_unmerged_periods;

RETURN total_months;
END $$

DELIMITER ;

-- 최초 적재 데이터
-- 초기 데이터
-- TB_USER_PROFILE_INFO
insert into TB_USER_PROFILE_INFO ( user_id
                                 , user_pw
                                 , user_nm
                                 , user_position
                                 , user_birth
                                 , user_department
                                 , hire_date
                                 , user_phone
                                 , user_address
                                 , user_role
                                 , created_date
                                 , modified_date
                                 , creator
                                 , modifier)
VALUES ( 'admin'
       , '$2a$10$XMQKsECASRc6gBJbLEw3ZOnBWF92BJSNUMLNcBQdYtzD2Q9gLeq/C'
       , '관리자'
       , '004'
       , '20201212'
       , '012'
       , '20240401'
       , '01011111111'
       , '대구광역시 남구 우리집303호'
       , '0'
       , now()
       , now()
       , 'admin'
       , 'admin');


-- TB_USER_PROFILE_INFO_HIST
INSERT INTO TB_USER_PROFILE_INFO_HIST(USER_ID, hist_seq, USER_NM, USER_POSITION, USER_BIRTH, user_department, hire_date,
                                      user_phone, user_address, created_date, creator)
SELECT USER_ID,
       0 AS HIST_SEQ,
       user_nm,
       user_position,
       user_birth,
       user_department,
       hire_date,
       user_phone,
       user_address,
       created_date,
       creator
FROM TB_USER_PROFILE_INFO
WHERE USER_ID = 'admin';

-- TB_COMMON_CODE
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '001', '직위/직급', '수습', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '002', '직위/직급', '인턴', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '003', '직위/직급', '사원', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '004', '직위/직급', '주임', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '005', '직위/직급', '대리', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '006', '직위/직급', '과장', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '007', '직위/직급', '차장', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '008', '직위/직급', '부장', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '009', '직위/직급', '이사', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '010', '직위/직급', '상무이사', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '011', '직위/직급', '전무이사', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '012', '직위/직급', '부사장', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('PSIT', '013', '직위/직급', '대표이사', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '001', '담당업무', 'IT컨설팅', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '002', '담당업무', 'IT프로젝트관리', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '003', '담당업무', 'IT아키텍처', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '004', '담당업무', 'SW개발', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '005', '담당업무', '시스템 구축 및 운영', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '006', '담당업무', 'IT마케팅', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '007', '담당업무', 'IT품질관리', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '008', '담당업무', '정보보호', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '009', '담당업무', 'IT기술교육', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '010', '담당업무', '정보기술기획', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '011', '담당업무', '정보기술컨설팅', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '012', '담당업무', '정보보호컨설팅', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '013', '담당업무', '데이터분석', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '014', '담당업무', '업무분석', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '015', '담당업무', '빅데이터기획', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '016', '담당업무', 'UI/UX기획', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '017', '담당업무', '인공지능서비스기획', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '018', '담당업무', 'IT프로젝트관리', '002', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '019', '담당업무', 'IT프로젝트사업관리', '002', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '020', '담당업무', 'SW아키텍처', '003', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '021', '담당업무', 'infrastructure아키텍처', '003', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '022', '담당업무', '데이터아키텍처', '003', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '023', '담당업무', '빅데이터아키텍처', '003', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '024', '담당업무', '인공지능아키텍처', '003', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '025', '담당업무', 'UI/UX개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '026', '담당업무', 'UI/UX디자인', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '027', '담당업무', '응용SW개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '028', '담당업무', '빅데이터개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '029', '담당업무', '시스템SW개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '030', '담당업무', '임베디드SW개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '031', '담당업무', '인공지능SW개발', '004', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '032', '담당업무', '데이터베이스관리', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '033', '담당업무', 'NW엔지니어링', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '034', '담당업무', 'IT시스템관리', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '035', '담당업무', 'IT시스템기술지원', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '036', '담당업무', '빅데이터엔지니어링', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '037', '담당업무', '인공지능서비스관리', '005', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '038', '담당업무', 'SW제품기획', '006', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '039', '담당업무', 'IT기술영업', '006', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '040', '담당업무', 'IT서비스기획', '006', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '041', '담당업무', 'IT품질관리', '007', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '042', '담당업무', 'IT테스트', '007', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '043', '담당업무', 'IT감리', '007', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '044', '담당업무', 'IT감사', '007', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '045', '담당업무', '정보보호관리', '008', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '046', '담당업무', '보안사고대응', '008', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('TASK', '047', '담당업무', 'IT기술교육', '009', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '001', '소속', 'CEO', NULL, 1, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '002', '소속', '경영지원팀', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '003', '소속', '기업부설연구소', '001', 2, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '004', '소속', 'SI사업본부', '003', 3, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '005', '소속', 'TS사업본부', '003', 3, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '006', '소속', 'ITO사업부', '004', 4, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '007', '소속', 'ITS사업부', '004', 4, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '008', '소속', '영업2팀', '003', 3, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '009', '소속', '클라우드사업', '005', 4, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '010', '소속', 'TS사업', '005', 4, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '011', '소속', '영업1팀', '006', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '012', '소속', '금융사업3팀', '006', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '013', '소속', '영남지사', '006', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '014', '소속', '금융사업2팀', '007', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '015', '소속', '금융사업1팀', '007', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '016', '소속', '솔루션사업', '007', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '017', '소속', 'DW사업', '007', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ORG', '018', '소속', '컨설팅사업', '007', 5, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('STUT', '001', '구분', '졸업', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('STUT', '002', '구분', '졸업예정', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('STUT', '003', '구분', '재학중', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('SCHL', '010', '구분', '고등학교', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('SCHL', '011', '구분', '대학교(2,3년)', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('SCHL', '012', '구분', '대학교(4년)', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('SCHL', '013', '구분', '대학원', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '001', '사업역할', 'PM', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '002', '사업역할', 'PMO', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '003', '사업역할', 'PL', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '004', '사업역할', 'PE', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '005', '사업역할', 'PA', NULL, 0, SYSDATE(), 'admin');
INSERT INTO TB_COMMON_CODE (code_group_id, code_id, code_group_nm, code_value, parent_id, level, created_date, creator)
VALUES ('ROLE', '006', '사업역할', 'QA', NULL, 0, SYSDATE(), 'admin');


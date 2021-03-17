create database if not exists curriculum_vitaes;
use curriculum_vitaes;
create table if not exists T_GENDERS(
            GENDER_ID varchar(255) not null,
            F_GENDER_NAME varchar(10) not null unique,
            primary key(GENDER_ID)
            )engine=InnoDB charset=utf8;

            create table if not exists T_JOB_CANDIDATES(
            JOB_CANDIDATE_ID varchar(255) not null,
            F_FIRST_NAME varchar(50) not null,
            F_LAST_NAME varchar(50) not null,
            F_PATRONYMIC varchar(50) not null,
            F_DATE_OF_BIRTH date not null,
            GENDER_ID varchar(255) not null,
            primary key(JOB_CANDIDATE_ID),
            constraint FK_GENDER_IN_JOB_CANDIDATES foreign key (GENDER_ID)
            references T_GENDERS(GENDER_ID)
            )engine=InnoDB charset=utf8;

            create table if not exists T_CONTACTS(
            CONTACT_ID varchar(255) not null,
            F_CONTACT_NAME varchar(50) not null,
            JOB_CANDIDATE_ID varchar(255) not null,
            primary key(CONTACT_ID),
            constraint FK_JOB_CANDIDATE_IN_CONTACTS foreign key (JOB_CANDIDATE_ID)
            references T_JOB_CANDIDATES(JOB_CANDIDATE_ID)
            )engine=InnoDB charset=utf8;

            create table if not exists T_TECHNOLOGIES(
            TECHNOLOGY_ID varchar(255) not null,
            F_TECHNOLOGY_NAME varchar(50) not null,
            primary key(TECHNOLOGY_ID)
            )engine=InnoDB charset=utf8;

            create table if not exists T_JOB_CANDIDATES_TECHNOLOGIES(
            JOB_CANDIDATE_ID varchar(255) not null,
            TECHNOLOGY_ID varchar(255) not null,
            primary key(JOB_CANDIDATE_ID, TECHNOLOGY_ID),
            constraint FK_JOB_CANDIDATE_IN_TECHNOLOGY_AND_JOB_CANDIDATES foreign key (JOB_CANDIDATE_ID)
            references T_JOB_CANDIDATES(JOB_CANDIDATE_ID),
            constraint FK_TECHNOLOGY_IN_TECHNOLOGY_AND_JOB_CANDIDATES foreign key (TECHNOLOGY_ID)
            references T_TECHNOLOGIES(TECHNOLOGY_ID)
            )engine=InnoDB charset=utf8;
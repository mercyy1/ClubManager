-- MPClub database setup (MySQL 8)
-- Run with:  mysql -u root -p < schema.sql

CREATE DATABASE IF NOT EXISTS memberdb;
USE memberdb;

CREATE TABLE IF NOT EXISTS member (
    EmailAddress VARCHAR(100) NOT NULL PRIMARY KEY,
    FullName     VARCHAR(100) NOT NULL,
    PhoneNumber  VARCHAR(20),
    ProgramName  VARCHAR(100),
    YearLevel    INT
);

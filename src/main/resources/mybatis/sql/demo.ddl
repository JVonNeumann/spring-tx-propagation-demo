CREATE DATABASE IF NOT EXISTS springtxpropagation DEFAULT CHARSET utf8;

USE springtxpropagation;

DROP table IF EXISTS tmp_employee;
CREATE TABLE tmp_employee (
  id int(10) NOT NULL AUTO_INCREMENT,
  dept_id int(10) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP table IF EXISTS tmp_department;
CREATE TABLE tmp_department (
  id int(10) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
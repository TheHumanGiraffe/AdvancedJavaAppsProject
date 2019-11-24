create database casino;
use casino;
create user 'casinomanager' identified by 'password';
CREATE TABLE player(name VARCHAR(255) NOT NULL, password text NOT NULL, chips int, wins int, losses int, PRIMARY KEY(name));
GRANT ALL PRIVILEGES ON *.* TO 'casinomanager';
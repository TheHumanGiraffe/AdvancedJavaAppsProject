create database casino;
use casino;
create user 'casinomanager' identified by 'password';
use casino;CREATE TABLE game (id int, gameName text, PRIMARY KEY(id));
use casino;CREATE TABLE gameHistory(id int, players json, winner int, game int REFERENCES game(id), PRIMARY KEY(id));
use casino;CREATE TABLE player(id int, name text, currentGame int REFERENCES game(id), chips int, PRIMARY KEY(id));
GRANT ALL PRIVILEGES ON *.* TO 'casinomanager';
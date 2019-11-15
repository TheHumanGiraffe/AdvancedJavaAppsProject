Database setup 

```sudo -i -u postgres
create user casinoManager with password 'password';
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO casinoManager;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO casinoManager;
CREATE DATABASE casino;
\c casino;
CREATE TABLE game (id int, gameName text, PRIMARY KEY(id));
CREATE TABLE gameHistory(id int, players json, winner int, game int REFERENCES game(id), PRIMARY KEY(id));
CREATE TABLE player(id int, name text, currentGame int REFERENCES game(id), chips int, PRIMARY KEY(id));
alter role casinoManager witH superuser;
```

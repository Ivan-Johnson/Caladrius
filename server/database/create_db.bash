#!/bin/bash
if [ "http" != $(whoami) ]; then
	echo "You must run this script as http"
	exit 1
fi

DB_FILE="/srv/caladrius.db"

sqlite3 "$DB_FILE" 'CREATE TABLE feeds       ( userid VARCHAR(39), feedid VARCHAR(39), feedbase64 VARCHAR(100000), lastFetch BIGINT, lastModify BIGINT,  PRIMARY KEY (userid, feedid));'
#sqlite3 "$DB_FILE" "INSERT INTO  feeds values('thisisauserid1',   '1001',             'lololol5',                0,                0);"
#sqlite3 "$DB_FILE" "INSERT INTO  feeds values('thisisauserid1',   '1002',             'lololol6',                0,                0);"
#sqlite3 "$DB_FILE" "INSERT INTO  feeds values('thisisauserid2',   '1003',             'lololol7',                0,                0);"
#sqlite3 "$DB_FILE" "INSERT INTO  feeds values('thisisauserid2',   '1004',             'lololol8',                0,                0);"

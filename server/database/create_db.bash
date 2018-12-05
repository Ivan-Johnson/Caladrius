#!/bin/bash
if [ "http" != $(whoami) ]; then
	echo "You must run this script as http"
	exit 1
fi

DB_FILE="/srv/caladrius.db"

trash "$DB_FILE"

###############
#CREATE TABLES#
###############
sqlite3 "$DB_FILE" 'CREATE TABLE feeds       ( userid VARCHAR(39), feedid VARCHAR(39), feedbase64 VARCHAR(100000), lastFetch BIGINT, lastModify BIGINT,  PRIMARY KEY (feedid));'
sqlite3 "$DB_FILE" 'CREATE TABLE users       ( userid VARCHAR(39), userBase64 VARCHAR(100000));'


###################
#TEST DATA (FEEDS)#
###################
sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_0',   '11573',             'DEADBEEF',                0,                0);"
sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_0',   '12484',             'DEADBEEF',                0,                0);"
sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_0',   '18693',             'DEADBEEF',                0,                0);"
sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_0',   '4144',              'DEADBEEF',                0,                0);"
sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_0',   '26184',             'DEADBEEF',                0,                0);"

# TEST_USER_1 has no feeds

sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_2',   '6427',              'DEADBEEF',                0,                0);"


sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_3',   '27560',              '1XDSpDji2jcqJoQgfQpA8zC6rnK7uJ31OPObAccMXKEo6CLuVSYTUcNer85r9Vvo3Z1PE6EoxPgjUODEsWqSSMUZ2tfUnbrPyWMJkc43ZsfNNS4RMgz4tYXbeQ7cccDS',                0,                0);"

sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_4',   '26814',              '',                0,                0);"


sqlite3 "$DB_FILE" "INSERT INTO  feeds values('TEST_USER_5',   '10194',              '',                0,                0);"

# TEST_USER_6 is empty for the purpose of identity testing; i.e. set then immediately get

###################
#TEST DATA (USERS)#
###################
sqlite3 "$DB_FILE" "INSERT INTO users values('TEST_USER_0', '3208');"

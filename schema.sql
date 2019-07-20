CREATE TABLE log_line (ID INTEGER NOT NULL, DATE DATETIME, IP VARCHAR(255), REQUEST VARCHAR(255), STATUS INTEGER, USERAGENT VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE blocked_ip (ID INTEGER NOT NULL, COMMENT LONGTEXT, DATE DATETIME, IP VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)

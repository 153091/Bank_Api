CREATE TABLE USER
(ID IDENTITY NOT NULL PRIMARY KEY,
SURNAME VARCHAR(255),
NAME VARCHAR(255),
AGE INTEGER);

CREATE TABLE ACCOUNT
(ID IDENTITY NOT NULL PRIMARY KEY,
ACCOUNT_NUMBER INTEGER,
USER_ID INTEGER unique); --unique?????
ALTER TABLE ACCOUNT ADD FOREIGN KEY (USER_ID) references USER(ID);

CREATE TABLE CARD
(ID IDENTITY NOT NULL PRIMARY KEY,
CARD_NUBMER INTEGER,
CARD_BAlANCE INTEGER,
ACCOUNT_ID INTEGER); --unique??????
ALTER TABLE CARD ADD FOREIGN KEY (ACCOUNT_ID) references ACCOUNT(ID);


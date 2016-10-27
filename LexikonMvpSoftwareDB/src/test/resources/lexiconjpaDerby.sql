CREATE TABLE LANGUAGES (
LANGUAGES_ID INT NOT NULL,
LANGUAGES_NAME VARCHAR(255),
PRIMARY KEY (LANGUAGES_ID)
);

CREATE TABLE SPECIALTY (
TERM_ID INT NOT NULL,
PRIMARY KEY (TERM_ID)
);

CREATE TABLE TECHNICALTERM (
TERM_ID INT NOT NULL,
SPECIALTY_TERM_ID INT,
PRIMARY KEY (TERM_ID)
);

CREATE TABLE TRANSLATIONS (
TRANSLATIONS_ID INT NOT NULL,
TRANSLATIONS_DESCRIPTION VARCHAR(255),
TRANSLATIONS_NAME VARCHAR(255),
TERM_TERM_ID INT,
LANGUAGES_LANGUAGES_ID INT
);

CREATE TABLE SEQUENCE (
SEQ_NAME VARCHAR(50) NOT NULL,
SEQ_COUNT INT,
PRIMARY KEY (SEQ_NAME)
);

ALTER TABLE TECHNICALTERM ADD CONSTRAINT FK_TECHNICALTERM_SPECIALTY_TERM_ID FOREIGN KEY (SPECIALTY_TERM_ID) REFERENCES SPECIALTY (TERM_ID);

ALTER TABLE TRANSLATIONS ADD CONSTRAINT FK_TRANSLATIONS_LANGUAGES_LANGUAGES_ID FOREIGN KEY (LANGUAGES_LANGUAGES_ID) REFERENCES LANGUAGES (LANGUAGES_ID);

SELECT * FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';

INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);
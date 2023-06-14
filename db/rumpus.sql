DROP DATABASE IF EXISTS rumpus;
CREATE DATABASE rumpus;

USE rumpus;

CREATE TABLE auth (
	auth_id INT AUTO_INCREMENT,
    CONSTRAINT pk_auth
		PRIMARY KEY (auth_id),
	
    id INT,
    authLevel VARCHAR(45)
);

-- TODO: look into making id primary key. get rid of username in 'user' table and use it as a meta table.
CREATE TABLE users (
	username VARCHAR(50) NOT NULL,
		PRIMARY KEY(username),
	password VARCHAR(68) NOT NULL,
	enabled TINYINT(1) NOT NULL
);

CREATE TABLE user (
	-- user_id INT AUTO_INCREMENT,
    id VARCHAR(10) NOT NULL UNIQUE,
    -- CONSTRAINT pk_user
-- 		PRIMARY KEY (user_id),
	
    -- id INT, -- Maybe change to not null later
    -- username VARCHAR(45),
    -- CONSTRAINT rumpus_user UNIQUE (username),
    email VARCHAR(45),
    -- password VARCHAR(68) NOT NULL,
    -- enabled TINYINT(1), 
    -- enabled TINYINT(1) NOT NULL,
    
    username VARCHAR(50) NOT NULL,
	CONSTRAINT fk_username_user
		FOREIGN KEY (username) REFERENCES users(username)
        ON DELETE CASCADE ON UPDATE CASCADE,
    auth_id INT
    -- CONSTRAINT fk_auth
--     	FOREIGN KEY (auth_id)
--     	REFERENCES auth(auth_id)
);

CREATE TABLE authorities (
	username VARCHAR(50) NOT NULL,
    authority VARCHAR(68) NOT NULL,
    CONSTRAINT fk_username_authorities
		FOREIGN KEY (username) REFERENCES users(username)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO users (USERNAME, PASSWORD, ENABLED) VALUES('chuckthemole','$2a$12$nASTBHmfkGpzV/yXV3dxpO2vPgxKnm0HHHjB7Ld9z1a/OxWHSTA0y',1);
INSERT INTO users (USERNAME, PASSWORD, ENABLED) VALUES('chuck','$2a$12$nASTBHmfkGpzV/yXV3dxpO2vPgxKnm0HHHjB7Ld9z1a/OxWHSTA0y',1);
INSERT INTO user (username, email, id, auth_id) values('chuckthemole', 'chuckthemole@gmail.com', '1111111111', -1);
INSERT INTO user (username, email, id, auth_id) values('chuck', 'chuck@gmail.com', '2222222222', -1);

INSERT INTO AUTHORITIES VALUES('chuck','ROLE_EMPLOYEE');
INSERT INTO AUTHORITIES VALUES('chuck','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('chuckthemole','ROLE_MANAGER');
INSERT INTO AUTHORITIES VALUES('chuckthemole','ROLE_ADMIN');
INSERT INTO AUTHORITIES VALUES('chuckthemole','ROLE_USER');

-- CREATE TABLE session (
-- 	session_id INT AUTO_INCREMENT,
--     CONSTRAINT pk_user
-- 		PRIMARY KEY (session_id),
-- 	
--     id VARCHAR(45), -- Maybe change to not null later
--     last_accessed_time DATETIME(6),
--     max_inactive_interval INT,
--     expired BOOLEAN,
--     
--     user_id INT,
--     CONSTRAINT user_id
--     	FOREIGN KEY (user_id)
--     	REFERENCES user(user_id)
-- );

CREATE TABLE SPRING_SESSION (
PRIMARY_ID CHAR(36) NOT NULL,
SESSION_ID CHAR(36) NOT NULL,
CREATION_TIME BIGINT NOT NULL,
LAST_ACCESS_TIME BIGINT NOT NULL,
MAX_INACTIVE_INTERVAL INT NOT NULL,
EXPIRY_TIME BIGINT NOT NULL,
PRINCIPAL_NAME VARCHAR(100),
CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
SESSION_PRIMARY_ID CHAR(36) NOT NULL,
ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
ATTRIBUTE_BYTES BLOB NOT NULL,
CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, 
ATTRIBUTE_NAME),
CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) 
REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

DELIMITER //
CREATE PROCEDURE get_user_by_id(IN userId VARCHAR(10))
BEGIN
	SELECT * FROM user WHERE id=userId;
END //
DELIMITER ;


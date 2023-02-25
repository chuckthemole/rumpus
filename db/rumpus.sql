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

CREATE TABLE user (
	user_id INT AUTO_INCREMENT,
    CONSTRAINT pk_user
		PRIMARY KEY (user_id),
	
    id INT, -- Maybe change to not null later
    name VARCHAR(45),
    CONSTRAINT rumpus_user UNIQUE (name),
    email VARCHAR(45),
    pass VARCHAR(45),
    
    auth_id INT,
    CONSTRAINT fk_auth
    	FOREIGN KEY (auth_id)
    	REFERENCES auth(auth_id)
);

CREATE TABLE users (
	username VARCHAR(50) NOT NULL,
		PRIMARY KEY(username),
	password VARCHAR(68) NOT NULL,
	enabled TINYINT(1) NOT NULL
);
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES('chuckthemole','$2a$12$nASTBHmfkGpzV/yXV3dxpO2vPgxKnm0HHHjB7Ld9z1a/OxWHSTA0y',1);
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES('chuck','$2a$12$nASTBHmfkGpzV/yXV3dxpO2vPgxKnm0HHHjB7Ld9z1a/OxWHSTA0y',1);

CREATE TABLE authorities (
	username VARCHAR(50) NOT NULL,
    authority VARCHAR(68) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);
INSERT INTO AUTHORITIES VALUES('chuck','ROLE_EMPLOYEE');
INSERT INTO AUTHORITIES VALUES('chuck','ROLE_USER');
INSERT INTO AUTHORITIES VALUES('chuckthemole','ROLE_MANAGER');
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
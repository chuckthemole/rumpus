DROP DATABASE IF EXISTS rumpus;
CREATE DATABASE rumpus;

USE rumpus;

CREATE TABLE auth (
	auth_id INT AUTO_INCREMENT,
    CONSTRAINT pk_auth
		PRIMARY KEY (auth_id),
	
    id INT NOT NULL,
    authLevel VARCHAR(45)
);

CREATE TABLE user (
	user_id INT AUTO_INCREMENT,
    CONSTRAINT pk_user
		PRIMARY KEY (user_id),
	
    id INT NOT NULL,
    name VARCHAR(45),
    
    auth_id INT,
    CONSTRAINT fk_auth
    	FOREIGN KEY (auth_id)
    	REFERENCES auth(auth_id)
);
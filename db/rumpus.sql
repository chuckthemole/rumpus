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
-- Generated at Wed Mar 25 17:27:23 UTC 2015

-- tables
-- Table babysitting_requests
DROP TABLE if exists babysitting_requests;
-- Table devices
DROP TABLE if exists devices;
-- Table friend_requests
DROP TABLE if exists friend_requests;
-- Table friends
DROP TABLE if exists friends;
-- Table messages
DROP TABLE if exists messages;
-- Table points
DROP TABLE if exists points;
-- Table schedules
DROP TABLE if exists schedules;
-- Table users
DROP TABLE if exists users;


-- Created by Vertabelo (http://vertabelo.com)
-- Script type: create
-- Scope: [tables, references, sequences, views, procedures]
-- Generated at Wed Mar 25 17:40:29 UTC 2015




-- tables
-- Table babysitting_requests
CREATE TABLE babysitting_requests (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    user_id int    NOT NULL ,
    babysitter_id int    NOT NULL ,
    job_date timestamp    NOT NULL ,
    status enum('pending','accepted','rejected')    NOT NULL DEFAULT 'pending' ,
    CONSTRAINT babysitting_requests_pk PRIMARY KEY (id)
);

-- Table devices
CREATE TABLE devices (
    id int    NOT NULL ,
    created_at datetime    NOT NULL ,
    last_synced_at timestamp    NOT NULL ,
    user_id int    NOT NULL 
);

-- Table friend_requests
CREATE TABLE friend_requests (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    requestor_id int    NOT NULL ,
    requestor_first_name varchar(255)    NOT NULL ,
    requestor_last_name varchar(255)    NOT NULL ,
    requestee_id int    NULL ,
    requestee_email_address varchar(255)    NULL ,
    status enum('pending','accepted','rejected')    NOT NULL DEFAULT 'pending' ,
    CONSTRAINT friend_requests_pk PRIMARY KEY (id)
);

-- Table friends
CREATE TABLE friends (
    id int    NOT NULL ,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    user_id int    NOT NULL ,
    friend_id int    NOT NULL ,
    friend_first_name varchar(255)    NOT NULL ,
    friend_last_name varchar(255)    NOT NULL ,
    CONSTRAINT friends_pk PRIMARY KEY (id)
);

-- Table messages
CREATE TABLE messages (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    sender_id int    NOT NULL ,
    recipient_id int    NOT NULL ,
    message text    NOT NULL ,
    type enum('outgoing','incoming')    NOT NULL ,
    status enum('unread','read')    NOT NULL DEFAULT 'unread' ,
    CONSTRAINT messages_pk PRIMARY KEY (id)
);

-- Table points
CREATE TABLE points (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    points int    NOT NULL ,
    user_id int    NOT NULL ,
    schedule_id int    NOT NULL ,
    CONSTRAINT points_pk PRIMARY KEY (id)
);

-- Table schedules
CREATE TABLE schedules (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    user_id int    NOT NULL ,
    friend_id int    NOT NULL ,
    job_date date    NOT NULL ,
    start_time time    NOT NULL ,
    end_time time    NOT NULL ,
    points_assigned bool    NOT NULL ,
    CONSTRAINT schedules_pk PRIMARY KEY (id)
);

-- Table users
CREATE TABLE users (
    id int    NOT NULL  AUTO_INCREMENT,
    created_at datetime    NOT NULL ,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
    first_name varchar(255)    NOT NULL ,
    email_address varchar(255)    NOT NULL ,
    last_name varchar(255)    NOT NULL ,
    CONSTRAINT users_pk PRIMARY KEY (id)
);






-- End of file.



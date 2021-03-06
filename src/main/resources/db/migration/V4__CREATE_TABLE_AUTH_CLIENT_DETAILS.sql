CREATE TABLE OAUTH_CLIENT_DETAILS (
  CLIENT_ID VARCHAR(256) PRIMARY KEY,
  RESOURCE_IDS VARCHAR(256),
  CLIENT_SECRET VARCHAR(256),
  SCOPE VARCHAR(256),
  AUTHORIZED_GRANT_TYPES VARCHAR(256),
  WEB_SERVER_REDIRECT_URI VARCHAR(256),
  AUTHORITIES VARCHAR(256),
  ACCESS_TOKEN_VALIDITY INTEGER,
  REFRESH_TOKEN_VALIDITY INTEGER,
  ADDITIONAL_INFORMATION VARCHAR(4096),
  AUTOAPPROVE VARCHAR(256)
);

CREATE TABLE OAUTH_CLIENT_TOKEN (
  AUTHENTICATION_ID VARCHAR(256) PRIMARY KEY,
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  USER_NAME VARCHAR(256),
  CLIENT_ID VARCHAR(256)
);

CREATE TABLE OAUTH_ACCESS_TOKEN (
  AUTHENTICATION_ID VARCHAR(256) PRIMARY KEY,
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  USER_NAME VARCHAR(256),
  CLIENT_ID VARCHAR(256),
  AUTHENTICATION BYTEA,
  REFRESH_TOKEN VARCHAR(256)
);

CREATE TABLE OAUTH_REFRESH_TOKEN (
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  AUTHENTICATION BYTEA
);

CREATE TABLE OAUTH_APPROVALS (
	USERID VARCHAR(256),
	CLIENTID VARCHAR(256),
	SCOPE VARCHAR(256),
	STATUS VARCHAR(10),
	EXPIRESAT TIMESTAMP,
	LASTMODIFIEDAT TIMESTAMP
);
ALTER table user_auth CHANGE COLUMN token access_token varchar(150);
ALTER table user_auth ADD refresh_token varchar(150);
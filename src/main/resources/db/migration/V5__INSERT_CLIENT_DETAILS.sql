INSERT INTO oauth_client_details (client_id,
								  client_secret,
								  scope,
								  authorized_grant_types,
								  resource_ids,
								  authorities,
								  access_token_validity,
								  refresh_token_validity,
								  web_server_redirect_uri,
								  additional_information,
								  autoapprove ) 
VALUES ('client',
		'$2a$10$z/e8u6oQpfMnjpo5QUmJVO4DKyu2WaBPGMF4lIfFG7kst2r4hQCcO',
		'read,write',
		'password,client_credentials,refresh_token',
		'AUTH',
		'read,write',
		3600,
		-1,
		'',
		NULL,
		'false');
package br.com.timesheetio.auth.configuration.authorization;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.timesheetio.auth.service.PersonAuthService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PersonAuthService personAuthService;	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.jdbc(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		DefaultOAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(Boolean.TRUE);
		
		endpoints.authenticationManager(authenticationManager)
				 .requestFactory(requestFactory)
				 .tokenStore(getJwtTokenStore())
				 .accessTokenConverter(getJwtAccessTokenConverter())
				 .userDetailsService(personAuthService);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
        		   .checkTokenAccess("permitAll()")
        		   .allowFormAuthenticationForClients();
	}
	
	@Bean
	public JwtAccessTokenConverter getJwtAccessTokenConverter() {
		
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("person-auth");
		
		return accessTokenConverter;
	}
	
	@Bean
	@Primary
	public JwtTokenStore getJwtTokenStore() {
		
		return new JwtTokenStore(getJwtAccessTokenConverter());
	}
	
	@Bean
	@Primary
	public DefaultTokenServices getTokenServices() {
		
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(getJwtTokenStore());
		defaultTokenServices.setSupportRefreshToken(Boolean.TRUE);
		
		return defaultTokenServices;
	}
}

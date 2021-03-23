package br.com.timesheetio.auth.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${host.link.authorization}")
	private String linkAuthorization;
	
	public static final String TAG_AUTH_OPERATION = "Auth management";
	
    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.timesheetio.auth"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag(TAG_AUTH_OPERATION, "Set of operations used to manage the credentials of sheet."))
                .securitySchemes(Collections.singletonList(getSecuritySchemes()))
                .securityContexts(Arrays.asList(getSecurityContext()));
    }    
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Timesheet.io Auth")
            .description("Responsible for managing credentials on timesheet.io")
            .license("MIT")
            .licenseUrl("http://opensource.org/licenses/MIT")
            .termsOfServiceUrl("http://www.youtube.com")
            .version("1.0.0")
            .contact(new Contact("Diego de J Cordeiro","http://localhost:8083/support", "planosdiego@gmail.com"))
            .build();
    }
    
    private OAuth getSecuritySchemes() {
    	
    	List<GrantType> grantTypes = new ArrayList<>();
    	grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(linkAuthorization));
    	
    	List<AuthorizationScope> authorizationScopes = new ArrayList<>();
    	authorizationScopes.add(new AuthorizationScope("read", "Somente consulta liberada."));
    	authorizationScopes.add(new AuthorizationScope("write", "Somente escrita liberada."));
    	
    	return new OAuth("Authorization Scope", authorizationScopes, grantTypes);
    }
    
    private SecurityContext getSecurityContext(){
    	
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope [] {
    														new AuthorizationScope("read", "Somente consulta liberada."),
    														new AuthorizationScope("write", "Somente escrita liberada.")};
    	
    	List<SecurityReference> securityReferences = new ArrayList<>();
    	securityReferences.add(new SecurityReference("Authorization Scope", authorizationScopes));
    	
    	return SecurityContext.builder()
    			.securityReferences(securityReferences)
    			.build();
    }
}

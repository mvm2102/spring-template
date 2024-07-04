package com.example.demo2.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPISecurityConfig {

    @Value("${keycloak.auth-server-url}")
    String authServerUrl;
    @Value("${keycloak.realm}")
    String realm;

    private static final String OAUTH_SCHEME_NAME = "spring_oauth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme())).security(Arrays.asList(
                new SecurityRequirement().addList("spring_oauth")));
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        return new OAuthFlows().clientCredentials(
                new OAuthFlow()
                        .tokenUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
        );
    }


}

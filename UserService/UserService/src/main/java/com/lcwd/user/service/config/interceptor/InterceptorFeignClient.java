package com.lcwd.user.service.config.interceptor;

import feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class InterceptorFeignClient implements RequestInterceptor{
    private final String AUTHORIZATION_HEADER="Authorization";
    private final String BEARER_TOKEN_TYPE="Bearer ";
    @Autowired
    private OAuth2AuthorizedClientManager manager;
    
    @Override
    public void apply(RequestTemplate reqTemp) {
        
        String token =manager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("my-internal-client").principal("internal")
                .build()).getAccessToken().getTokenValue();
        reqTemp.header(this.AUTHORIZATION_HEADER, this.BEARER_TOKEN_TYPE+token);
        
    }
    
}

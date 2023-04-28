package com.lcwd.user.service.config;

import com.lcwd.user.service.config.interceptor.RestTemplateInterceptor;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationApp {
    @Autowired
    private ClientRegistrationRepository client;
    @Autowired
    private OAuth2AuthorizedClientRepository oauthClient;
    @Bean
    public OAuth2AuthorizedClientManager manager(
        ClientRegistrationRepository clientRegistRepo,
        OAuth2AuthorizedClientRepository authClientRepo){
        
        OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        
        DefaultOAuth2AuthorizedClientManager defaultAuthorizedClient
                = 
                new DefaultOAuth2AuthorizedClientManager
                (
                        clientRegistRepo, authClientRepo
                );
        
        defaultAuthorizedClient.setAuthorizedClientProvider(provider);
        
        return defaultAuthorizedClient;
    }
    
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restT=new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptor= new ArrayList<>();
        
        interceptor.add(new RestTemplateInterceptor(manager(client, oauthClient)));
        restT.setInterceptors(interceptor);
        return restT;
    }       
}
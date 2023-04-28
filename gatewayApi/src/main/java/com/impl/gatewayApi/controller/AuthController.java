package com.impl.gatewayApi.controller;
import com.impl.gatewayApi.config.MessageHandlerAuth;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public ResponseEntity<MessageHandlerAuth>login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model){
        
        MessageHandlerAuth auth= new MessageHandlerAuth();//Creating auth response object
        
        auth.setUserId(user.getEmail());//Setting email to response
        
        auth.setAccessToken(client.getAccessToken().getTokenValue());//Setting token to response 
        
        auth.setRefreshToken(client.getRefreshToken().getTokenValue());
        
        auth.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());
        
        List<String> authorities = user.getAuthorities().stream().map(grantedAuthority ->{
            return grantedAuthority.getAuthority();
        }).collect(Collectors.toList());
                
        auth.setAuthorities(authorities);
        
        return ResponseEntity.ok(auth);
    }
}

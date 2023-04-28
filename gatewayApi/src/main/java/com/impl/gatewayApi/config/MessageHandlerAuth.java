package com.impl.gatewayApi.config;
import java.util.Collection;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageHandlerAuth {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expireAt;
    private Collection<String>authorities;
}

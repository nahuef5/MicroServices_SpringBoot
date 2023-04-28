package com.lcwd.user.service.exceptions.messageHandler;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageHandler {
    private String message;
    private boolean success;
    private HttpStatus status;
}

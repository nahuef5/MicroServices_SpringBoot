package com.lcwd.user.service.exceptions;

import com.lcwd.user.service.exceptions.messageHandler.MessageHandler;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageHandler> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        MessageHandler response = MessageHandler.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);

    }
}

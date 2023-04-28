package com.lcwd.user.service.controller;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(UserController.class);
    //CREATE
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User us = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(us);
    }

    //GET A USER

    @GetMapping("/{userId}")
    @RateLimiter(name="userRateLimiter", fallbackMethod="ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable Integer userId) {        
        
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    //METHOD FOR  CircuitBreaker
    public ResponseEntity<User> ratingHotelFallback(Integer userId, Exception ex) {
        
        ex.printStackTrace();
        User user = User.builder()
                .email("ERROR@ERROR.com")
                .name("FATAL")
                .about("This error is created because some service is down")
                .userId(314159)
                .build();
        return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
    }

    //ALL USER
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
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
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User us = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(us);
    }

    //single user get

    @GetMapping("/{userId}")
    @RateLimiter(name="userRateLimiter", fallbackMethod="ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable Integer userId) {        
        
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    //creating fall back method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallback(Integer userId, Exception ex) {
        
        ex.printStackTrace();

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId(141234)
                .build();
        return new ResponseEntity(user, HttpStatus.BAD_REQUEST);
    }


    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}

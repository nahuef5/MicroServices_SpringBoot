package com.lcwd.user.service.services;

import com.lcwd.user.service.dto.*;
import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.repository.UserRepository;
import com.lcwd.user.service.services.external_services.HotelService;

import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;
    
    private final Logger log= LoggerFactory.getLogger(UserService.class);

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        //implement Rating service call: rest template
        return userRepository.findAll();
    }

    //get single user
    public User getUser(Integer userId) {
        //get user from database with the user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        //fetch rating of the above user from Rating service
        RatingDto[]ratingsOfUser=restTemplate.getForObject("http://RATING/ratings/users/"+user.getUserId(), RatingDto[].class);
        
        List<RatingDto> ratings=Arrays.stream(ratingsOfUser).toList();
        
        List<RatingDto>ratingList = ratings.stream().map( rating -> {
            //api call to hotel to get the hotel
            
            HotelDto hotel=hotelService.getHotel(rating.getHotelId());
            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        
        }).collect(Collectors.toList());
                
        user.setRatings(ratingList);
        return user;
    }
}

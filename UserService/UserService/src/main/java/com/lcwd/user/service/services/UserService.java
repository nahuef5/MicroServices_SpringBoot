package com.lcwd.user.service.services;

import com.lcwd.user.service.dto.*;
import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.repository.UserRepository;
import com.lcwd.user.service.services.external_services.HotelService;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
    //CREATE
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    //GET USERS LISt
    public List<User> getAllUser() {
        //IMPLEMENT RATING SERVICE CALL: REST TEMPLATE
        return userRepository.findAll();
    }

    //GET A USER
    public User getUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        //FETCH RATING OF THE ABOVE USER FROM RATING SERVICE
        RatingDto[]ratingsOfUser=restTemplate.getForObject("http://RATING/ratings/users/"+user.getUserId(), RatingDto[].class);
        
        List<RatingDto> ratings=Arrays.stream(ratingsOfUser).toList();
        
        List<RatingDto>ratingList = ratings.stream().map( rating -> {
            //API CALL TO HOTEL TO GET THE HOTEL
            HotelDto hotel=hotelService.getHotel(rating.getHotelId());
            //SET THE HOTEL TO RATING
            rating.setHotel(hotel);
            return rating;
        
        }).collect(Collectors.toList());
                
        user.setRatings(ratingList);
        return user;
    }
}

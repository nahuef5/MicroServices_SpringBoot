package com.lcwd.rating.service;

import com.lcwd.rating.entity.Rating;
import com.lcwd.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RatingService {

   @Autowired
    private RatingRepository repository;

    public Rating create(Rating rating) {
        return repository.save(rating);
    }

    public List<Rating> getRatings() {
        return repository.findAll();
    }

    public List<Rating> getRatingByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public List<Rating> getRatingByHotelId(Integer hotelId) {
        return repository.findByHotelId(hotelId);
    }
}
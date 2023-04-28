package com.lcwd.user.service.services.external_services;

import com.lcwd.user.service.dto.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name="RATING")
public interface RatingService {
     @PostMapping("/ratings")
    public ResponseEntity<RatingDto> createRating(RatingDto values);


    //PUT
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable("ratingId") Integer ratingId, RatingDto rating);


    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable Integer ratingId);
}


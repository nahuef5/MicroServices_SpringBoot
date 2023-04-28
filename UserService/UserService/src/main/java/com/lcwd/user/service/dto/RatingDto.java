package com.lcwd.user.service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Integer ratingId;
    private Integer userId;
    private Integer hotelId;
    private int rating;
    private String feedback;
    private HotelDto hotel;
}
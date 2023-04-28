package com.lcwd.user.service.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Integer hotelId;
    private String name;
    private String location;
    private String about;
}

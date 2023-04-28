package com.lcwd.user.service.services.external_services;
import com.lcwd.user.service.dto.HotelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="HOTEL")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    HotelDto getHotel(@PathVariable ("hotelId")Integer hotelId);
}

package com.lcwd.hotel.entity;

import java.io.Serializable;
import lombok.*;
import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Integer hotelId;
    private  String name;
    private  String location;
    private  String about;

    public Hotel(String name, String location, String about) {
        this.name = name;
        this.location = location;
        this.about = about;
    }
}

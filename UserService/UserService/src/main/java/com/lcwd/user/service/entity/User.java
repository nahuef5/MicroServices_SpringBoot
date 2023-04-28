package com.lcwd.user.service.entity;

import com.lcwd.user.service.dto.RatingDto;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "micro_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer userId;
    private String name;
    private String email;
    private String about;
    @Transient
    private List<RatingDto>ratings=new ArrayList<>();

    public User(String name, String email, String about) {
        this.name = name;
        this.email = email;
        this.about = about;
    }
    
}
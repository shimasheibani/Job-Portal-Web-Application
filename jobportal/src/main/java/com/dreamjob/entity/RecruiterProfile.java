package com.dreamjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "recuiter_profile")
public class RecruiterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR2(255)")
    private String city;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String company;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String country;
    @Column(columnDefinition = "VARCHAR2(255)", name = "first_name")
    private String firstName;
    @Column(columnDefinition = "VARCHAR2(255)", name = "last_name")
    private String lastName;
    @Column(nullable = true, length = 64, name = "profile_photo")
    private String profilePhoto;
    @Column(columnDefinition = "VARCHAR2(255)")
    private String state;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private User user;

    public RecruiterProfile(User savedUser) {
    }

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto == null || user == null) return null;
        return "/photos/recruiter/" + user.getId() + "/" + profilePhoto;
    }

}

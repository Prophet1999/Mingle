package com.mingle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    // Profile details
    private String bio;
    private String gender;
    private String interestedIn;

    private java.time.LocalDate dob;

    @ElementCollection
    private java.util.List<String> photos;

    // Preferences
    private Integer minAgePreference;
    private Integer maxAgePreference;
    private Double maxDistancePreference; // in Km

    // Location (Latitude/Longitude) - simplified for now, will use PostGIS later
    private Double latitude;
    private Double longitude;
}

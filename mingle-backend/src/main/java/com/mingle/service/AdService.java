package com.mingle.service;

import com.mingle.model.Ad;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AdService {

    private final List<Ad> mockAds = Arrays.asList(
            new Ad(UUID.randomUUID().toString(), "Premium Dating", "Get unlimited swipes!",
                    "https://via.placeholder.com/300x250", "https://example.com/premium"),
            new Ad(UUID.randomUUID().toString(), "Local Events", "Meet singles near you.",
                    "https://via.placeholder.com/300x250", "https://example.com/events"),
            new Ad(UUID.randomUUID().toString(), "Date Night", "Best restaurants for dates.",
                    "https://via.placeholder.com/300x250", "https://example.com/restaurants"));

    public Ad getRandomAd() {
        Random rand = new Random();
        return mockAds.get(rand.nextInt(mockAds.size()));
    }
}

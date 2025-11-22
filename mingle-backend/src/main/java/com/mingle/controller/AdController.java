package com.mingle.controller;

import com.mingle.model.Ad;
import com.mingle.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping("/random")
    public ResponseEntity<Ad> getRandomAd() {
        return ResponseEntity.ok(adService.getRandomAd());
    }
}

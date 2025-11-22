package com.mingle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String targetUrl;
}

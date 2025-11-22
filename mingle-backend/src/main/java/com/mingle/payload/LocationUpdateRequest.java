package com.mingle.payload;

import lombok.Data;

@Data
public class LocationUpdateRequest {
    private Double latitude;
    private Double longitude;
}

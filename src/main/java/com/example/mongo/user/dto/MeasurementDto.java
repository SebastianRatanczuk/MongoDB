package com.example.mongo.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementDto {
    private String date;
    private String measurement;
    private String info;
}

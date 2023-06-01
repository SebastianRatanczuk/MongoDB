package com.example.mongo.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {
    private String medicationName;
    private String dosage;
    private String time;
}

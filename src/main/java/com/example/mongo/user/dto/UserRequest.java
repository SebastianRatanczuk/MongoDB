package com.example.mongo.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRequest {

    private String name;
    private String surname;
    private String pesel;
    private String phone;
    private List<MeasurementDto> measurements;
    private List<MedicationDto> medications;
}

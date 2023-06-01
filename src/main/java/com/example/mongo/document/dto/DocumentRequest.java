package com.example.mongo.document.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentRequest {
    String clientId;
    String date;
    String name;
    String category;
    byte[] fileData;
}

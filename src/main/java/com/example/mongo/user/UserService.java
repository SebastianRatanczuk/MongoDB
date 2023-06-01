package com.example.mongo.user;

import com.example.mongo.user.dto.MeasurementDto;
import com.example.mongo.user.dto.MedicationDto;
import com.example.mongo.user.dto.UserRequest;
import com.example.mongo.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void create(UserRequest userRequest) {
        Document document = userMapper.requestToDocument(userRequest);
        userRepository.save(document);
    }

    public List<UserResponse> readAll() {
        return userRepository.findAll().stream()
                .map(userMapper::documentToResponse)
                .toList();
    }

    public void updateMedications(String id, List<MedicationDto> medicationDtos) {
        UserRequest userRequest = UserRequest.builder()
                .medications(medicationDtos)
                .build();
        update(id, userRequest);
    }

    public void updateMeasurements(String id, List<MeasurementDto> measurementDtos) {
        UserRequest userRequest = UserRequest.builder()
                .measurements(measurementDtos)
                .build();
        update(id, userRequest);
    }

    public void update(String id, UserRequest updateRequest) {
        Document rawClient = userRepository.findById(id);
        UserRequest clientFromDB = userMapper.documentToRequest(rawClient);
        UserRequest updatedDto = userMapper.updateEntity(updateRequest, clientFromDB);
        Update update = userMapper.requestToUpdate(updatedDto);
        userRepository.update(id, update);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}

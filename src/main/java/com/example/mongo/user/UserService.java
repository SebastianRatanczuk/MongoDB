package com.example.mongo.user;

import com.example.mongo.user.dto.MeasurementDto;
import com.example.mongo.user.dto.MedicationDto;
import com.example.mongo.user.dto.UserDto;
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

    public void create(UserDto userDto) {
        Document document = userMapper.dtoToDocument(userDto);
        userRepository.save(document);
    }

    public List<String> readAll() {
        return userRepository.findAll().stream()
                .map(Document::toString)
                .toList();
    }

    public void updateMedications(String id, List<MedicationDto> medicationDtos) {
        UserDto userDto = UserDto.builder()
                .medications(medicationDtos)
                .build();
        update(id, userDto);
    }

    public void updateMeasurements(String id, List<MeasurementDto> measurementDtos) {
        UserDto userDto = UserDto.builder()
                .measurements(measurementDtos)
                .build();
        update(id, userDto);
    }

    public void update(String id, UserDto updateRequest) {
        Document rawClient = userRepository.findById(id);
        UserDto clientFromDB = userMapper.documentToDto(rawClient);
        UserDto updatedDto = userMapper.updateEntity(updateRequest, clientFromDB);
        Update update = userMapper.dtoToUpdate(updatedDto);
        userRepository.update(id, update);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}

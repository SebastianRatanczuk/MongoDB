package com.example.mongo.user;

import com.example.mongo.user.dto.MeasurementDto;
import com.example.mongo.user.dto.MedicationDto;
import com.example.mongo.user.dto.UserRequest;
import com.example.mongo.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void create(@RequestBody UserRequest dto) {
        userService.create(dto);
    }

    @GetMapping
    public List<UserResponse> readAll() {
        return userService.readAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody UserRequest dto) {
        userService.update(id, dto);
    }

    @PutMapping("medications/{id}")
    public void updateMedication(@PathVariable String id, @RequestBody List<MedicationDto> dto) {
        userService.updateMedications(id, dto);
    }

    @PutMapping("measurements/{id}")
    public void updateMeasurements(@PathVariable String id, @RequestBody List<MeasurementDto> dto) {
        userService.updateMeasurements(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }
}

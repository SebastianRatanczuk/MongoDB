package com.example.mongo.user;

import com.example.mongo.MapstructConfig;
import com.example.mongo.user.dto.UserDto;
import com.example.mongo.user.dto.MeasurementDto;
import com.example.mongo.user.dto.MedicationDto;
import org.bson.Document;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MapstructConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto updateEntity(UserDto updateRequest, @MappingTarget UserDto entityToUpdate);

    default Update dtoToUpdate(UserDto userDto) {
        Update update = new Update();
        update.set("name", userDto.getName())
                .set("surname", userDto.getSurname())
                .set("pesel", userDto.getPesel())
                .set("phone", userDto.getPhone())
                .set("measurements", serializeMeasurements(userDto.getMeasurements()))
                .set("medications", serializeMedications(userDto.getMedications()));
        return update;
    }

    default Document dtoToDocument(UserDto userDto) {
        Document document = new Document();
        document.append("name", userDto.getName());
        document.append("surname", userDto.getSurname());
        document.append("pesel", userDto.getPesel());
        document.append("phone", userDto.getPhone());
        document.append("measurements", serializeMeasurements(userDto.getMeasurements()));
        document.append("medications", serializeMedications(userDto.getMedications()));
        return document;
    }

    default List<Document> serializeMeasurements(List<MeasurementDto> measurements) {
        List<Document> documents = new ArrayList<>();
        for (MeasurementDto measurement : measurements) {
            Document document = new Document();
            document.append("date", measurement.getDate());
            document.append("measurement", measurement.getMeasurement());
            document.append("info", measurement.getInfo());
            documents.add(document);
        }
        return documents;
    }

    default List<Document> serializeMedications(List<MedicationDto> medications) {
        List<Document> documents = new ArrayList<>();
        for (MedicationDto medication : medications) {
            Document document = new Document();
            document.append("medicationName", medication.getMedicationName());
            document.append("dosage", medication.getDosage());
            document.append("time", medication.getTime());
            documents.add(document);
        }
        return documents;
    }

    default UserDto documentToDto(Document document) {
        return UserDto.builder()
                .name(document.getString("name"))
                .surname(document.getString("surname"))
                .pesel(document.getString("pesel"))
                .phone(document.getString("phone"))
                .measurements(deserializeMeasurements(document.getList("measurements", Document.class)))
                .medications(deserializeMedications(document.getList("medications", Document.class)))
                .build();
    }

    default List<MeasurementDto> deserializeMeasurements(List<Document> measurementDocuments) {
        List<MeasurementDto> measurements = new ArrayList<>();
        for (Document measurementDocument : measurementDocuments) {
            MeasurementDto measurementDto = MeasurementDto.builder()
                    .date(measurementDocument.getString("date"))
                    .measurement(measurementDocument.getString("measurement"))
                    .info(measurementDocument.getString("info"))
                    .build();
            measurements.add(measurementDto);
        }
        return measurements;
    }

    default List<MedicationDto> deserializeMedications(List<Document> medicationDocuments) {
        List<MedicationDto> medications = new ArrayList<>();
        for (Document medicationDocument : medicationDocuments) {
            MedicationDto medicationDto = MedicationDto.builder()
                    .medicationName(medicationDocument.getString("medicationName"))
                    .dosage(medicationDocument.getString("dosage"))
                    .time(medicationDocument.getString("time"))
                    .build();
            medications.add(medicationDto);
        }
        return medications;
    }
}

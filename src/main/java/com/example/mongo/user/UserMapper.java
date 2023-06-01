package com.example.mongo.user;

import com.example.mongo.MapstructConfig;
import com.example.mongo.user.dto.MeasurementDto;
import com.example.mongo.user.dto.MedicationDto;
import com.example.mongo.user.dto.UserRequest;
import com.example.mongo.user.dto.UserResponse;
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
    UserRequest updateEntity(UserRequest updateRequest, @MappingTarget UserRequest entityToUpdate);

    default Update requestToUpdate(UserRequest userRequest) {
        Update update = new Update();
        update.set("name", userRequest.getName())
                .set("surname", userRequest.getSurname())
                .set("pesel", userRequest.getPesel())
                .set("phone", userRequest.getPhone())
                .set("measurements", serializeMeasurements(userRequest.getMeasurements()))
                .set("medications", serializeMedications(userRequest.getMedications()));
        return update;
    }

    default Document requestToDocument(UserRequest userRequest) {
        Document document = new Document();
        document.append("name", userRequest.getName());
        document.append("surname", userRequest.getSurname());
        document.append("pesel", userRequest.getPesel());
        document.append("phone", userRequest.getPhone());
        document.append("measurements", serializeMeasurements(userRequest.getMeasurements()));
        document.append("medications", serializeMedications(userRequest.getMedications()));
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

    default UserRequest documentToRequest(Document document) {
        return UserRequest.builder()
                .name(document.getString("name"))
                .surname(document.getString("surname"))
                .pesel(document.getString("pesel"))
                .phone(document.getString("phone"))
                .measurements(deserializeMeasurements(document.getList("measurements", Document.class)))
                .medications(deserializeMedications(document.getList("medications", Document.class)))
                .build();
    }

    default UserResponse documentToResponse(Document document) {
        return UserResponse.builder()
                .id(document.getObjectId("_id").toString())
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

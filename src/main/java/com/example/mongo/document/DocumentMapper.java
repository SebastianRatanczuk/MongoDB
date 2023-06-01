package com.example.mongo.document;

import com.example.mongo.MapstructConfig;
import com.example.mongo.document.dto.DocumentDto;
import com.example.mongo.document.dto.DocumentUpdateDto;
import org.bson.BsonDocument;
import org.bson.Document;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.mongodb.core.query.Update;

@Mapper(config = MapstructConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {
    DocumentDto updateEntity(DocumentUpdateDto updateRequest, @MappingTarget DocumentDto entityToUpdate);

    default Update dtoToUpdate(DocumentDto documentDto) {
        Update update = new Update();
        update.set("clientId", documentDto.getClientId())
                .set("date", documentDto.getDate())
                .set("name", documentDto.getName())
                .set("category", documentDto.getCategory())
                .set("fileData", documentDto.getFileData());
        return update;
    }

    default Document dtoToDocument(DocumentDto documentDto) {
        Document document = new Document();
        document.append("clientId", documentDto.getClientId());
        document.append("date", documentDto.getDate());
        document.append("name", documentDto.getName());
        document.append("category", documentDto.getCategory());
        document.append("fileData", documentDto.getFileData());
        return document;
    }

    default DocumentDto documentToDto(Document document) {
        BsonDocument bsonDocument = document.toBsonDocument();
        return DocumentDto.builder()
                .clientId(document.getString("clientId"))
                .date(document.getString("date"))
                .name(document.getString("name"))
                .category(document.getString("category"))
                .fileData(bsonDocument.getBinary("fileData").getData())
                .build();
    }
}

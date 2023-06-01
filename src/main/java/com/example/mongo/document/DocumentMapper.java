package com.example.mongo.document;

import com.example.mongo.MapstructConfig;
import com.example.mongo.document.dto.DocumentRequest;
import com.example.mongo.document.dto.DocumentResponse;
import com.example.mongo.document.dto.DocumentUpdateRequest;
import org.bson.BsonDocument;
import org.bson.Document;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.mongodb.core.query.Update;

@Mapper(config = MapstructConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {
    DocumentRequest updateEntity(DocumentUpdateRequest updateRequest, @MappingTarget DocumentRequest entityToUpdate);

    default Update requestToUpdate(DocumentRequest documentRequest) {
        Update update = new Update();
        update.set("clientId", documentRequest.getClientId())
                .set("date", documentRequest.getDate())
                .set("name", documentRequest.getName())
                .set("category", documentRequest.getCategory())
                .set("fileData", documentRequest.getFileData());
        return update;
    }

    default Document requestToDocument(DocumentRequest documentRequest) {
        Document document = new Document();
        document.append("clientId", documentRequest.getClientId());
        document.append("date", documentRequest.getDate());
        document.append("name", documentRequest.getName());
        document.append("category", documentRequest.getCategory());
        document.append("fileData", documentRequest.getFileData());
        return document;
    }

    default DocumentRequest documentToRequest(Document document) {
        BsonDocument bsonDocument = document.toBsonDocument();
        return DocumentRequest.builder()
                .clientId(document.getString("clientId"))
                .date(document.getString("date"))
                .name(document.getString("name"))
                .category(document.getString("category"))
                .fileData(bsonDocument.getBinary("fileData").getData())
                .build();
    }

    default DocumentResponse documentToResponse(Document document) {
        BsonDocument bsonDocument = document.toBsonDocument();
        return DocumentResponse.builder()
                .id(document.getObjectId("_id").toString())
                .clientId(document.getString("clientId"))
                .date(document.getString("date"))
                .name(document.getString("name"))
                .category(document.getString("category"))
                .fileData(bsonDocument.getBinary("fileData").getData())
                .build();
    }
}

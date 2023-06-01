package com.example.mongo.document;

import com.example.mongo.document.dto.DocumentRequest;
import com.example.mongo.document.dto.DocumentResponse;
import com.example.mongo.document.dto.DocumentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public void create(DocumentRequest userDto) {
        Document document = documentMapper.requestToDocument(userDto);
        documentRepository.save(document);
    }

    public List<DocumentResponse> readAll() {
        return documentRepository.findAll().stream()
                .map(documentMapper::documentToResponse)
                .toList();
    }

    public void update(String id, DocumentUpdateRequest updateRequest) {
        Document rawClient = documentRepository.findById(id);
        DocumentRequest clientFromDB = documentMapper.documentToRequest(rawClient);
        DocumentRequest updatedDto = documentMapper.updateEntity(updateRequest, clientFromDB);
        Update update = documentMapper.requestToUpdate(updatedDto);
        documentRepository.update(id, update);
    }

    public void delete(String id) {
        documentRepository.deleteById(id);
    }
}

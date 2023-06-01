package com.example.mongo.document;

import com.example.mongo.document.dto.DocumentDto;
import com.example.mongo.document.dto.DocumentUpdateDto;
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

    public void create(DocumentDto userDto) {
        Document document = documentMapper.dtoToDocument(userDto);
        documentRepository.save(document);
    }

    public List<DocumentDto> readAll() {
        return documentRepository.findAll().stream()
                .map(documentMapper::documentToDto)
                .toList();
    }

    public void update(String id, DocumentUpdateDto updateRequest) {
        Document rawClient = documentRepository.findById(id);
        DocumentDto clientFromDB = documentMapper.documentToDto(rawClient);
        DocumentDto updatedDto = documentMapper.updateEntity(updateRequest, clientFromDB);
        Update update = documentMapper.dtoToUpdate(updatedDto);
        documentRepository.update(id, update);
    }

    public void delete(String id) {
        documentRepository.deleteById(id);
    }
}

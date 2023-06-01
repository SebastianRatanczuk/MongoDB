package com.example.mongo.document;

import com.example.mongo.document.dto.DocumentRequest;
import com.example.mongo.document.dto.DocumentResponse;
import com.example.mongo.document.dto.DocumentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public void create(@RequestBody DocumentRequest dto) {
        documentService.create(dto);
    }

    @GetMapping
    public List<DocumentResponse> readAll() {
        return documentService.readAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody DocumentUpdateRequest dto) {
        documentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        documentService.delete(id);
    }
}

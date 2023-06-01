package com.example.mongo.document;

import com.example.mongo.document.dto.DocumentDto;
import com.example.mongo.document.dto.DocumentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public void create(@RequestBody DocumentDto dto) {
        documentService.create(dto);
    }

    @GetMapping
    public List<DocumentDto> readAll() {
        return documentService.readAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody DocumentUpdateDto dto) {
        documentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        documentService.delete(id);
    }
}

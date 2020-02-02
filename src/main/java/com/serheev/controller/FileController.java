package com.serheev.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @GetMapping(value = "/files/{file_name}")
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
        String FilePath = "/resources/" + fileName;
        return new FileSystemResource(FilePath);
    }
}

package com.enviro.grad001.assessment.CurthwellMulivha.Controller;


import com.enviro.grad001.assessment.CurthwellMulivha.Entity.EnvironmentalData;
import com.enviro.grad001.assessment.CurthwellMulivha.Repository.EnvironmentalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/environmental-data")
public class EnvironmentalDataController {

    @Autowired
    private EnvironmentalDataRepository repository;

@PostMapping("/upload")
public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
        EnvironmentalData data = new EnvironmentalData();
        data.setFileName(file.getOriginalFilename());
        data.setFileContent(file.getBytes());
        repository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully.");
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to upload file: " + e.getMessage());
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessedResult(@PathVariable Long id) {
        Optional<EnvironmentalData> optionalData = repository.findById(id);
        if (optionalData.isPresent()) {
            EnvironmentalData environmentalData = optionalData.get();
            String processedResult = environmentalData.getProcessedResult();
            if (processedResult != null) {
                return ResponseEntity.ok(processedResult);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Processed result not available.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<EnvironmentalData>> getAllProcessedResults() {
        List<EnvironmentalData> processedResults = repository.findAll();
        if (processedResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(processedResults);
        } else {
            return ResponseEntity.ok(processedResults);
        }
    }

}

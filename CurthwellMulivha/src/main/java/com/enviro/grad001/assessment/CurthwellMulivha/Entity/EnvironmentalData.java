package com.enviro.grad001.assessment.CurthwellMulivha.Entity;

import jakarta.persistence.*;

@Entity
public class EnvironmentalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private byte[] fileContent;

    private String processedResult;


    public EnvironmentalData() {
    }

    public EnvironmentalData(Long id, String fileName, byte[] fileContent, String processedResult) {
        this.id = id;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.processedResult = processedResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getProcessedResult() {
        return processedResult;
    }

    public void setProcessedResult(String processedResult) {
        this.processedResult = processedResult;
    }
}

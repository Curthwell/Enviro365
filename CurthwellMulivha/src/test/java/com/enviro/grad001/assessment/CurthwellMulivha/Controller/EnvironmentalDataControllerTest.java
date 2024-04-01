package com.enviro.grad001.assessment.CurthwellMulivha.Controller;

import com.enviro.grad001.assessment.CurthwellMulivha.Entity.EnvironmentalData;
import com.enviro.grad001.assessment.CurthwellMulivha.Repository.EnvironmentalDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EnvironmentalDataControllerTest {

    @Mock
    private EnvironmentalDataRepository repository;

    @InjectMocks
    private EnvironmentalDataController controller;

    @Test
    void testUploadFile() throws InaccessibleObjectException {
        // Prepare test data
        byte[] fileContent = "test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // Mock repository behavior
//        Mockito.doNothing().when(repository).save(any());

        // Mocking the save method of the repository
        doReturn(null).when(repository).save(any());
        // Perform the upload
        ResponseEntity<String> response = controller.uploadFile(multipartFile);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully.", response.getBody());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetProcessedResult() {
        // Prepare test data
        long id = 1L;
        EnvironmentalData data = new EnvironmentalData();
        data.setId(id);
        data.setProcessedResult("processed result");

        // Mock repository behavior
        when(repository.findById(id)).thenReturn(Optional.of(data));

        // Perform the request
        ResponseEntity<?> response = controller.getProcessedResult(id);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("processed result", response.getBody());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetProcessedResultNotFound() {
        // Prepare test data
        long id = 1L;

        // Mock repository behavior
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Perform the request
        ResponseEntity<?> response = controller.getProcessedResult(id);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetAllProcessedResults() {
        // Prepare test data
        List<EnvironmentalData> dataList = new ArrayList<>();
        dataList.add(new EnvironmentalData());
        dataList.add(new EnvironmentalData());

        // Mock repository behavior
        when(repository.findAll()).thenReturn(dataList);

        // Perform the request
        ResponseEntity<List<EnvironmentalData>> response = controller.getAllProcessedResults();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dataList, response.getBody());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllProcessedResultsEmpty() {
        // Prepare test data
        List<EnvironmentalData> emptyList = new ArrayList<>();

        // Mock repository behavior
        when(repository.findAll()).thenReturn(emptyList);

        // Perform the request
        ResponseEntity<List<EnvironmentalData>> response = controller.getAllProcessedResults();

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
        verify(repository, times(1)).findAll();
    }
}
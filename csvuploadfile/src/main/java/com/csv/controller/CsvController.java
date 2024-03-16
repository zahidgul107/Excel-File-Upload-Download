package com.csv.controller;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csv.entity.File;
import com.csv.helper.ExcelHelper;
import com.csv.repository.FileRepository;
import com.csv.service.CsvEntryService;

@RestController
@RequestMapping("/api/")
public class CsvController {
	
	@Autowired
	FileRepository fRepo;
	
	@Autowired
	CsvEntryService csvService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		
        if (ExcelHelper.checkExcelFormat(file)) {

		String uniqueId = UUID.randomUUID().toString();
		File f = new File(uniqueId, true);
		
		fRepo.save(f);
		this.csvService.save(file, f);
		
		com.csv.payload.response.ResponseBody responseBody = new com.csv.payload.response.ResponseBody(uniqueId);

		// Send 202 Accepted response with unique ID
		return ResponseEntity.accepted().body(responseBody);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
	}
	
	
	@GetMapping("download/{id}")
    public ResponseEntity<?> downloadCsv(@PathVariable String id) {

        File f = fRepo.findById(id).orElse(null);

        if (f == null) {
            // If it does not exist, return 404 Not Found response
            return ResponseEntity.notFound().build();
        }

        // Check if CsvEntry is still being processed
        if (f.isProcessing()) {
            // If it's still being processed, return 409 Conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Upload is still being processed");
        }
        
        String filename = "csv.xlsx";
        ByteArrayInputStream csvData = csvService.getDataByFile(f);
        InputStreamResource file = new InputStreamResource(csvData);
        
        ResponseEntity<InputStreamResource> body = ResponseEntity.ok()
        	.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        	.contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.ms-excel"))
        	.body(file);
        
        return body;
    }

}

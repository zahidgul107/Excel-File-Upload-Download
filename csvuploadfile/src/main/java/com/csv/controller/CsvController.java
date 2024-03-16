package com.csv.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		// Generate unique ID
		String uniqueId = UUID.randomUUID().toString();
		File f = new File(uniqueId, false);
		fRepo.save(f);
		this.csvService.save(file, f);
		// Construct response body with unique ID
		com.csv.payload.response.ResponseBody responseBody = new com.csv.payload.response.ResponseBody(uniqueId);

		// Send 202 Accepted response with unique ID
		return ResponseEntity.accepted().body(responseBody);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
	}

}

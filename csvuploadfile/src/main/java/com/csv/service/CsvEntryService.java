package com.csv.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csv.entity.CsvEntry;
import com.csv.entity.File;
import com.csv.helper.ExcelHelper;
import com.csv.repository.CsvEntryRepository;
import com.csv.repository.FileRepository;

@Service
public class CsvEntryService {
	
	@Autowired
	CsvEntryRepository csvRepo;
	@Autowired
	FileRepository fRepo;
	
	public void save(MultipartFile file, File f) {

        try {
            List<CsvEntry> products = ExcelHelper.convertExcelToListOfProduct(file.getInputStream(), f);
            this.csvRepo.saveAll(products);
            f.setProcessing(false);
            fRepo.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public ByteArrayInputStream getDataByFile(File f) {
		List<CsvEntry> all = csvRepo.findByFile(f);
		
		ByteArrayInputStream byteArrayInputStream = ExcelHelper.dataToExcel(all);
		
		return byteArrayInputStream;
	}

}

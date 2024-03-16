package com.csv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.entity.CsvEntry;
import com.csv.entity.File;

public interface CsvEntryRepository extends JpaRepository<CsvEntry, Long>{

	List<CsvEntry> findByFile(File f);

}

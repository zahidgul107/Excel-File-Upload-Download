package com.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.entity.CsvEntry;

public interface CsvEntryRepository extends JpaRepository<CsvEntry, String>{

}

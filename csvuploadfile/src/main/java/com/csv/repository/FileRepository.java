package com.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.entity.File;

public interface FileRepository extends JpaRepository<File, String> {
	
	

}

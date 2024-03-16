package com.csv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CsvEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	private String finalColumn;
	
	@ManyToOne
	@JoinColumn(name = "file_id")
	private File file;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinalColumn() {
		return finalColumn;
	}

	public void setFinalColumn(String finalColumn) {
		this.finalColumn = finalColumn;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public CsvEntry(Long id, String finalColumn, File file) {
		super();
		this.id = id;
		this.finalColumn = finalColumn;
		this.file = file;
	}

	public CsvEntry() {
		super();
	}
	
	

	
	
	

}

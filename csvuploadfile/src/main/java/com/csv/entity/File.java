package com.csv.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class File {
	
	@Id
    private String id;
	
	private boolean processing;

	public File() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isProcessing() {
		return processing;
	}

	public void setProcessing(boolean processing) {
		this.processing = processing;
	}

	public File(String id, boolean processing) {
		super();
		this.id = id;
		this.processing = processing;
	}

}

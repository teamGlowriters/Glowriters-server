package com.glowriters.domain;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Period {
	private LocalDateTime created_date;
	private LocalDateTime updated_date;

	public Period() {
		this.created_date = LocalDateTime.now();
		this.updated_date = LocalDateTime.now();
	}
}

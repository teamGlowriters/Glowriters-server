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
	
	@PrePersist
  protected void onCreate() {
      created_date = LocalDateTime.now();
      updated_date = LocalDateTime.now();
  }
}

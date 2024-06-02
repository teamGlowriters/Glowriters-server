package com.glowriters.domain;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	@ManyToOne
  private Member member;
	private int category_id;
	private LocalDateTime created_date;
	private LocalDateTime updated_date;
	private int post_status = 1;
	
	@PrePersist
  protected void onCreate() {
      created_date = LocalDateTime.now();
      updated_date = LocalDateTime.now();
  }
}

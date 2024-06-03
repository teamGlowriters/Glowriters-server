package com.glowriters.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Post extends Period{
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long post_id;
  private String title;
  private String content;
  private String category;
  private int post_status = 1;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;
  
  
}

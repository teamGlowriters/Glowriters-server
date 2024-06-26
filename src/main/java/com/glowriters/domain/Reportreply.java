package com.glowriters.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reportreply extends Period{
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reportreply_id;
	private String report_content;
	
	@ManyToOne 
  @JoinColumn(name = "post_id") 
	private Post post;
	
	@ManyToOne 
  @JoinColumn(name = "reply_id") 
	private Reply reply;
	
}

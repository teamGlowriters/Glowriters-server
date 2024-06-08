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
public class Reportmember extends Period{
	
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reportmember_id;
	private String report_content;
	
	@ManyToOne 
  @JoinColumn(name = "member_id") 
	private Member member;
}

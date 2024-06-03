package com.glowriters.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Postfile{
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long postfile_id;
	private String filepath;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
}

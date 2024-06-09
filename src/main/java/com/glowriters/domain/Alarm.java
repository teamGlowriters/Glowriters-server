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
public class Alarm extends Period{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long alarm_id;
	private int alarm_status=1;
	private int alarm_category;
	
	@ManyToOne 
  @JoinColumn(name = "sender_id")
  private Member sender;
	
	@ManyToOne 
  @JoinColumn(name = "receiver_id")
  private Member receiver;
}

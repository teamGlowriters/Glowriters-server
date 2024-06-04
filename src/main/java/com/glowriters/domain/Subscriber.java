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
public class Subscriber extends Period{
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subscribertable_id;
  // 0: 구독취소, 1: 구독중
	private int  subscribe_status = 1;
	
  @ManyToOne
  @JoinColumn(name="blogger_id") // 너의 테이블에 컬럼명이 뭐야?
  private Member blogger;
  
  @ManyToOne
  @JoinColumn(name="subscriber_id")
  private Member subscriber;
}

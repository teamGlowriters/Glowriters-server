package com.glowriters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glowriters.repository.SubscriberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriberService {

	@Autowired
	private final SubscriberRepository subscriberRepository;
  
  //특정 blogger_id를 기준으로 구독자 수를 반환하는 메소드
  public long countSubscribersByBloggerId(Long bloggerId) {
      return subscriberRepository.countByBloggerId(bloggerId);
  }
}

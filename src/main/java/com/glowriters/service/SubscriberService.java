package com.glowriters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Member;
import com.glowriters.domain.Postlike;
import com.glowriters.domain.Subscriber;
import com.glowriters.repository.SubscriberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriberService {

	private final SubscriberRepository subscriberRepository;

	public Subscriber save(Member blogger, Member subscriber) {
		Subscriber sub = new Subscriber();
		sub.setBlogger(blogger);
		sub.setSubscriber(subscriber);
		return subscriberRepository.save(sub);
	}

	@Transactional
	// 특정 member_id를 기준으로 구독자 수를 반환하는 메소드
	public long countSubscribersByBloggerId(Long member_id) {
		return subscriberRepository.countByBloggerId(member_id);
	}

	@Transactional
	public int checkSubscriptionStatus(Long bloggerId, Long subscriberId) {
		return subscriberRepository.existsByBloggerIdAndSubscriberId(bloggerId, subscriberId);
	}

	@Transactional
	public Subscriber getSubscriber(Long bloggerId, Long subscriberId) {
		return subscriberRepository.findByBloggerIdAndSubscriberId(bloggerId, subscriberId);
	}

	@Transactional
	public void subscribeCancel(Subscriber subscriber) {
		subscriber.setSubscribe_status(0);
		subscriberRepository.save(subscriber);
	}

	@Transactional
	public void subscribeCancelCancel(Subscriber subscriber) {
		subscriber.setSubscribe_status(1);
		subscriberRepository.save(subscriber);
	}

	@Transactional
	public List<Subscriber> getActiveSubscribersByBloggerId(Long memberId) {
		return subscriberRepository.findByBloggerIdAndSubscribeStatus(memberId);
	}
}

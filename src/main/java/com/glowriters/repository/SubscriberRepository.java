package com.glowriters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
  
  // 특정 blogger_id를 기준으로 구독자 수를 세는 메소드
	@Query("SELECT COUNT(s) FROM Subscriber s WHERE s.blogger.id = :bloggerId AND s.subscribe_status = 1")
	long countByBloggerId(@Param("bloggerId") Long bloggerId);
	
}

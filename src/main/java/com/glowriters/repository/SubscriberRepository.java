package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
  
  // 특정 blogger_id를 기준으로 구독자 수를 세는 메소드
	@Query("SELECT COUNT(s) FROM Subscriber s WHERE s.blogger.id = :bloggerId AND s.subscribe_status = 1")
	long countByBloggerId(@Param("bloggerId") Long bloggerId);
	
  // blogger_id와 subscriber_id를 기준으로 구독 상태를 확인하는 메소드
  @Query("SELECT CASE WHEN COUNT(s) > 0 THEN 1 ELSE 0 END FROM Subscriber s WHERE s.blogger.id = :bloggerId AND s.subscriber.id = :subscriberId")
  int existsByBloggerIdAndSubscriberId(@Param("bloggerId") Long bloggerId, @Param("subscriberId") Long subscriberId);
  
  // blogger_id와 subscriber_id를 기준으로 해당 레코드를 반환하는 메소드
  @Query("SELECT s FROM Subscriber s WHERE s.blogger.id = :bloggerId AND s.subscriber.id = :subscriberId")
  Subscriber findByBloggerIdAndSubscriberId(@Param("bloggerId") Long bloggerId, @Param("subscriberId") Long subscriberId);
  
  // member_id가 blogger_id와 같고 subscribe_status가 1인 행을 모두 가져오는 메소드
  @Query("SELECT s FROM Subscriber s WHERE s.blogger.id = :memberId AND s.subscribe_status = 1")
  List<Subscriber> findByBloggerIdAndSubscribeStatus(@Param("memberId") Long memberId);
}

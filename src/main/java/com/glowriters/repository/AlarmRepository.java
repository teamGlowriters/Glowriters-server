package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
	//status=1인 알람만 가져온다.
//	@Query("SELECT a FROM Alarm a WHERE a.alarm_status = 1")
//	List<Alarm> findAllByStatus();
	
	@Query("SELECT a FROM Alarm a WHERE a.alarm_status = 1")
	List<Alarm> findAll();
	
	//블로거가 받은 모든 알림 갯수
	@Query("SELECT COUNT(a) FROM Alarm a WHERE a.receiver.id = :bloggerId AND a.alarm_status = 1")
  long countByBloggerId(@Param("bloggerId") Long bloggerId);
}

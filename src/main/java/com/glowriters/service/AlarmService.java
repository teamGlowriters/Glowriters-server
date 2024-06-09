package com.glowriters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.glowriters.domain.Alarm;
import com.glowriters.domain.Member;
import com.glowriters.repository.AlarmRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;

	@Transactional
	public Alarm save(Member sender, Member receiver, int alarm_category) {
		Alarm alarm = new Alarm();
		alarm.setSender(sender);
		alarm.setReceiver(receiver);
		alarm.setAlarm_category(alarm_category);
		return alarmRepository.save(alarm);
	}

	@Transactional
	public Alarm findById(long alarm_id) {
		return alarmRepository.findById(alarm_id).orElseThrow(null);
	}

	@Transactional
	public List<Alarm> findAll() {
		return alarmRepository.findAll();
	}

	// 유저가 보낸 알람들을 리턴
	@Transactional
	public List<Alarm> findByUserId(long user_id) {
		return alarmRepository.findAll().stream().filter(alarm -> alarm.getSender().getMember_id() == user_id)
				.collect(Collectors.toList());
	}

	// 블로거에게 온 알람들을 리턴
	@Transactional
	public List<Alarm> findByBloggerId(long blogger_id) {
		return alarmRepository.findAll().stream().filter(alarm -> alarm.getReceiver().getMember_id() == blogger_id)
				.collect(Collectors.toList());
	}

	@Transactional
	public long countAlarmByMemberId(long member_id) {
		return alarmRepository.countByBloggerId(member_id);
	}
	
	//하나의 알림을 삭제하는 함수 (삭제 = 알림을 확인) => 아이디로
	@Transactional
	public Alarm deleteAlarm(long alarm_id) {
		Alarm alarm = alarmRepository.findById(alarm_id).orElse(null);
		alarm.setAlarm_status(0);
		return alarmRepository.save(alarm);
	}
	
	//하나의 알림을 삭제하는 함수 (삭제 = 알림을 확인) => 알림 객체로
	@Transactional
	public Alarm deleteAlarm(Alarm alarm) {
		alarm.setAlarm_status(0);
		return alarmRepository.save(alarm);
	}
	
	//여러 알림을 전부 삭제하는 함수
	@Transactional
	public List<Alarm> deleteAlarm(List<Alarm> alarms) {
		List<Alarm> result = new ArrayList<>();
		
		for (Alarm alarm : alarms) {
			alarm.setAlarm_status(0);
			result.add(alarmRepository.save(alarm)); //저장하면서 동시에 결과로 기록
		}
		
		return result; //결과 리턴
	}

}

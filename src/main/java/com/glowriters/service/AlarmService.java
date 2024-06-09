package com.glowriters.service;

import org.springframework.stereotype.Service;

import com.glowriters.domain.Alarm;
import com.glowriters.domain.Member;
import com.glowriters.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	
	public Alarm save(Member sender, Member receiver, int alarm_category) {
		Alarm alarm = new Alarm();
		alarm.setSender(sender);
		alarm.setReceiver(receiver);
		alarm.setAlarm_category(alarm_category);
		return alarmRepository.save(alarm);
	}
}

package com.glowriters.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDTO {
	// 알림전달메시지
	private String message;
	private String alarmTypeImg;
	private String alarmGoToLink;
	
	// Member
	
	// Alarm 
	private long alarm_id;
	private int alarm_status; //1: 읽지않음 0: 읽음
	private int alarm_category; //1:구독 2:좋아요
	private long sender_id;
	private long receiver_id;
	private LocalDateTime created_date;
	private LocalDateTime updated_date;
	//필요에따라 sender/receiver의멤버객체를 가져올수있음
	
	
}

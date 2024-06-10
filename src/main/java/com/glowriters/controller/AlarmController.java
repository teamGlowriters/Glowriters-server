package com.glowriters.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowriters.DTO.AlarmDTO;
import com.glowriters.domain.Alarm;
import com.glowriters.service.AlarmService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlarmController extends BaseController {
	private final AlarmService alarmService;

	public List<AlarmDTO> getAlarmDTO(List<Alarm> alarms) {
		List<AlarmDTO> ads = new ArrayList<>();

		for (Alarm alarm : alarms) {
			AlarmDTO ad = new AlarmDTO();

			// 1(구독)과 2(좋아요)일때 다르게 지정
			String message = "";
			String alarmGoToLink = "";
			ad.setAlarm_category(alarm.getAlarm_category());
			if (ad.getAlarm_category() == 1) {
				ad.setAlarmTypeImg("/images/subscribeIcon.png");
				message += "[ 구독 ] ";
				message += alarm.getSender().getMember_nickname();
				message += "님이 나를 구독 하였습니다.";
				alarmGoToLink += "/blogger-details/blogger-details/";
				alarmGoToLink += alarm.getSender().getMember_id();
			} else if (ad.getAlarm_category() == 2) {
				ad.setAlarmTypeImg("/images/likeIcon.png");
				message += "[ 좋아요 ] ";
				message += alarm.getSender().getMember_nickname();
				message += "님이 내 게시글에 좋아요를 눌렀습니다.";
				// 좋아요 누른 게시물로 이동하려면 아래 링크를 수정
				alarmGoToLink += "/blogger-details/blogger-details/";
				alarmGoToLink += alarm.getSender().getMember_id();
			}
			ad.setMessage(message);
			ad.setAlarmGoToLink(alarmGoToLink);

			// 공통 부분
			ad.setAlarm_id(alarm.getAlarm_id());
			ad.setAlarm_status(alarm.getAlarm_status()); // 사실상 1인것만 가져왔을테니 안씀
			ad.setSender_id(alarm.getSender().getMember_id());
			ad.setReceiver_id(alarm.getReceiver().getMember_id());
			ad.setCreated_date(alarm.getCreated_date());
			ad.setUpdated_date(alarm.getUpdated_date());

			ads.add(ad);
		}

		return ads;
	}

	@GetMapping("/alarm/alarm")
	public String viewAlarm(HttpSession session, Model model) {
		// 로그인 안했으면 메인페이지로 이동
		if (model.getAttribute("isLogin") == "no") {
			return "redirect:/";
		}

		// 현재 사용자에게 온 알람을 조회
		long blogger_id = (long) session.getAttribute("member_id");
		List<Alarm> alarms = alarmService.findByBloggerId(blogger_id);

		// 뷰에 뿌릴 DTO를 작성
		List<AlarmDTO> ads = getAlarmDTO(alarms);

		model.addAttribute("ads", ads);
		return "/alarm/alarm";
	}

	// 알림 삭제했을때 호출
	@GetMapping("/alarm/alarm/delete")
	public String deleteAlarm(@RequestParam("alarm_id") long alarm_id, Model model, HttpSession session) {
		// 1. 알림 삭제 - 아이디로 삭제
		alarmService.deleteAlarm(alarm_id);
		
		// 2. 다시 현재 사용자에게 온 알람을 조회
		long blogger_id = (long) session.getAttribute("member_id");
		List<Alarm> alarms = alarmService.findByBloggerId(blogger_id);
		List<AlarmDTO> ads = getAlarmDTO(alarms);
		
		// 3. 새로 뷰를 전달
		model.addAttribute("ads", ads);
		return "/alarm/alarm :: #notice";
	}

}

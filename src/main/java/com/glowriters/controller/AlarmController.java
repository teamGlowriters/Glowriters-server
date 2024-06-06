package com.glowriters.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlarmController extends BaseController {
	
	
	@GetMapping("/alarm/alarm")
	public String viewAlarm() {
		
		
		return "/alarm/alarm";
	}
	

}

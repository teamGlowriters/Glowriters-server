package com.glowriters.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowriters.domain.KakaoDTO;
import com.glowriters.service.KakaoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	private final KakaoService kakaoService;
	private final ObjectMapper objectMapper;
	
	//메인뷰에서 로그인 버튼을 눌렀을때
	@GetMapping("/oauth")
	public HttpEntity<String> callback(HttpServletRequest request) throws Exception{
		//카카오로부터 받은 인증코드를 통해 사용자정보를담은 domain객체를 생성(서비스호출)
		KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));
		
		//결과출력용
		String result = objectMapper.writeValueAsString(kakaoInfo);
		return ResponseEntity.ok(result);
	}
}

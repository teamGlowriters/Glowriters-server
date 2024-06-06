package com.glowriters.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowriters.DTO.KakaoDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoService {

	@Value("${kakao.client.id}")
	private String KAKAO_CLIENT_ID;

	@Value("${kakao.client.secret}")
	private String KAKAO_CLIENT_SECRET;

	@Value("${kakao.redirect.url}")
	private String KAKAO_REDIRECT_URL;

	private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
	private final static String KAKAO_API_URI = "https://kapi.kakao.com";

	// 1. 카카오
	public String getKakaoLogin() {
		return KAKAO_AUTH_URI + "/oauth/authorize" + "?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri=" + KAKAO_REDIRECT_URL
				+ "&response_type=code"; // 고정값 전달
	}

	// 2. 인증코드로 카카오에 http요청을보내서 엑세스토큰을 요청.
	// 리프레쉬토큰도 주는데. 이것은 엑세스토큰을 갱신할때 사용. (저장해두고 사용)
	// 이 함수는 엑세스토큰으로 사용자정보만 리턴함 (리프레시토큰사용 X)
	public KakaoDTO getKakaoInfo(String code) throws Exception {
		if (code == null)
			throw new Exception("인증코드를 얻을수 없었음");

		String accessToken = "";
		String refreshToken = "";

		try {
			// 요청을 만듬
			HttpHeaders headers = new HttpHeaders();
			// 헤더만들고
			headers.add("Content-type", "application/x-www-form-urlencoded");
			// 바디만들고
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", KAKAO_CLIENT_ID);
			params.add("client_secret", KAKAO_CLIENT_SECRET);
			params.add("code", code);
			params.add("redirect_uri", KAKAO_REDIRECT_URL);

			// Kakao OAuth API를 호출하기위해 (REST API를 사용하기위해)
			// RestTemplate객체를 만듬
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

			// RestTemplate객체를 사용해서 URL로 요청을 보내고 응답을 response에 담는다.
			ResponseEntity<String> response = restTemplate.exchange(KAKAO_AUTH_URI + "/oauth/token", // 해당 url로 요청을 보낸다.
					HttpMethod.POST, // POST로 보냄
					httpEntity, // 위에서만든 헤더와 바디를 담은 객체
					String.class // 요청에대한 응답 타입
			);

			//응답에서 토큰을 추출
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonObj = objectMapper.readTree(response.getBody());

			accessToken = jsonObj.get("access_token").asText();
			refreshToken = jsonObj.get("refresh_token").asText();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("API call failed");
		}

		return getUserInfoWithToken(accessToken, refreshToken);
	}

	
	// 3. 토근을 받아서 kakao domain객체 생성
	private KakaoDTO getUserInfoWithToken(String accessToken, String refreshToken) throws Exception {
		// 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		// 엑세스토큰을 담아서 사용자정보를 요청하는 헤더
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 요청을 보내고 응답받기
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = rt.exchange(KAKAO_API_URI + "/v2/user/me", HttpMethod.POST, httpEntity,
				String.class);

		// 응답 데이터 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonObj = objectMapper.readTree(response.getBody());
		JsonNode account = jsonObj.get("kakao_account");
		JsonNode profile = account.get("profile");

		// 가져온 정보들
		long id = jsonObj.get("id").asLong();
		String email = account.get("email").asText();
		String nickname = profile.get("nickname").asText();
		String profileImage = profile.get("profile_image_url").asText(); // 프로필 이미지 URL 가져오기

		return KakaoDTO.builder()
				.id(id)
				.email(email)
				.nickname(nickname)
				.profileImage(profileImage)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	//로그아웃요청을 보내는 함수
	public void kakaoDisconnect(String accessToken) throws JsonProcessingException{
		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded");
    
    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
    		"https://kapi.kakao.com/v1/user/logout",
    		HttpMethod.POST,
    		kakaoLogoutRequest,
    		String.class
    );
    
    // 응답받은 responsebody 내용을 꺼냄
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    
    String member_hashcode = String.valueOf(jsonNode.get("id"));
    log.info("로그아웃한 회원의 해쉬코드 =  " + member_hashcode);
	}

}

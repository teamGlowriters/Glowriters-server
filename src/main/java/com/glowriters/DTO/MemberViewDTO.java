package com.glowriters.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

//멤버(블로거)에 대한 정보
@Getter
@Setter
public class MemberViewDTO {
	
  // Member (블로거 자체 사람에대한 정보)
	private long member_id;
	private String member_nickname;
	private String member_profile;
	private String member_comment;
	
	// Post 블로거가 작성한 총 게시물 수
	private long postCount; 
	 
	// Subscriber 블로거를 구독하는 사람들 수
	private long subscriberCount;
	// 로그인한 사용자가 해당 블로거를 구독했는지 알려줌
	private int isSubscribe;
	
	// Categorys 블로거가 작성한 게시글의 카테고리 종류들
	private List<String> categorys;
}

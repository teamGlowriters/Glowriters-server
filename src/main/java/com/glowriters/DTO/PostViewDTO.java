package com.glowriters.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostViewDTO {
	
	// Member
	private String member_nickname;
	private String member_profile;
	
	// Post
	private long post_id;
	private String title;
	private String content;
	private String category;
	private LocalDateTime created_date;
	private LocalDateTime updated_date;
	
	// 새로운게시글인지
	// created == updated를 비교해서 넣을것
	//"yes", "no"값이 들어감
	private String NEW;
	
	// PostFile
	private String filepath;
	
	// Subscriber cnt
	private long subscriberCount;
	
	// Like cnt 추후에 좋아요갯수는 구현해야함
	private long likeCount;
}

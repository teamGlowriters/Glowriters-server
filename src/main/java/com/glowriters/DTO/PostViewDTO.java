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
	private LocalDateTime create_date;
	
	// PostFile
	private String filepath;
	
	// Subscriber
	private long subscriberCount;
}

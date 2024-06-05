package com.glowriters.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberViewDTO {
	
  // Member
	private String member_nickname;
	private String member_profile;
	
	// Post
	private String content;
	private long postCount;
	
	// Subscriber
	private long subscriberCount;
}

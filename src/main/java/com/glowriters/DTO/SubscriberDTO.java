package com.glowriters.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriberDTO {
	
	// 해당 블로거를 구독하는 사람들의 정보
	private long member_id;
	private String member_nickname;
	private String member_profile;
	private String member_comment;
	
}

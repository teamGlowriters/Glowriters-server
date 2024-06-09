package com.glowriters.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

// 댓글에 관련된 정보
@Getter
@Setter
public class ReplyViewDTO {
	
  // member (댓글 작성자 닉네임, 댓글 작성자 프로필)
	private long member_id;
	private String reply_member_nickname;
	private String reply_member_profile;
	
	// reply (댓글 내용, 댓글 생성 시간)
	private long reply_id;
	private String reply_content;
	private LocalDateTime reply_created_date;
}

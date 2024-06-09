package com.glowriters.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerCommentDTO {
	
	//댓글이 달린 게시물의 제목
	private String title;
	
	private long reportReply_id;
	
	//댓글 내용
	private String reply_content;
	
	//신고내용
	private String report_content;
	
	//게시물, 댓글내용이 같은 갯수
	private long reportCount;
	
	//가장 최근 신고 날짜
	private LocalDateTime updated_date;
	
}

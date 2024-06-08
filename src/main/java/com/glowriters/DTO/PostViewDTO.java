package com.glowriters.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.glowriters.domain.Period;
import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;

import lombok.Getter;
import lombok.Setter;

//게시물에 대한 정보
@Getter
@Setter
public class PostViewDTO {
	
	// Member (게시물을 작성한 사람에대한 정보)
	private long member_id;
	private String member_nickname;
	private String member_profile;
	private String member_comment;
	
	// Post (게시물 자체에 대한 정보)
	private long post_id;
	private String title;
	private String content;
	private String category;
	private LocalDateTime created_date;
	private LocalDateTime updated_date;
	
	
	// PostFile (게시물에 첨부됬던 이미지중 첫번째하나)
	private String filepath;
	// 게시물에 첨부됬던 이미지들 모두 다
	public List<String> filepaths = new ArrayList<String>();
	
	// Subscriber cnt (게시물을 작성한 사람의 구독자 수)
	private long subscriberCount;
	
	// 게시물이 새로운 게시물인가?
	//"yes", "no"값이 들어감
	// getCreated.equals(getUpdated)를 비교해서 넣을것
	private String NEW;
	
	// 게시글의 좋아요 갯수. 나중에 구현
	private long likeCount;
	
	// 게시글 좋아요 여부
	private int isLike;
	
	// reply(댓글)
	private String reply_member_nickname;
	private String reply_member_profile;
	private LocalDateTime reply_created_date;
	private String reply_content;
	private long replyCount;
	
}

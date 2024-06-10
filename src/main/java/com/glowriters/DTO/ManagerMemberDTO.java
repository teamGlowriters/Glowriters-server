package com.glowriters.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerMemberDTO {
	//테이블 고유 id
	private long reportMember_id;
	//신고당한 멤버 id
	private long member_id;
	//신고 당한 멤버 닉네임
	private String member_nickname;
	//신고를 몇번 당했는가
	private long report_count;
	//신고내용
	private String report_content;
}

package com.glowriters.domain;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends Period {

	@Id
	// primary키 자동 섪정
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long member_id;
	
	private String member_hashcode;
	private String member_email;
	private String member_nickname;
	// 0: 탈퇴, 1: 회원
	private int member_status = 1;
	private String member_profile;
	private String member_comment="";
	
//  양방향 연결
//	@OneToMany(mappedBy = "member")
//	@OneToMany(mappedBy = "Member", cascade = CascadeType.REMOVE)  
//  private List<Post> posts;
 
}

package com.glowriters.domain;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends Period {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String user_id;
	private String user_email;
	private String user_nickname;
	// 0: 탈퇴, 1: 회원
	private int user_status = 1;
	private String user_profile;
	private String user_comment = "";
	
	
	@OneToMany(mappedBy = "member")
  private List<Post> posts;
 
}

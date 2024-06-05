package com.glowriters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;
import com.glowriters.repository.PostFileRepository;

import jakarta.transaction.Transactional;

@Service
public class PostFileSerivce {
	
	@Autowired
	private final PostFileRepository postFileRepository;

	public PostFileSerivce(PostFileRepository postFileRepository) {
		this.postFileRepository = postFileRepository;
	}
	
	@Transactional
	public Postfile save(Post post, Postfile postFile) {
		postFile.setPost(post);
		return postFileRepository.save(postFile);
	}
	
	// Postfile 테이블에서 post_id를 가지고 매개변수로 입력받은 post_id와 값이 같은 Postfile 객체만 리스트로 가지고옴
	// 리스트로 가지고 온 이유는 1개 ~ 3개(몇개를 가지고 올 지 모름) 따라서 List로 가지고 옴
	@Transactional
	public List<Postfile> findAllByPost(long post_id) {
		return postFileRepository.findByPostId(post_id);
	}
	
	@Transactional
	public List<Postfile> findAll(){
		return postFileRepository.findAll();
	}
}

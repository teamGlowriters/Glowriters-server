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
	
	@Transactional
	public List<Postfile> findAll(){
		return postFileRepository.findAll();
	}
}

package com.glowriters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glowriters.domain.Post;
import com.glowriters.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post save(Post post) {
		return postRepository.save(post);
	}
}

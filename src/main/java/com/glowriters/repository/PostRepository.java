package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.glowriters.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByCategory(String category);
}

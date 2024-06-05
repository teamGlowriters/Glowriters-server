package com.glowriters.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("SELECT p FROM Post p WHERE p.category = :category ORDER BY p.created_date DESC")
  List<Post> findByCategoryOrderByCreatedDateDescJPQL(@Param("category") String category);
	
	@Query("SELECT COUNT(p) FROM Post p WHERE p.member.id = :memberId AND p.post_status = 1")
	long countBymemberId(@Param("memberId") Long memberId);
}

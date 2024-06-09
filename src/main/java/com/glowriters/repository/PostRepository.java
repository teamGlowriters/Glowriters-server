package com.glowriters.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

	@Query("SELECT p FROM Post p WHERE p.category = :category AND p.post_status = 1 ORDER BY p.created_date DESC")
  List<Post> findByCategoryOrderByCreatedDateDescJPQL(@Param("category") String category);
	
	@Query("SELECT COUNT(p) FROM Post p WHERE p.member.id = :memberId AND p.post_status = 1")
	long countBymemberId(@Param("memberId") Long memberId);
	
	// 제목으로 입력값과 같은 게시글을 가져오는 JPA
	List<Post> findByTitleContaining(String keyword);
	
  // member_id가 같고 post_status가 1인 게시물들의 category를 뽑아주는 쿼리
  @Query("SELECT p.category FROM Post p WHERE p.member.id = :memberId AND p.post_status = 1")
  List<String> findCategoriesByMemberIdAndPostStatus(@Param("memberId") Long memberId);
  
  @Query("SELECT p FROM Post p WHERE p.member.id = :memberId AND p.post_status = 1 ORDER BY p.created_date DESC")
  List<Post> findByMemberIdAndPostStatus(@Param("memberId") Long memberId);
  
  // 전체를 가져오되 status=1인 것만
  @Query("SELECT p FROM Post p WHERE p.post_status = 1")
  List<Post> findAllByStatus();
}

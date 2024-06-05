package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.glowriters.domain.Post;
import com.glowriters.domain.Postfile;

public interface PostFileRepository extends JpaRepository<Postfile, Long>{
	// post_id가 매개변수로 전달한 post_id가 Postfile 테이블의 post_id와 같은 것들을 가져오는 쿼리
	@Query("SELECT pf FROM Postfile pf WHERE pf.post.id = :postId")
  List<Postfile> findByPostId(@Param("postId") Long postId);
}

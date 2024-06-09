package com.glowriters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Reportreply;

public interface ReportReplyRepository extends JpaRepository<Reportreply, Long> {

	// 게시글id 댓글id와 일치하는 모든 객체를 가져오는 쿼리
	@Query("SELECT r FROM Reportreply r WHERE r.post.post_id = :post_id AND r.reply.reply_id = :reply_id")
	List<Reportreply> findByPostIdAndReplyId(long post_id, long reply_id);

//	@Query("SELECT r FROM Reportreply r WHERE r.post.post_id = :post_id AND r.reply.reply_id = :reply_id AND r.report_content = :report_content")
//	List<Reportreply> findByPostIdAndReplyIdAndContent(long post_id, long reply_id, String report_content);

	// 검색어로 댓글내용과 일치하는 결과를 가져오는 쿼리
	@Query("SELECT rr FROM Reportreply rr INNER JOIN rr.reply r WHERE r.reply_content LIKE %:keyword%")
	List<Reportreply> findByPostContentContaining(String keyword);

	// id를 받아서 행을 아예 삭제
	@Transactional
	@Modifying
	@Query("DELETE FROM Reportreply rr WHERE rr.reportreply_id = :reportreplyId")
	void deleteByReportreplyId(Long reportreplyId);
}

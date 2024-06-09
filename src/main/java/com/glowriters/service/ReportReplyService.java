package com.glowriters.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glowriters.domain.Post;
import com.glowriters.domain.Reply;
import com.glowriters.domain.Reportreply;
import com.glowriters.repository.ReportReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportReplyService {

	private final ReportReplyRepository reportReplyRepository;

	public Reportreply save(Post replyInPost, Reply badReply, Reportreply reportreply) {
		reportreply.setPost(replyInPost);
		reportreply.setReply(badReply);
		return reportReplyRepository.save(reportreply);
	}

	public List<Reportreply> findAll() {
		return reportReplyRepository.findAll();
	}

	//모든 댓글신고 객체를 날짜가 빠른순으로 정렬해서 리턴(1월이 2월보다 인덱스가 앞인것.)
	public List<Reportreply> findAllSortByUpdateDate() {
		return reportReplyRepository.findAll().stream()
				.sorted(Comparator.comparing(Reportreply::getUpdated_date).reversed()).collect(Collectors.toList());
	}
	
	//게시물id 댓글id와 일치하는 모든 신고 행을 가져옴
	public List<Reportreply> findByPostIdAndReplyId(long post_id, long reply_id){
		return reportReplyRepository.findByPostIdAndReplyId(post_id, reply_id);
	}
	
//	public List<Reportreply> findByPostIdAndReplyIdAndContent(long post_id, long reply_id, String report_content){
//		return reportReplyRepository.findByPostIdAndReplyIdAndContent(post_id, reply_id, report_content);
//	}
	
	//검색어로 댓글내용을 가져오는 함수
	public List<Reportreply> findReplyContentByKeyword(String keyword){
		return reportReplyRepository.findByPostContentContaining(keyword);
	}
	
	
	//신고 기록을 삭제
	@Transactional
	public void deleteById(long reportReply_id) {
		reportReplyRepository.deleteByReportreplyId(reportReply_id);
	}
	

}

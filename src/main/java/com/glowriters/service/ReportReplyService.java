package com.glowriters.service;

import org.springframework.stereotype.Service;

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
}

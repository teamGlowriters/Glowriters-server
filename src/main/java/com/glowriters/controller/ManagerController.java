package com.glowriters.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowriters.DTO.ManagerCommentDTO;
import com.glowriters.domain.Reportreply;
import com.glowriters.service.ReportReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController extends BaseController {
	private final ReportReplyService reportreplyService;

	public List<ManagerCommentDTO> getMangerCommentDTO(List<Reportreply> reports) {
		List<ManagerCommentDTO> mcds = new ArrayList<>();

		// 1차
		for (Reportreply report : reports) {
			ManagerCommentDTO mcd = new ManagerCommentDTO();
			mcd.setTitle(report.getPost().getTitle());
			mcd.setReportReply_id(report.getReportreply_id());
			mcd.setReply_content(report.getReply().getReply_content());
			mcd.setReport_content(report.getReport_content());

			// 현재 댓글신고와 같은 post_id, reply_id를 가진 모든 갯수를 셈
			long reportCount = reportreplyService
					.findByPostIdAndReplyId(report.getPost().getPost_id(), report.getReply().getReply_id()).size();
			mcd.setReportCount(reportCount);

			mcd.setUpdated_date(report.getUpdated_date());
			mcds.add(mcd);
		}

		return mcds;
	}

	// 매니저페이지 로그인화면
	@GetMapping("/manager/login")
	public String viewManagerLogin() {

		return "/manager/login/login";
	}

	// 댓글 신고 관리 페이지
	@GetMapping("/manager/comment")
	public String viewManagerComment(Model model) {
		// 최신순으로 정렬해서 가져옴
		List<Reportreply> replys = reportreplyService.findAllSortByUpdateDate();
		List<ManagerCommentDTO> mcds = getMangerCommentDTO(replys);

		model.addAttribute("mcds", mcds);
		return "/manager/comment/comment";
	}
	
	//검색결과를 비동기 통신으로 전달
	@GetMapping("/manager/comment/{sort}")
	public String asyncRecent(@PathVariable("sort") String sort, @RequestParam("text") String text, Model model) {
		List<Reportreply> replys = new ArrayList<>();

		if (text == null) { // 검색어 입력 안했을때는 전체 검색
			replys = reportreplyService.findAll();
		} else { // 검색어 입력했을때는 검색어만 검색
			replys = reportreplyService.findReplyContentByKeyword(text);
		}

		List<ManagerCommentDTO> mcds = getMangerCommentDTO(replys);

		if (sort.equals("recent")) {
			mcds = mcds.stream().sorted(Comparator.comparing(ManagerCommentDTO::getUpdated_date).reversed())
					.collect(Collectors.toList());
		} else if (sort.equals("cumulate")) {
			mcds = mcds.stream().sorted(Comparator.comparingLong(ManagerCommentDTO::getReportCount).reversed())
					.collect(Collectors.toList());
		}

		model.addAttribute("mcds", mcds);
		return "/manager/comment/comment :: #commentList";
	}
	
	//삭제 결과를 비동기 통신으로 전달
	@PostMapping("/manager/comment/delete/delete")
	public String asyncDelete(@RequestBody Map<String, Object> data, Model model) {
		// 삭제 로직 진행
		List<Long> reportIds = new ArrayList<>();
    if (data.containsKey("reportIds")) {
        Object reportIdsObj = data.get("reportIds");
        if (reportIdsObj instanceof List) {
            List<?> reportIdsList = (List<?>) reportIdsObj;
            for (Object id : reportIdsList) {
                if (id instanceof Long) {
                    reportIds.add((Long) id);
                } else if (id instanceof String) {
                    reportIds.add(Long.parseLong((String) id));
                }
            }
        }
    }
    for (Long report_id : reportIds) {
        reportreplyService.deleteById(report_id);
    }
    
		//다시 페이지를 보여줌
		List<Reportreply> replys = new ArrayList<>();
		replys = reportreplyService.findAll();
		List<ManagerCommentDTO> mcds = getMangerCommentDTO(replys);

		model.addAttribute("mcds", mcds);
		return "/manager/comment/comment :: #commentList";
	}
	
	
	
	
	
	
	
	

	@GetMapping("/manager/user")
	public String viewManagerUser() {

		return "/manager/user/user";
	}

}

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
import com.glowriters.DTO.ManagerMemberDTO;
import com.glowriters.domain.Reportmember;
import com.glowriters.domain.Reportreply;
import com.glowriters.service.ReportMemberService;
import com.glowriters.service.ReportReplyService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ManagerController extends BaseController {
	private final ReportReplyService reportreplyService;
	private final ReportMemberService reportMemberService;

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

	public List<ManagerMemberDTO> getManagerMemberDTO(List<Reportmember> rms) {
		List<ManagerMemberDTO> mmds = new ArrayList<>();

		for (Reportmember rm : rms) {
			ManagerMemberDTO mmd = new ManagerMemberDTO();
			mmd.setReportMember_id(rm.getReportmember_id());
			mmd.setMember_id(rm.getMember().getMember_id());
			mmd.setMember_nickname(rm.getMember().getMember_nickname());
			int report_count = reportMemberService.countByMemberId(mmd.getMember_id());
			mmd.setReport_count(report_count);
			mmd.setReport_content(rm.getReport_content());
			
			mmds.add(mmd);
		}
		return mmds;
	}

	// 관리자 아이디 비밀번호에 성공해서 로그인했는지 확인
	public boolean checkManagerLogin(HttpSession session) {
		return session.getAttribute("managerLogin") == "yes";
	}

	// 1. 관리자 페이지 로그인화면
	@GetMapping("/manager/login")
	public String viewManagerLogin(HttpSession session) {
		return "/manager/login/login";
	}

	// 로그인 정보가 일치할때 사용자관리 페이지로 리다이렉트
	@PostMapping("/manager/login")
	public String viewManagerLoginSubmit(HttpSession session) {
		session.setAttribute("managerLogin", "yes"); // 세션에 관리자가 로그인함을 기록

		return "redirect:/manager/user"; // 컨트롤러호출하는이게 맞나?
	}

	// 로그아웃시 호출, 로그아웃처리
	@GetMapping("/manager/logout")
	public String logoutManager(HttpSession session) {
		session.removeAttribute("managerLogin"); // 세션에서 관리자 로그인을 지움
		return "redirect:/"; // 로그아웃시 다시 메인
	}

	// 2. 댓글 신고 관리 페이지
	@GetMapping("/manager/comment")
	public String viewManagerComment(Model model, HttpSession session) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";
		// 최신순으로 정렬해서 가져옴
		List<Reportreply> replys = reportreplyService.findAllSortByUpdateDate();
		List<ManagerCommentDTO> mcds = getMangerCommentDTO(replys);

		model.addAttribute("mcds", mcds);
		return "/manager/comment/comment";
	}

	// 2-1. 검색결과를 비동기 통신으로 전달
	@GetMapping("/manager/comment/{sort}")
	public String asyncRecent(@PathVariable("sort") String sort, HttpSession session, @RequestParam("text") String text,
			Model model) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";

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

	// 2-2. 삭제 결과를 비동기 통신으로 전달
	@PostMapping("/manager/comment/delete/delete")
	public String asyncDelete(@RequestBody Map<String, Object> data, Model model, HttpSession session) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";

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

		// 다시 페이지를 보여줌
		List<Reportreply> replys = new ArrayList<>();
		replys = reportreplyService.findAll();
		List<ManagerCommentDTO> mcds = getMangerCommentDTO(replys);

		model.addAttribute("mcds", mcds);
		return "/manager/comment/comment :: #commentList";
	}

	// 3. 사용자 관리 페이지
	@GetMapping("/manager/user")
	public String viewManagerUser(HttpSession session, Model model) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";

		List<Reportmember> rms = reportMemberService.findAll();
		List<ManagerMemberDTO> mmds = getManagerMemberDTO(rms);

		model.addAttribute("mmds", mmds);
		return "/manager/user/user";
	}

	// 검색 결과
	@GetMapping("/manager/user/search")
	public String asyncUserSearch(HttpSession session, @RequestParam("text") String text, Model model) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";

		// 전달받은 text검색어로 검색
		List<Reportmember> rms = reportMemberService.findAllByMemberNickname(text);
		List<ManagerMemberDTO> mmds = getManagerMemberDTO(rms);

		model.addAttribute("mmds", mmds);
		return "/manager/user/user :: #userList";
	}

	// 삭제 결과
	@PostMapping("/manager/user/delete/delete")
	public String asyncUserDelete(@RequestBody Map<String, Object> data, Model model, HttpSession session) {
		if (!checkManagerLogin(session))
			return "redirect:/manager/login";

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
			reportMemberService.deleteById(report_id); //실제 삭제 진행
		}

		// 다시 페이지 로딩
		List<Reportmember> rms = reportMemberService.findAll();
		List<ManagerMemberDTO> mmds = getManagerMemberDTO(rms);

		model.addAttribute("mmds", mmds);
		return "/manager/user/user :: #userList";
	}

}

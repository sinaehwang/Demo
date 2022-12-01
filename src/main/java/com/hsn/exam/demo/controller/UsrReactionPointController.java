package com.hsn.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ReactionPointService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	
	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reactionPoint/doGoodReaction") // 해당value url로 접속이 들어오면 매핑해줌(기본RequsetMethod.방식은 get방식)
	@ResponseBody // 요청들어온거에 대해 응답결과를 Body에 넣어서 보내줌(RequestBoby는 요청하는 요청본문을 말함)
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri,HttpServletRequest req, HttpSession session) {// 리액션포인트 테이블의 좋아요가 증가하는 결과를 보내줘야함
		// replaceUri는 detail에서 좋아요후에 페이지를 Body에 응답결과로 보여주기 위해 파라미터를 받아옴
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		reactionPointService.doGoodReaction(loginedMemberId, relTypeCode, relId);

		
		return Ut.msgAndReplace("좋아요!실행", replaceUri);

	}
	
	@RequestMapping("/usr/reactionPoint/CancleGoodReaction")
	@ResponseBody
	public String CancleGoodReaction(String relTypeCode, int relId, String replaceUri,HttpSession session) {
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		reactionPointService.decreaseGoodReaction(loginedMemberId, relTypeCode, relId);

		return Ut.msgAndReplace("좋아요!취소실행", replaceUri);

	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri,HttpSession session) {
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		reactionPointService.doBadReaction(loginedMemberId, relTypeCode, relId);
		

		return Ut.msgAndReplace("싫어요!실행", replaceUri);

	}

	@RequestMapping("/usr/reactionPoint/CanCancelBadReaction")
	@ResponseBody
	public String CanCancelBadReaction(String relTypeCode, int relId, String replaceUri,HttpSession session) {
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		reactionPointService.decreaseBadReaction(loginedMemberId, relTypeCode, relId);

		return Ut.msgAndReplace("싫어요!취소실행", replaceUri);

	}

}

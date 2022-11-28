package com.hsn.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.service.ReplyService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Reply;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UsrReplyController {
	
	@Autowired
    private ArticleService articleService;
    @Autowired
    private ReplyService replyService;

    @RequestMapping("/usr/reply/doWrite")
    public String showWrite(HttpServletRequest req, String relTypeCode, int relId, String body, String redirectUri) {
        switch ( relTypeCode ) {
            case "article":
                Article article = articleService.getArticleById(relId);
                if ( article == null ) {
                    return Ut.msgAndBack(req, "해당 게시물이 존재하지 않습니다.");
                }
                break;
            default:
                return Ut.msgAndBack(req, "올바르지 않은 relTypeCode 입니다.");
        }

        Rq rq = (Rq)req.getAttribute("rq");

        int memberId = rq.getLoginedMemberId();

        ResultData writeResultData = replyService.write(relTypeCode, relId, memberId, body);
        
        int newReplyId = (int) writeResultData.getData1();
        
        redirectUri = Ut.getNewUri(redirectUri, "focusReplyId", newReplyId + "");

        return Ut.msgAndReplace(req, writeResultData.getMsg(), redirectUri);
    }
    
    @RequestMapping("/usr/reply/doDelete")
    public String doDelete(HttpServletRequest req,int id,String redirectUri) {
    	
    	Reply reply = replyService.getReplyById(id);
    	
    	if(reply == null) {
    		return Ut.msgAndBack(req, "해당 댓글은 존재하지 않습니다.");
    	}
    	
    	Rq rq = (Rq)req.getAttribute("rq");
    	
    	if(reply.getMemberId() != rq.getLoginedMemberId()) {
    		return Ut.msgAndBack("해당 댓글에 대해 권한이 없습니다.");
    	}
    	
    	ResultData deleteResultData = replyService.delete(id);
    	
    	return Ut.msgAndReplace(req, deleteResultData.getMsg(), redirectUri);
    }

}

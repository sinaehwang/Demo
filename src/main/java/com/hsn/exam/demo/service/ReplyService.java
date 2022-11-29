package com.hsn.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReplyRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Reply;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	

	public ResultData write(String relTypeCode, int relId, int memberId, String body) {
		replyRepository.write(relTypeCode, relId, memberId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", "댓글이 작성되었습니다.", "id", id);
	}


	public List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(String relTypeCode, int relId) {
		return replyRepository.getForPrintRepliesByRelTypeCodeAndRelId(relTypeCode,relId);
	}


	public Reply getReplyById(int id) {
        return replyRepository.getReplyById(id);
    }


	public ResultData delete(int id) {
		
		replyRepository.delete(id);

        return  ResultData.from("S-1", Ut.f("%d번댓글 삭제완료했습니다.", id), "id", id);
    }


	public ResultData modify(int id, String body) {
		
		replyRepository.modify(id,body);
		
		return ResultData.from("S-1", Ut.f("%d번댓글 수정완료했습니다.", id), "id", id);
	}




}

package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReplyRepository;
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

}

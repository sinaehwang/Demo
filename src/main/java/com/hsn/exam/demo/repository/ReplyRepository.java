package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	public void write(String relTypeCode, int relId, int memberId, String body);

	public int getLastInsertId();

	public List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(String relTypeCode, int relId);

	public Reply getReplyById(int id);

	public void delete(int id);

}

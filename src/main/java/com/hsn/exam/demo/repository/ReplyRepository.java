package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyRepository {

	public void write(String relTypeCode, int relId, int memberId, String body);

	public int getLastInsertId();

}

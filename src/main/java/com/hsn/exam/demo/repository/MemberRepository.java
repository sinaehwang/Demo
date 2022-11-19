package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {

	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email);

	public int getLastInsertId();

	public Member getMember(int id);

	public int isDuplicateLoginId(String loginId);

	public int getDuplecateEmailAndName(String name, String email);

	public Member FoundMemberByLoginId(String loginId);

	public Member getMemberByNameAndEmail(String name, String email);

	


}

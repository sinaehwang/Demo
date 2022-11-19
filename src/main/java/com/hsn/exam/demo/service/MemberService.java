package com.hsn.exam.demo.service;

import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.MemberRepository;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member getMember(int id) {

		Member member = memberRepository.getMember(id);

		return member;
	}

	public ResultData Login(String loginId, String loginPw) {

		// 로그인 아이디로 존재하는 회원이있는지,아이디존재하면 비밀번호가 일치하는지 확인 비밀번호까지 일치한다면 로그인 성공메세지리턴
		Member FoundMemberByLoginId = memberRepository.FoundMemberByLoginId(loginId);

		if (FoundMemberByLoginId == null) {
			return ResultData.from("F-1", "아이디와 일치하는 회원정보가 없습니다.");
		}

		if (FoundMemberByLoginId.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-2", "입력하신 비밀번호가 일치하지 않습니다.");
		}

		return ResultData.from("S-1", "로그인 성공", "FoundMemberByLoginId", FoundMemberByLoginId);
	}

	public Member getMemberByLoginId(String loginId) {

		return memberRepository.FoundMemberByLoginId(loginId);

	}

	public Member getMemberByNameAndEmail(String name, String email) {
		
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public ResultData join(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);

	}

}

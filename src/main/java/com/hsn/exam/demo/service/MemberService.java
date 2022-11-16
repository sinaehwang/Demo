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

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		
		int isDuplicateLoginId	= memberRepository.isDuplicateLoginId(loginId);
		
		if(isDuplicateLoginId>0) { //0보다 크다는것은 중복되는 아이디가 있다는 뜻이기 때문에
		
			return 0;
		}
		
		isDuplicateLoginId = memberRepository.getDuplecateEmailAndName(name, email); //이름과 이메일중복여부체크
		
		if(isDuplicateLoginId > 0) {
			
			return -1;
		}
		
		memberRepository.join(loginId,loginPw,name,nickname,cellphoneNo,email); //db에 데이터insert하고
		
		int id = memberRepository.getLastInsertId(); //실행된 쿼리db에서 마지막으로 insert된 id를 가져오면 입력된 데이터를 불러올수있음
		
		return id;
	}

	public Member getMember(int id) {

		Member member = memberRepository.getMember(id);
		
		return member;
	}

	public ResultData Login(String loginId, String loginPw) {

		//로그인 아이디로 존재하는 회원이있는지,아이디존재하면 비밀번호가 일치하는지 확인 비밀번호까지 일치한다면 로그인 성공메세지리턴
		Member FoundMemberByLoginId = memberRepository.FoundMemberByLoginId(loginId);
		
		if(FoundMemberByLoginId == null) {
			return ResultData.from("F-1","아이디와 일치하는 회원정보가 없습니다.");
		}
		
		if(FoundMemberByLoginId.getLoginPw().equals(loginPw)==false) {
			return ResultData.from("F-2","입력하신 비밀번호가 일치하지 않습니다.");
		}
		
		return ResultData.from("S-1","로그인 성공","FoundMemberByLoginId", FoundMemberByLoginId);
	}


}
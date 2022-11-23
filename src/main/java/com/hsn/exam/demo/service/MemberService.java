package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.MemberRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MailService mailService;

	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	
	@Value("${custom.siteName}")
	private String siteName;

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

	public Member getMemberById(int id) {
		return memberRepository.getMember(id);
	}

	public ResultData notifyTempLoginPwByEmail(Member actor) {
		String title = "[" + siteName + "] 임시 패스워드 발송";
        String tempPassword = Ut.getTempPassword(6);
        String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
        body += "<a href=\"" + siteMainUri + "/usr/member/login\" target=\"_blank\">로그인 하러가기</a>";

        ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

        if (sendResultData.isFail()) {
            return sendResultData;
        }
        
        tempPassword = Ut.sha256(tempPassword);

        setTempPassword(actor, tempPassword);

        return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}

	private void setTempPassword(Member actor, String tempPassword) {
		
		memberRepository.modify(actor.getId(), tempPassword, null, null, null, null);
    }

	public ResultData modify(int id, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		
		memberRepository.modify(id, loginPw, name, nickname, cellphoneNo, email);

        return ResultData.from("S-1", "회원정보가 수정되었습니다.", "id", id);
	}

}

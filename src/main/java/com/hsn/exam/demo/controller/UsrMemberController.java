package com.hsn.exam.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.hsn.exam.demo.service.GenFileService;
import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {

	// 전역변수를 만들어줌

	@Autowired
	private MemberService memberService;

	@Autowired
	private GenFileService genFileService;

	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/join")
	public String join() {

		return "usr/member/join";

	}

	@RequestMapping("/usr/member/mypage")
	public String showMypage(HttpServletRequest req) {

		return "usr/member/mypage";
	}

	@RequestMapping("/usr/member/checkPassword")
	public String showCheckPassword(HttpServletRequest req) {
		return "usr/member/checkPassword";
	}

	@RequestMapping("/usr/member/doCheckPassword")
	public String doCheckPassword(HttpServletRequest req, String loginPw, String redirectUri) {

		Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();

		if (loginedMember.getLoginPw().equals(loginPw) == false) {
			return Ut.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
		}

		// 비밀번호 인증코드생성
		String authCode = memberService.genCheckPasswordAuthCode(loginedMember.getId());

		redirectUri = Ut.getNewUri(redirectUri, "checkPasswordAuthCode", authCode);

		return Ut.msgAndReplace(req, "", redirectUri);
	}

	@RequestMapping("/usr/member/modify")
	public String modify(HttpServletRequest req, String checkPasswordAuthCode) {

		Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();

		// 멤버가가지고있는 비밀번호인증코드 유효성을 체크

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckPasswordAuthCode(loginedMember.getId(), checkPasswordAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			return Ut.msgAndBack(req, checkValidCheckPasswordAuthCodeResultData.getMsg());
		}

		return "usr/member/modify";

	}

	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpServletRequest req, String loginPw, String name, String nickname, String cellphoneNo,
			String email, String checkPasswordAuthCode, MultipartRequest multipartRequest) {

		Member loginedMember = ((Rq) req.getAttribute("rq")).getLoginedMember();

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckPasswordAuthCode(loginedMember.getId(), checkPasswordAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			return Ut.msgAndBack(req, checkValidCheckPasswordAuthCodeResultData.getMsg());
		}

		if (loginPw != null && loginPw.trim().length() == 0) {
			loginPw = null;
		}

		int id = ((Rq) req.getAttribute("rq")).getLoginedMemberId();

		ResultData modifyRd = memberService.modify(id, loginPw, name, nickname, cellphoneNo, email);

		if (modifyRd.isFail()) {
			return Ut.msgAndBack(req, modifyRd.getMsg());
		}
		
		if ( req.getParameter("deleteFile__member__0__extra__profileImg__1") != null ) {
			genFileService.deleteGenFile("member", loginedMember.getId(), "extra", "profileImg", 1);
        }
		

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, loginedMember.getId());
			}
		}

		return Ut.msgAndReplace(req, modifyRd.getMsg(), "/");
	}

	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId를 입력해주세요");
		}

		if (Ut.allNumberString(loginId)) {
			return ResultData.from("F-2", "로그인아이디는 숫자만으로 구성될수 없습니다.");
		}

		if (Ut.startWithNumberString(loginId)) {
			return ResultData.from("F-3", "로그인아이디는 숫자로 시작될수 없습니다.");
		}

		if (loginId.length() < 5) {
			return ResultData.from("F-4", "로그인아이디를 5글자 이상 입력해주세요");
		}

		if (loginId.length() > 20) {
			return ResultData.from("F-5", "로그인아이디를 20글자 이하로 입력해주세요");
		}

		if (Ut.isStandardLoginIdString(loginId) == false) {
			return ResultData.from("F-6", "로그인아이디는 영문과 숫자의 조합으로 구성되어야 합니다.");
		}

		Member existMember = memberService.getMemberByLoginId(loginId);

		if (existMember != null) {
			return ResultData.from("F-7", Ut.f("%s는 이미 사용중인 아이디입니다.", loginId));
		}

		return ResultData.from("S-1", Ut.f("%s는 사용가능한 아이디입니다.", loginId), "loginId", loginId);
	}

	@RequestMapping("/usr/member/doJoin") // 브라우저요청으로 글을 추가하는경우
	public String dojoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickname,
			String cellphoneNo, String email, MultipartRequest multipartRequest) {// form 안에 모든 input파일을 가져옴

		Member oldMember = memberService.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return Ut.msgAndBack(req, loginId + "(은)는 이미 사용중인 로그인아이디 입니다.");
		}

		oldMember = memberService.getMemberByNameAndEmail(name, email);

		if (oldMember != null) {
			return Ut.msgAndBack(req,
					String.format("%s님은 이미 %s 메일주소로 %s 에 가입하셨습니다.", name, email, oldMember.getRegDate()));
		}

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			return Ut.msgAndBack(req, joinRd.getMsg());
		}

		int newMemberId = (int) joinRd.getData1();

		// 회원가입후에 프로필이미지파일이 생성되야함
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {

			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, newMemberId);// 젠파일에 멤버번호와 파일저장

			}
		}
		
		String redirectUri = "/usr/member/login";

		return Ut.msgAndReplace(req, joinRd.getMsg(), redirectUri);

	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {

		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	public String doLogin(HttpServletRequest req, HttpSession session, String loginId, String loginPw,
			String redirectUri) {

		if (Ut.empty(redirectUri)) {
			redirectUri = "/";
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.msgAndBack(req, loginId + "(은)는 존재하지 않는 로그인아이디 입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {

			return Ut.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
		}

		String msg = Ut.f("%s님 로그인되었습니다.", member.getLoginId());

		session.setAttribute("loginedMemberId", member.getId());

		// 임시비번사용하고있는지 체크
		boolean isUsingTempPassword = memberService.isUsingTempPassword(member.getId());

		if (isUsingTempPassword) {
			msg = "임시 비밀번호를 사용중입니다. 비밀번호를 변경해주세요.";
			redirectUri = "/usr/member/mypage";
		}

		return Ut.msgAndReplace(req, msg, redirectUri);

	}

	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpServletRequest req, HttpSession session) {

		session.removeAttribute("loginedMemberId");

		String msg = "로그아웃 되었습니다.";
		return Ut.msgAndReplace(req, msg, "/");
	}

	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId(HttpServletRequest req) {
		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	public String doFindLoginId(HttpServletRequest req, String name, String email, String redirectUri) {
		if (Ut.empty(redirectUri)) {
			redirectUri = "/";
		}

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		return Ut.msgAndBack(req, Ut.f("회원님의 사용중인 아이디는 [%s] 입니다.", member.getLoginId()));
	}

	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw(HttpServletRequest req) {
		return "usr/member/findLoginPw";
	}

	@RequestMapping("/usr/member/doFindLoginPw")
	public String doFindLoginPw(HttpServletRequest req, String loginId, String name, String email, String redirectUri) {
		if (Ut.empty(redirectUri)) {
			redirectUri = "/";
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		if (member.getName().equals(name) == false) {
			return Ut.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		if (member.getEmail().equals(email) == false) {
			return Ut.msgAndBack(req, "일치하는 회원이 존재하지 않습니다.");
		}

		ResultData notifyTempLoginPwByEmailRs = memberService.notifyTempLoginPwByEmail(member);

		return Ut.msgAndReplace(req, notifyTempLoginPwByEmailRs.getMsg(), redirectUri);
	}
	
	
	@RequestMapping("/auth/kakao/calback")//카카로API를 통해 로그인이 인증되어 인증코드를 받게됨
	@ResponseBody
	public String kakaocalback(@RequestParam String code) {
		
		//POST방식으로 key=value데이터를 카카오에 요청하기 위해 RestTemplate사용
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeaders 오브젝트생성
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트생성
		MultiValueMap<String, String>params = new LinkedMultiValueMap<>();
		
		params.add("grant_type", "authorization_code");
		params.add("client_id", "b6d266ed8e0ac2b0c97c846dc1bcc5bc");
		params.add("redirect_uri", "http://localhost:8082/auth/kakao/calback");
		params.add("code", code);
		
		////HttpHeaders와 HttpBody를 하나의 오브젝트에 담는다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
		
		//POST방식으로 http요청, response변수의 응답을 받음
		ResponseEntity<String>response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				);
		
		return "카카오토큰요청완료:토큰요청에대한 응답"+ response;
		
	}

}

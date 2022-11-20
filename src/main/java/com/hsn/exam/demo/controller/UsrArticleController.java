package com.hsn.exam.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.service.GenFileService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;
import com.hsn.exam.demo.vo.GenFile;
import com.hsn.exam.demo.vo.Req;
import com.hsn.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	// 전역변수를 만들어줌

	@Autowired
	private ArticleService articleService;// Service랑 연결됨

	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId) {

		Req rq = (Req) req.getAttribute("req");

		if (rq.isNotLogined()) {
			return Ut.msgAndBack(req, "로그인 후 이용해주세요.");
		}

		Board board = articleService.getBoardbyId(boardId);

		req.setAttribute("board", board);

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite") // 브라우저요청으로 글을 추가하는경우
	public String doWrite(@RequestParam Map<String, Object> param, HttpServletRequest req,
			MultipartRequest multipartRequest) {

		Req rq = (Req) req.getAttribute("req");

		if (rq.isNotLogined()) {
			return Ut.msgAndBack(req, "로그인 후 이용해주세요.");
		}

		if (param.get("title") == null) {
			// return ResultData.from("F-1", "title을 입력해주세요");
			return Ut.msgAndBack(req, "제목을 입력해주세요");
		}

		if (param.get("body") == null) {
			// return ResultData.from("F-2", "body을 입력해주세요");
			return Ut.msgAndBack(req, "내용을 입력해주세요");
		}

		// 임시
		int loginedMemberId = 1;

		param.put("memberId", loginedMemberId);

		ResultData writeArticlerd = articleService.writeArticle(param);// data1에 id를 저장해서 리턴해준상태

		// Article article =
		// articleService.getArticle((int)writeArticlerd.getData1());//data1으로 새로운 data를
		// 찾은상태

		ResultData article = articleService.getArticle((int) writeArticlerd.getData1());

		if (article.isFail()) {
			// return ResultData.from(article.getResultCode(), article.getMsg());
			return Ut.msgAndBack(req, article.getMsg());
		}

		Article writearticle = (Article) article.getData1();

		int newArticleId = writearticle.getId();

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				ResultData saverd = genFileService.save(multipartFile, newArticleId);
			}
		}

		String replaceUrl = "detail?id=" + newArticleId;

		return Ut.msgAndReplace(req, Ut.f("%d번째 게시글이 작성되었습니다.", newArticleId), replaceUrl);

		// return msgAndBack(req, "성공");

		// return ResultData.newData(writeArticlerd, "article", article.getData1());

	}

	@RequestMapping("/usr/article/detail")
	public String detail(HttpServletRequest req, int id) {

		ResultData articlerd = articleService.getArticle(id);

		if (articlerd.isFail()) {
			return Ut.msgAndBack(req, articlerd.getMsg());
		}

		Article article = (Article) articlerd.getData1();

		Board board = articleService.getBoardbyId(article.getBoardId());

		req.setAttribute("article", article);
		req.setAttribute("board", board);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/list")
	public String getArticles(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page, String searchKeywordType, String searchKeyword) {

		Board board = articleService.getBoardbyId(boardId);

		if (Ut.empty(searchKeywordType)) {
			searchKeywordType = "titleAndBody";
		}

		if (board == null) {

			return Ut.msgAndBack(req, boardId + "번 게시판은 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeyword, searchKeywordType);

		if (searchKeyword == null || searchKeyword.trim().length() == 0) {

		}

		req.setAttribute("totalItemsCount", totalItemsCount);

		// 한 페이지에 보여줄 수 있는 게시물 최대 개수
		int itemsCountInAPage = 6;
		// 총 페이지 수
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);

		// 현재 페이지(임시)
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);

		List<Article> articles = articleService.getArticles(boardId, itemsCountInAPage, page, searchKeyword,
				searchKeywordType);

		for (Article article : articles) {
			GenFile genFile = genFileService.getGenFile("article", article.getId(), "common", "attachment", 1);

			if (genFile != null) {

				article.setExtra__thumbImg(genFile.getForPrintUrl());

			}
		}

		req.setAttribute("articles", articles);

		return "usr/article/list";

	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody()

	public ResultData getArticle(int id) {

		ResultData article = articleService.getArticle(id);

		if (article.isFail()) {

			return ResultData.from(article.getResultCode(), article.getMsg());
		}

		return ResultData.from(article.getResultCode(), article.getMsg(), "article", article.getData1());
	}

	@RequestMapping("/usr/article/doDelete")
	public String doDelete(int id, HttpSession httpSession, HttpServletRequest req) {

		/*
		 * boolean isLogined = false;
		 * 
		 * int loginedMemberId = 0;
		 * 
		 * if (httpSession.getAttribute("loginedMemberId") != null) { isLogined = true;
		 * loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); }
		 * 
		 * if (isLogined == false) { // return ResultData.from("F-A", "로그인 후 이용해주세요");
		 * return msgAndBack(req, "로그인 후 이용해주세요"); }
		 */

		ResultData Foundarticle = articleService.getArticle(id);

		if (Foundarticle.isFail()) {

			// return ResultData.from(Foundarticle.getResultCode(), Foundarticle.getMsg());

			return Ut.msgAndBack(req, Foundarticle.getMsg());

		}

		Article article = (Article) Foundarticle.getData1();

		// 임시
		int loginedMemberId = 1;

		if (article.getMemberId() != loginedMemberId) {

			// return ResultData.from("F-1", "해당 게시글에 대해 삭제권한이 없습니다.");

			return Ut.msgAndBack(req, "해당 게시글에 대해 삭제권한이 없습니다.");

		}

		articleService.doDelete(id);

		String redirectUrl = "../article/list?boardId=" + article.getBoardId();

		// return ResultData.from("S-1", Ut.f("%d번게시글 삭제완료.", id));

		return Ut.msgAndReplace(req, Ut.f("%d번 게시글삭제완료", id), redirectUrl);

	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Integer id, HttpServletRequest req) {

		if (id == null) {
			return Ut.msgAndBack(req, "id를 입력해주세요.");
		}

		Article article = articleService.getForPrintArticle(id);

		Board board = articleService.getBoardbyId(article.getBoardId());

		List<GenFile> files = genFileService.getGenFiles("article", article.getId(), "common", "attachment");

		Map<String, GenFile> filesMap = new HashMap<>();

		for (GenFile file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		article.getExtraNotNull().put("file__common__attachment", filesMap);
		req.setAttribute("article", article);

		if (article == null) {
			return Ut.msgAndBack(req, "존재하지 않는 게시물번호 입니다.");
		}

		req.setAttribute("article", article);
		req.setAttribute("board", board);

		return "usr/article/modify";

	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody()
	public ResultData doModify(int id, String title, String body, HttpSession httpSession) {// return타입을 String과 Article
																							// 둘다사용하기위해 Object로변경해줌

		/*
		 * boolean isLogined = false;
		 * 
		 * int loginedMemberId = 0;
		 * 
		 * if (httpSession.getAttribute("loginedMemberId") != null) { isLogined = true;
		 * loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); }
		 * 
		 * if (isLogined == false) { return ResultData.from("F-A", "로그인 후 이용해주세요"); }
		 */

		ResultData ModifyArticle = articleService.getArticle(id);

		if (ModifyArticle.isFail()) {
			return ResultData.from(ModifyArticle.getResultCode(), ModifyArticle.getMsg());
		}

		Article article = (Article) ModifyArticle.getData1();

		/*
		 * if (article.getMemberId() != loginedMemberId) {
		 * 
		 * return ResultData.from("F-1", "해당 게시글에 대해 수정권한이 없습니다."); }
		 */

		ResultData ModifyArticlerd = articleService.doModify(id, title, body);

		return ResultData.from(ModifyArticlerd.getResultCode(), ModifyArticlerd.getMsg(), "ModifyArticlerd",
				ModifyArticlerd.getData1());

	}

}

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
import com.hsn.exam.demo.service.ReactionPointService;
import com.hsn.exam.demo.service.ReplyService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;
import com.hsn.exam.demo.vo.GenFile;
import com.hsn.exam.demo.vo.Reply;
import com.hsn.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	// 전역변수를 만들어줌
	
	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private ArticleService articleService;// Service랑 연결됨

	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId) {

		Board board = articleService.getBoardbyId(boardId);

		req.setAttribute("board", board);

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/DataSearch")
	public String search() {

		return "usr/article/search";
	}

	@RequestMapping("/usr/article/doWrite") // 브라우저요청으로 글을 추가하는경우
	public String doWrite(@RequestParam Map<String, Object> param, HttpServletRequest req, HttpSession session,
			MultipartRequest multipartRequest) {

		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		if (loginedMemberId == 0) {
			return Ut.msgAndBack(req, "로그인후 이용해주세요");
		}

		if (param.get("title") == null) {
			return Ut.msgAndBack(req, "제목을 입력해주세요");
		}

		if (param.get("body") == null) {
			return Ut.msgAndBack(req, "내용을 입력해주세요");
		}
		if (param.get("catergoryId") == null) {
			return Ut.msgAndBack(req, "카테고리를 입력해주세요");
		}

		param.put("memberId", loginedMemberId);

		ResultData writeArticlerd = articleService.writeArticle(param);// data1에 id를 저장해서 리턴해준상태

		ResultData article = articleService.getArticle((int) writeArticlerd.getData1());

		if (article.isFail()) {

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

		String replaceUri = "detail?id=" + newArticleId;

		return Ut.msgAndReplace(req, Ut.f("%d번째 게시글이 작성되었습니다.", newArticleId), replaceUri);

	}

	@RequestMapping("/usr/article/detail")
	public String detail(HttpServletRequest req, int id, HttpSession session) {
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		ResultData articlerd = articleService.getArticle(id);

		if (articlerd.isFail()) {
			return Ut.msgAndBack(req, articlerd.getMsg());
		}

		Article article = (Article) articlerd.getData1();
		
		
		List<Reply>replies = replyService.getForPrintRepliesByRelTypeCodeAndRelId("article", id);
		
		req.setAttribute("replies", replies);
		
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(loginedMemberId,"article", id);
		
		req.setAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		
		req.setAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSucess());
		
		
		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {// 기존추천버튼을 취소먼저해야하는경우의수

			int checkButton = (int) actorCanMakeReactionRd.getData1(); //기존선택버튼이 좋아요인지 싫어요인지 구분하기위해서

			if (checkButton > 0) {

				req.setAttribute("actorCanCancelGoodReaction", true);// 좋아요버튼을 먼저취소해야하는경우
			} 
			
			else  {
				
				req.setAttribute("actorCanCancelBadReaction", true);// 싫어요버튼을 먼저취소해야하는경우
			}
		}

		if (actorCanMakeReactionRd.getResultCode().equals("F-1")) {//추천버튼을 누를수없는경우의수(로그인을 하지 않은경우)

			return Ut.msgAndBack(req, actorCanMakeReactionRd.getMsg());

		}

		
		List<GenFile> files = genFileService.getGenFiles("article", article.getId(), "common", "attachment");
		
		Map<String, GenFile> filesMap = new HashMap<>();

		for (GenFile file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		article.getExtraNotNull().put("file__common__attachment", filesMap);
		
		req.setAttribute("article", article);
		
		Board board = articleService.getBoardbyId(article.getBoardId());
		
		req.setAttribute("board", board);
		
		if (article == null) {
            return Ut.msgAndBack(req, id + "번 게시물이 존재하지 않습니다.");
        }
		
		
		
		



		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/list")
	public String getArticles(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "0") String catergoryType, @RequestParam(defaultValue = "1") int page,
			String searchKeywordType, String searchKeyword) {

		Board board = articleService.getBoardbyId(boardId);

		if (Ut.empty(searchKeywordType)) {
			searchKeywordType = "titleAndBody";
		}

		if (board == null) {

			return Ut.msgAndBack(req, boardId + "번 게시판은 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		int catergoryId = Integer.parseInt(catergoryType);

		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeyword, searchKeywordType,
				catergoryId);

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
				searchKeywordType, catergoryId);

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
	public String doDelete(int id, HttpSession httpSession, HttpServletRequest req, HttpSession session ) {
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		ResultData Foundarticle = articleService.getArticle(id);

		if (Foundarticle.isFail()) {

			return Ut.msgAndBack(req, Foundarticle.getMsg());

		}

		Article article = (Article) Foundarticle.getData1();

		ResultData actorCanDeleteRd  = articleService.getActorCanDeleteRd(article, loginedMemberId);
		
		if (actorCanDeleteRd.isFail()) {
			
			return Ut.msgAndBack(req, actorCanDeleteRd.getMsg());
		}
		
		articleService.doDelete(id);

		String redirectUri = "../article/list?boardId=" + article.getBoardId();

		return Ut.msgAndReplace(req, Ut.f("%d번 게시글삭제완료", id), redirectUri);

	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Integer id, HttpServletRequest req, HttpSession session) {

		if (id == null) {
			return Ut.msgAndBack(req, "id를 입력해주세요.");
		}
		
		int loginedMemberId = Ut.getAsInt(session.getAttribute("loginedMemberId"), 0);

		Article article = articleService.getForPrintArticle(id);
		
		ResultData actorCanModifyRd  = articleService.getActorCanModifyRd(article, loginedMemberId);
		
		if (actorCanModifyRd.isFail()) {
			
			return Ut.msgAndBack(req, actorCanModifyRd.getMsg());
		}

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
	public String doModify(int id, String title, String body, HttpServletRequest req) {// return타입을 String과 Article
																						// 둘다사용하기위해 Object로변경해줌

		ResultData Articlerd = articleService.getArticle(id);

		if (Articlerd.isFail()) {
			return Ut.msgAndBack(req, Articlerd.getMsg());
		}

		Article ModifyArticle = articleService.doModify(id, title, body);

		String redirectUri = "../article/detail?id=" + ModifyArticle.getId();

		return Ut.msgAndReplace(req, Ut.f("%d번 게시글수정완료", id), redirectUri);

	}

}

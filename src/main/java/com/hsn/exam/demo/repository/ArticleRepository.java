package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;

@Mapper
public interface ArticleRepository {
	
	public Article getArticle(int id);

	public void writeArticle(String title, String body,int loginedMemberId);

	public int getLastInsertId();

	public List<Article> getArticles(int boardId, int limitFrom, int limitTake, String searchKeyword, String searchKeywordType);

	public void doDelete(int id);

	public void doModify(int id, String title, String body);

	public Board getBoardbyId(int boardId);

	public int getArticlesTotalCount(int boardId, String searchKeyword, String searchKeywordType);


}

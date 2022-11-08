package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	public Article getArticle(int id);

	public void writeArticle(String title, String body);

	public int getLastInsertId();

	public List<Article> getArticles();

	public void doDelete(int id);

	public void doModify(int id, String title, String body);


}

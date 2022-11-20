package com.hsn.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hsn.exam.demo.interceptor.BeforeActionInterceptor;
import com.hsn.exam.demo.interceptor.NeedToLoginInterceptor;
import com.hsn.exam.demo.interceptor.NeedToLogoutInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	@Autowired
	NeedToLoginInterceptor needToLoginInterceptor;

	@Autowired
	NeedToLogoutInterceptor needToLogoutInterceptor;

	@Value("${custom.genFileDirPath}")
	private String genFileDirPath;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**") // 모든들어오는 요청에대해 비포인터셉터를 실행하라는 명령
				.excludePathPatterns("/resource/**") // resource로 들어오는 요청에대해서는 비포인터셉터 실행을 제외하라는요청
				.excludePathPatterns("/error");

		registry.addInterceptor(needToLoginInterceptor).addPathPatterns("/usr/article/write")
				.addPathPatterns("/usr/article/doWrite").addPathPatterns("/usr/article/doDelete")
				.addPathPatterns("/usr/article/modify").addPathPatterns("/usr/article/doModify");

		registry.addInterceptor(needToLogoutInterceptor).addPathPatterns("/usr/member/login")
				.addPathPatterns("/usr/member/doLogin").addPathPatterns("/usr/member/join")
				.addPathPatterns("/usr/member/doJoin").addPathPatterns("/usr/member/findLoginId")
				.addPathPatterns("/usr/member/doFindLoginId").addPathPatterns("/usr/member/findLoginPw")
				.addPathPatterns("/usr/member/doFindLoginPw");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/gen/**").addResourceLocations("file:///" + genFileDirPath + "/")
				.setCachePeriod(20);
	}

}

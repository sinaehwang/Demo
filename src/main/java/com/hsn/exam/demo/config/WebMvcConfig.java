package com.hsn.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hsn.exam.demo.interceptor.BeforeActionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	
	
	@Value("${custom.genFileDirPath}")
	private String genFileDirPath;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
        registry.addInterceptor(beforeActionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/resource/**");
    }
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/gen/**").addResourceLocations("file:///" + genFileDirPath + "/")
				.setCachePeriod(20);
	}
	
	
	

}

package com.glowriters.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// HTML 파일을 정적으로 제공하기 위한 설정
    registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/templates/");

    // CSS 파일을 정적으로 제공하기 위한 설정
    registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/");
    
    registry.addResourceHandler("/images/**")
    				.addResourceLocations("classpath:/static/images/");

    registry.addResourceHandler("/js/**")
    				.addResourceLocations("classpath:/static/js/");
	}

}

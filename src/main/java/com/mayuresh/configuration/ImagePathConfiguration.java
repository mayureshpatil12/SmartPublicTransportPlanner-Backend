package com.mayuresh.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagePathConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		final String imagePath=System.getProperty("user.dir")+"/uploads/images/";
		registry.addResourceHandler("/profile-images/**")
		.addResourceLocations("file:"+imagePath);
	}
}

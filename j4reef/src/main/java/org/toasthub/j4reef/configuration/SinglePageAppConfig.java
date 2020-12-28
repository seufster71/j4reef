package org.toasthub.j4reef.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class SinglePageAppConfig implements WebMvcConfigurer {

	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry
	        .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.png", "/**/*.ttf", "/**/*.woff", "/**/*.woff2")
	        .setCachePeriod(0)
	        .addResourceLocations("classpath:/static/");

	      registry.addResourceHandler("/", "/**")
	        .setCachePeriod(0)
	        .addResourceLocations("classpath:/static/index.html")
	        .resourceChain(true)
	        .addResolver(new PathResourceResolver() {
	          @Override
	          protected Resource getResource(String resourcePath, Resource location) throws IOException {
	            if (resourcePath.startsWith("/api")) {
	              return null;
	            }

	            return location.exists() && location.isReadable() ? location : null;
	          }
	        });
	  }

}

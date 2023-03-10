package com.servicecity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.servicecity.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ServicecityServerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ServicecityServerApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ServicecityServerApplication.class);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
       return new WebMvcConfigurer() {
    	   @Override
           public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("*").allowedOrigins("*");
           }  
       };
            
   }

}

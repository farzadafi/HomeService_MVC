package com.example.HomeService_MVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class HomeServiceMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeServiceMvcApplication.class, args);
	}

	@Bean
	@ConditionalOnProperty(value = "spring.security.cors-origin.enabled", havingValue = "true")
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedMethod(HttpMethod.GET);
		corsConfiguration.addAllowedMethod(HttpMethod.POST);
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("authorization");
		corsConfiguration.addAllowedHeader("content-type");
		corsConfiguration.addAllowedHeader("cookie");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}

package jp.kobespiral.sandazerocarbonappbackend.cofigration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // .allowedOrigins("http://localhost:5173");
                .allowedOrigins("https://sanda-zero-carbon-app-yuyohi.vercel.app/");
    }
}

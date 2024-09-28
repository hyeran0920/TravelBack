package filminkorea.fik.configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnails/**","/layer/**", "/api/**")
                .addResourceLocations("file://C:/upload/","file://C:/layer/", "file://C:/photoImg/");

    }

    //맛집 api불러오기 용
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

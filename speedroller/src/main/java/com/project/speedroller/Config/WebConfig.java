package com.project.speedroller.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/corporativo/mision").setViewName("corporativo/mision");
        registry.addViewController("/corporativo/vision").setViewName("corporativo/vision");
        registry.addViewController("/corporativo/valores").setViewName("corporativo/valores");
        registry.addViewController("/servicios").setViewName("corporativo/servicios");
        registry.addViewController("/eventos").setViewName("corporativo/eventos");
    }
}

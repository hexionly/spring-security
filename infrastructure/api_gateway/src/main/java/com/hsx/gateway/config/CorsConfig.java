package com.hsx.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域配置
 *
 * @author HEXIONLY
 * @date 2022/3/8 16:20
 */
@Configuration
public class CorsConfig {

    /**
     * 解决跨域
     *
     * @return
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许访问 * 任意
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource configurationSource =
                new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 所有路径都允许访问
        configurationSource.registerCorsConfiguration("/**", configuration);

        return new CorsWebFilter(configurationSource);
    }
}

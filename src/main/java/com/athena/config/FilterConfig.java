package com.athena.config;

import org.springframework.context.annotation.Configuration;

/**
 * Filter配置
 *
 * @author Mr.sun
 */
@Configuration
public class FilterConfig {

   /* @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }*/
}

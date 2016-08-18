/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.config;

import com.ymatou.common.mybatis.interceptor.WebInterceptor;
import com.ymatou.datamonitor.exception.MySimpleMappingExceptionResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "com.ymatou",useDefaultFilters=false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class)
        })
public class WebConfig extends WebMvcConfigurerAdapter{

    protected final Log logger = LogFactory.getLog(getClass());


    /**
     * 全局异常处理
     * @return
     */
    @Bean(name="exceptionResolver")
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){

        MySimpleMappingExceptionResolver exceptionHandler= new MySimpleMappingExceptionResolver();

        return exceptionHandler;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new ErrorCustomizer();
    }

    private static class ErrorCustomizer implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage("/common/error.html"));
            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/common/400.html"));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/common/403.html"));
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/common/404.html"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/common/500.html"));
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*.html").addResourceLocations("classpath:/static/html/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/app/**").addResourceLocations("classpath:/static/app/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

//    @Override
//    protected void addFormatters(FormatterRegistry registry) {
//        // Add formatters and/or converters
//    }
//

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // Configure the list of HttpMessageConverters to use
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
//                Lists.newArrayList(
//                        MediaType.APPLICATION_JSON,
//                        MediaType.TEXT_HTML,
//                        MediaType.TEXT_PLAIN,
//                        new MediaType("","",new UTF())
//                        )
//        );
//
//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//
//        stringHttpMessageConverter.setSupportedMediaTypes(
//                Lists.newArrayList(
//                        MediaType.TEXT_HTML,
//                        MediaType.TEXT_PLAIN
//                )
//        );
//
//        converters.add(new ResourceHttpMessageConverter());
//        converters.add(mappingJackson2HttpMessageConverter);
//
//    }
//
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");
    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:index.html");
////        Resource page = this.resourceProperties.getWelcomePage();
////        if (page != null) {
////            logger.info("Adding welcome page: " + page);
////            registry.addViewController("/").setViewName("forward:index.html");
////        }
//
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.enableContentNegotiation(new MappingJackson2JsonView());
//        registry.jsp();
//    }


    @Configuration
    @ConditionalOnWebApplication
    @ConditionalOnClass({WebMvcConfigurerAdapter.class})
    @ConditionalOnMissingBean({OpenEntityManagerInViewInterceptor.class, OpenEntityManagerInViewFilter.class})
    @ConditionalOnProperty(
            prefix = "spring.jpa",
            name = {"open-in-view"},
            havingValue = "true",
            matchIfMissing = true
    )
    protected static class JpaWebConfiguration {
        protected JpaWebConfiguration() {
        }

        @Configuration
        protected static class JpaWebMvcConfiguration extends WebMvcConfigurerAdapter {
            protected JpaWebMvcConfiguration() {
            }

            @Bean
            public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
                return new OpenEntityManagerInViewInterceptor();
            }

            public void addInterceptors(InterceptorRegistry registry) {
                registry.addWebRequestInterceptor(this.openEntityManagerInViewInterceptor());
            }
        }
    }
}

package com.stylefeng.guns.rest.config;

import brave.spring.beans.TracingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-11-13 14:08
 */
@Configuration
public class TraceConfig {

    @Bean
    public TracingFactoryBean getTracingBean() {
        TracingFactoryBean tracingFactoryBean = new TracingFactoryBean();
        tracingFactoryBean.setLocalServiceName("gateway");
        tracingFactoryBean.setSpanReporter(AsyncReporter.create(OkHttpSender.create("http://106.14.148.237:9411/api/v2/spans")));

        return tracingFactoryBean;
    }
}

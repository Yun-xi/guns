package com.stylefeng.guns.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-11-05 16:21
 */
@Configuration
@ImportResource(locations = {"classpath:tcc-transaction.xml", "classpath:tcc-transaction-dubbo.xml"})
public class TCCConfig {
}

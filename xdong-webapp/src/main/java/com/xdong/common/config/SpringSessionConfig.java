package com.xdong.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "xdong", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}

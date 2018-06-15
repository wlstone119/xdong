package com.xdong.common.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

/**
 * 类TomcatConfig.java的实现描述：TODO 类实现描述
 * @author wanglei 2018年1月30日 下午3:31:01
 */
@Configuration
public class TomcatConfig {

    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.setPort(8081);
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
        return tomcatFactory;
    }
}

class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {

    public void customize(Connector connector) {
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        // 设置最大连接数
        protocol.setMaxConnections(2000);
        // 设置最大线程数
        protocol.setMaxThreads(2000);
        protocol.setConnectionTimeout(30000);
    }

}

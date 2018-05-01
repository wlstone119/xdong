package com.xdong.banner;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiPropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

/**
 * 类EndBanner.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月29日 下午10:30:24
 */
public class EndBanner {

    private static final Log    logger   = LogFactory.getLog(EndBanner.class);

    private static final String location = "classpath:bootimage/banner.txt";

    private Resource            resource;

    public EndBanner(){
        ResourceLoader resourceLoader = new DefaultResourceLoader(ClassUtils.getDefaultClassLoader());
        this.resource = resourceLoader.getResource(location);
    }

    public void printBanner(PrintStream out) {
        try {
            String banner = StreamUtils.copyToString(this.resource.getInputStream(), Charset.forName("UTF-8"));
            out.println(banner);
        } catch (Exception ex) {
            logger.warn("Banner not printable: " + this.resource + " (" + ex.getClass() + ": '" + ex.getMessage()
                        + "')", ex);
        }
    }

    protected String getBootVersion() {
        return SpringBootVersion.getVersion();
    }
}

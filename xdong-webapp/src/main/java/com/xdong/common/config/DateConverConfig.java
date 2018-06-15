package com.xdong.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类DateConverConfig.java的实现描述：TODO 类实现描述
 * @author wanglei 2018年6月15日 下午5:20:55
 */
@Configuration
public class DateConverConfig {

    @Bean
    public Converter<String, Date> stringDateConvert() {
        return new Converter<String, Date>() {

            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (Exception e) {
                    SimpleDateFormat sdfday = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = sdfday.parse((String) source);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
                return date;
            }
        };
    }

}

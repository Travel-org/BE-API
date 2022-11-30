package com.ssu.travel.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.ssu.travel.global.p6spy.CustomP6spySqlFormat;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {

    @PostConstruct
    public void setMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6spySqlFormat.class.getName());
    }
}

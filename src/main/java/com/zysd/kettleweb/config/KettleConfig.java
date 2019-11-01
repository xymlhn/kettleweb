package com.zysd.kettleweb.config;

import org.springframework.context.annotation.*;

@Configuration
public class KettleConfig {

    @Bean(name = "KettleEnvironmentInit")
    public KettleInit startInit(){
        return new KettleInit();
    }


}

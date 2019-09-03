package com.zysd.kettleweb.config;

import org.springframework.context.annotation.*;

@Configuration
public class SpringConfig {

    @Bean(name = "KettleEnvironmentInit")
    public KettleInit startInit(){
        return new KettleInit();
    }


}

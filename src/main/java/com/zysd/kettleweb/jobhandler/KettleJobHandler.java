package com.zysd.kettleweb.jobhandler;

import com.zysd.kettleweb.kettle.KettleService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 命令行任务
 *
 * @author xuxueli 2018-09-16 03:48:34
 */
@JobHandler(value="kettleJobHandler")
@Component
public class KettleJobHandler extends IJobHandler {
    @Autowired
    private KettleService kettleService;
    @Override
    public ReturnT<String> execute(String param) throws Exception {

        kettleService.runKjb(param,null,null);
        return ReturnT.SUCCESS;
    }

}

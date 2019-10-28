/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zysd.kettleweb.kettle;

import com.xxl.job.core.log.XxlJobLogger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.core.parameters.UnknownParamException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.RowAdapter;
import org.pentaho.di.trans.step.RowListener;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMetaDataCombi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * kettle主服务
 * @author cartman
 *
 */
@Service
public class KettleService {

    @Value(value = "${kettle.jobPath}")
    private String jobPath;

    /**
     * 执行作业
     * @param etlJobName 作业名称
     * @param parameters
     * @param variables
     * @throws Exception
     */
    public void runKjb(String etlJobName, Map<String, String> parameters, Map<String, String> variables) throws Exception {
        if ("".equals(etlJobName)) {
            XxlJobLogger.log("转换job名称为空");
            throw new Exception("Job name is empty!");
        } else {
            String completePath = jobPath + etlJobName;
            XxlJobLogger.log("Path: " + completePath);
            File f = new File(completePath);
            if (f.isFile()) {
                JobMeta jobMeta = new JobMeta(completePath, null);
                Job job = new Job(null, jobMeta);
                if (parameters != null) {
                    parameters.forEach((key, value) -> {
                        try {
                            job.setParameterValue(key, value);
                        } catch (UnknownParamException ex) {
                            Logger.getLogger(KettleService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
                if (variables != null) {
                    variables.forEach(job::setVariable);
                }
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                job.setVariable("actualDate", dateFormat.format(new Date()));
                job.setLogLevel(LogLevel.ERROR);
                job.start();

                job.waitUntilFinished();
                //读取kettle运行时日志写入xxl-job日志文件中
                String logChannelId = job.getLogChannelId();
                LoggingBuffer appender = KettleLogStore.getAppender();
                String logText = appender.getBuffer(logChannelId, true).toString();
                XxlJobLogger.log(logText);
                appender.clear();
                if (job.isFinished() && job.getErrors() == 0) {
                    XxlJobLogger.log(etlJobName + "----- 转换成功");
                } else {
                    XxlJobLogger.log(etlJobName + "转换失败");
                    throw new Exception("转换失败");

                }
            } else {
                XxlJobLogger.log(etlJobName+"不存在");
                throw new Exception("Job not found!");
            }
        }
    }

    /**
     * 执行ktr文件
     * @param filename
     * @param params
     * @return
     */
    public List<Map<String,Object>> runKtr(String filename, Map<String, String> params) throws Exception {

        String completePath = jobPath + filename;
        TransMeta tm = new TransMeta(completePath);
        Trans trans = new Trans(tm);
        if (params != null) {
            Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                //trans.setParameterValue(entry.getKey(),entry.getValue());
                trans.setVariable(entry.getKey(),entry.getValue());
            }
        }
        trans.setLogLevel(LogLevel.ERROR);
        trans.execute(null);
        trans.waitUntilFinished();

        //日志
        String logChannelId = trans.getLogChannelId();
        LoggingBuffer appender = KettleLogStore.getAppender();
        String logText = appender.getBuffer(logChannelId, true).toString();
        XxlJobLogger.log(logText);
        appender.clear();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (RowMetaAndData rowMetaAndData : trans.getResultRows()){
            Map<String,Object> map = new HashMap<>();
            String[] fieldNames = rowMetaAndData.getRowMeta().getFieldNames();
            for(int i=0;i < fieldNames.length;i++){
                map.put(fieldNames[i],rowMetaAndData.getData()[i]);
            }
            mapList.add(map);
        }
        return mapList;

    }
}

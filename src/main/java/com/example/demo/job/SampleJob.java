package com.example.demo.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 示例带参定时任务
 */
@Slf4j
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // here,you can do everything
        log.info(String.format("无参定时任务已启动 时间:"+ DateUtil.now()));
    }
}

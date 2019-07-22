package com.example.demo.service;

import com.example.demo.model.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class QuartzJobService {

    /**
     * 项目启动时初始化定时器
     */
    @PostConstruct
    public void init() {
        //从数据库查出所有定时任务，并开启
        //模拟初始化检测是否需要启动 or 暂停过程
        //===============开始=========
//        QuartzJob q1=   new QuartzJob("com.example.demo.job.SampleJob",
//                "0/1 * * * * ? ", null, "无参定时任务", 0);
//        QuartzJob q2 = new QuartzJob("com.example.demo.job.SampleParamJob",
//                "0/1 * * * * ? ", null, "有参定时任务", 0);
//        List<QuartzJob> jobs = Arrays.asList(q1,q2);
//        if (q1.getStatus() == 1) {
//            //暂停该定时任务
//        }
        //==============结束============
        add("com.example.demo.job.SampleJob","0/1 * * * * ? ",null);
        add("com.example.demo.job.SampleParamJob","0/1 * * * * ? ","i'm param");
    }

    @Autowired
    private Scheduler scheduler;

    public Object startQuartz(String param) {
        QuartzJob job = new QuartzJob();
        if (StringUtils.isEmpty(param)) {
            job.setJobClassName("com.example.demo.job.SampleJob");
            job.setCronExpression("0/1 * * * * ? ");
            job.setDescription("无参定时任务");
            log.info("无参定时任务已被重新启动....");
        }
        if (!StringUtils.isEmpty(param)) {
            job.setJobClassName("com.example.demo.job.SampleParamJob");
            job.setCronExpression("0/1 * * * * ? ");
            job.setDescription("有参定时任务");
            job.setParameter(param);
            log.info("有参定时任务已被重新启动....");
        }
        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }



    public Object pause(String param) {
        QuartzJob job = new QuartzJob();
        if (StringUtils.isEmpty(param)) {
            //模拟数据
            job.setJobClassName("com.example.demo.job.SampleJob");
            //暂停无参定时器
            try {
                scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
                log.info("无参定时器已暂停暂停.......");
            } catch (SchedulerException e) {
                log.info("无参定时器暂停失败");
            }
        }
        if (!StringUtils.isEmpty(param)) {
            //模拟数据
            job.setJobClassName("com.example.demo.job.SampleParamJob");
            //暂停有参定时器
            try {
                scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
                log.info("有参定时器已暂停暂停.......");
            } catch (SchedulerException e) {
                log.info("有参定时器暂停失败.......");
            }
        }
        return ResponseEntity.ok().build();
    }


    /**
     * 添加定时任务
     *
     * @param jobClassName
     * @param cronExpression
     * @param parameter
     */
    public void add(String jobClassName, String cronExpression, String parameter) {

        try {
            // 启动调度器
            scheduler.start();

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter", parameter)
                    .build();

            //表达式调度构建器(即任务执行的时间) 使用withMisfireHandlingInstructionDoNothing() 忽略掉调度暂停过程中没有执行的调度
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.info("创建定时器失败");
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    public Object addQuartz() {
        //模拟数据
        //从数据库查询是否存在
//        List<QuartzJob> list = quartzJobService.findByJobClassName(job.getJobClassName());
//        if(list!=null&&list.size()>0){
//            return new ResultUtil<Object>().setErrorMsg("该定时任务类名已存在");
//        }
        //不存在新添加定时任务
//        add(job.getJobClassName(),job.getCronExpression(),job.getParameter());
        // 保存到db
//        quartzJobService.save(job);
        return ResponseEntity.ok().build();
    }

    public Object deleteQuartz() {
        //模拟页面传过来的数据
        String jobId = "1";
        //从数据库查询该定时器
        //删除
        try {
            scheduler.deleteJob(JobKey.jobKey(""));
        } catch (SchedulerException e) {
            log.info("定时器删除成功");
        }
        return ResponseEntity.ok().build();
    }

    public Object updateQuartz() {
        //模拟数据
        //先删除
//        delete(job.getJobClassName());
        //再添加
//        add(job.getJobClassName(),job.getCronExpression(),job.getParameter());
//        job.setStatus(CommonConstant.STATUS_NORMAL);
        //持久化到db
//        quartzJobService.update(job);
        return null;
    }
}

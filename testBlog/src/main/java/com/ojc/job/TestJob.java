package com.ojc.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {

    //从每分钟的0秒开始,每五秒执行一次
    /*@Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //要执行的代码
        System.out.println("定时代码执行了");
    }*/

}

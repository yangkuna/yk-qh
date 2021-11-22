package com.example.ykqh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 杨昆
 * @date 2021/6/17 10:48
 * @describe 异步线程池配置
 */
@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    /**
     *
    任务拒绝策略
    1、AbortPolicy           抛出异常（不可取）ThreadPoolExecutor中默认的拒绝策略就是AbortPolicy。缺点：直接抛出异常。
    2、CallerRunsPolicy      采用调用executor的线程执行任务（可取）缺点：可能会阻塞上层线程。
    3、DiscardPolicy         抛弃任务，不执行，不抛异常（不可取）缺点：丢失任务。
    4、DiscardOldestPolicy   抛弃队列中旧任务，在队列中添加新任务（不可取）缺点：丢失任务。
    5、自定义拒绝策略（可取）重写rejectedExecution(Runnable r, ThreadPoolExecutor executor)方法，即可自定义拒绝策略
     *
     当任务到达时，如果当前运行的线程少于corePoolSize，则 Executor始终首选添加新的线程执行任务，
     而不是把任务放入队列。当创建的线程数大于等于 corePoolSize，则 Executor始终首选将任务加入队列，
     而不添加新的线程，任务由核心线程执行。当线程池里核心线程和队列都满时(大于核心线程6个＋100个任务 = 106)
     才创建新的线程执行任务。但当创建的线程大于最大线程数＋任务数时（112），执行饱和策略CallerRunsPolicy
     */

    @Override
    @Bean(name = "asyncThreadPoolExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(6);
        // 最大线程数
        taskExecutor.setMaxPoolSize(12);
        // 队列最大长度
        taskExecutor.setQueueCapacity(100);
        // 空闲线程等待时间
        taskExecutor.setKeepAliveSeconds(30);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("YK-QH");
        // 当任务数量超过MaxPoolSize和QueueCapacity时使用的策略，该策略是用调用任务的线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 线程异常时的处理方法
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        log.error("异步线程：{}出现异常" ,Thread.currentThread().getName());
        return null;
    }

}
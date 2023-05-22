package org.example.thread.pool;


import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(5, 10, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100000));
    }
}

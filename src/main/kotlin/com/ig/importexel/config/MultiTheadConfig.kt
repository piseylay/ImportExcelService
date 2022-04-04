package com.ig.importexel.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class MultiTheadConfig {
    @Bean
    @Primary
    fun getTaskExecutor(): TaskExecutor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 3
        executor.setQueueCapacity(1000)
        executor.setThreadNamePrefix("userThread-")
        executor.initialize()
        return executor
    }
}

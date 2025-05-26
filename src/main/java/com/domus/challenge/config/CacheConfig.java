package com.domus.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class to enable scheduled tasks in the application.
 */
@Configuration
@EnableScheduling
public class CacheConfig {
    // This class enables Spring's scheduling capabilities, allowing
    // the use of @Scheduled annotations throughout the project.
}


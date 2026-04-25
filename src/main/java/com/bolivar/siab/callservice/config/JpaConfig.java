package com.bolivar.siab.callservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Configuration for Oracle database connectivity and Hibernate settings.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.bolivar.siab.callservice")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {
    // Spring Boot auto-configures the EntityManagerFactory and DataSource from application.yml.
    // This configuration class enables JPA repositories scanning, auditing, and transaction management.
}

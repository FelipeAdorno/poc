package br.com.monkey.ecx.configuration;

import br.com.monkey.ecx.core.auditing.AuditingDateTimeProvider;
import br.com.monkey.ecx.core.auditing.SpringSecurityAuditor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"br.com.monkey.ecx"})
@EnableJpaRepositories(basePackages = {"br.com.monkey.ecx"})
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class DomainConfiguration {

    @Bean
    public DateTimeProvider dateTimeProvider(Clock systemClock) {
        return new AuditingDateTimeProvider(systemClock);
    }

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new SpringSecurityAuditor();
    }

    @Bean
    public Clock systemClock() {
        return Clock.systemUTC();
    }

}

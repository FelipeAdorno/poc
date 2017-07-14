package br.com.monkey.ecx.core.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.nonNull;

public class SpringSecurityAuditor implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String user = "unknown";
        if(nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            user = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return user;
    }
}
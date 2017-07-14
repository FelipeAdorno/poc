package br.com.monkey.ecx.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static java.util.Objects.nonNull;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class UserIdAndIpValidation {

    @SuppressWarnings("unchecked")
    @Around("execution(public * br.com.monkey.ecx.*..*.produce(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if(joinPoint.getArgs()[0] instanceof Message) {
            Message messageWrapper = (Message) joinPoint.getArgs()[0];
            if(nonNull(SecurityContextHolder.getContext().getAuthentication())) {
                messageWrapper.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            } else {
                messageWrapper.setUserName("UNKNOWN");
            }

            try {
                if (RequestContextHolder.currentRequestAttributes() instanceof IpRequestAttribute) {
                    messageWrapper.setUserIp(((IpRequestAttribute)
                            RequestContextHolder.currentRequestAttributes()).getIp());
                } else {
                    messageWrapper.setUserIp(((ServletRequestAttributes) RequestContextHolder
                            .currentRequestAttributes()).getRequest().getRemoteAddr());
                }
            } catch (IllegalStateException ex) {
                log.warn("error on get user ip: ", ex.getMessage());
                messageWrapper.setUserIp("UNKNOWN");
            }
        }
        return joinPoint.proceed();
    }
}

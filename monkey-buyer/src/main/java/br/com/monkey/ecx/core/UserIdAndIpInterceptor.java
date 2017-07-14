package br.com.monkey.ecx.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class UserIdAndIpInterceptor {

    @SuppressWarnings("unchecked")
    @Before("execution(public * br.com.monkey.ecx.*..*.onReceiveMessage(..))")
    public void logAround(JoinPoint joinPoint) throws Throwable {
        if(joinPoint.getArgs()[0] instanceof Message) {
            Message message = (Message) joinPoint.getArgs()[0];
            setUserIdInContextHolder(message);
            setIpAddressInContextHolder(message);
        }
    }

    private void setUserIdInContextHolder(Message messageWrapper) {
        if (isEmpty(messageWrapper.getUserName())) {
            if (nonNull(SecurityContextHolder.getContext().getAuthentication())) {
                messageWrapper.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            } else {
                messageWrapper.setUserName("UNKNOWN");
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(messageWrapper.getUserName(), null, null));
        }
    }

    private void setIpAddressInContextHolder(Message messageWrapper) {
        if (isEmpty(messageWrapper.getUserIp())) {
            if (nonNull(RequestContextHolder.getRequestAttributes())) {
                messageWrapper.setUserIp(((ServletRequestAttributes)
                        RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr());
            } else {
                messageWrapper.setUserIp("UNKNOWN");
            }
        } else {
            addIpInRequest(messageWrapper);
        }
    }

    private void addIpInRequest(Message messageWrapper) {
        IpRequestAttribute ipRequestAttribute = new IpRequestAttribute();
        ipRequestAttribute.addIp(messageWrapper.getUserIp());
        RequestContextHolder.setRequestAttributes(ipRequestAttribute);
    }
}

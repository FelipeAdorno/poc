package br.com.monkey.ecx.core.auditing;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import static java.util.Objects.nonNull;

public class UserIpEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        String ip = getIpAddress();
        if(entity instanceof Auditable) {
            ((Auditable) entity).setCreateUserIp(ip);
        }
        preUpdate(entity);
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        String ip = getIpAddress();
        if(entity instanceof Auditable) {
            ((Auditable) entity).setLastUserIp(ip);
        }
    }

    private String getIpAddress() {
        String ip = "UNKNOWN";
        if(nonNull(RequestContextHolder.currentRequestAttributes())) {
            if (RequestContextHolder.currentRequestAttributes() instanceof IpRequestAttribute) {
                ip = ((IpRequestAttribute) RequestContextHolder.currentRequestAttributes()).getIp();
            } else {
                ip = ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes()).getRequest().getRemoteAddr();
            }
        }
        return ip;
    }
}
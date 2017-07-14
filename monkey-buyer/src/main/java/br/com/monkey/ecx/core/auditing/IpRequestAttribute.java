package br.com.monkey.ecx.core.auditing;

import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class IpRequestAttribute implements RequestAttributes {

    private Map<String, String> userIp = new HashMap<>();

    @Override
    public Object getAttribute(String name, int scope) {
        return userIp.get(name);
    }

    public String getIp() {
        return userIp.get("userIp");
    }

    public void addIp(String ip) {
        userIp.put("userIp", ip);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        userIp.put(name, value.toString());
    }

    @Override
    public void removeAttribute(String name, int scope) {

    }

    @Override
    public String[] getAttributeNames(int scope) {
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {

    }

    @Override
    public Object resolveReference(String key) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}

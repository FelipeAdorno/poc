package br.com.monkey.ecx.core.auditing;

public interface Auditable {

    void setCreateUserIp(String createUserIp);

    void setLastUserIp(String lastUserIp);
}

package br.com.monkey.ecx.core.auditing;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, UserIpEntityListener.class})
public class AbstractEntity implements Auditable, Serializable {

    @CreatedDate
    private Date createdAt;

    @CreatedBy
    private String createUserId;

    @CreatedIp
    private String createUserIp;

    @LastModifiedDate
    private Date updatedAt;

    @LastModifiedBy
    private String lastUserId;

    @LastModifiedIp
    private String lastUserIp;
}

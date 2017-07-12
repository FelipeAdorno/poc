package br.com.monkey.ecx.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@ToString
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners({AuditingEntityListener.class})
public class AbstractEntity {

    @CreatedDate
    private Date createdAt;

    @CreatedBy
    private String createUserId;

//    @CreatedIp
    private String createUserIp;

    @LastModifiedDate
    private Date updatedAt;

    @LastModifiedBy
    private String lastUserId;

//    @LastModifiedIp
    private String lastUserIp;
}
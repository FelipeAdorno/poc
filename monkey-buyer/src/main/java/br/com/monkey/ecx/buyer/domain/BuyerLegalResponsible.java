package br.com.monkey.ecx.buyer.domain;

import br.com.monkey.ecx.buyer.enumeration.BuyerLegalResponsibleStatusEnum;
import br.com.monkey.ecx.core.auditing.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "BuyerLegalResponsible")
public class BuyerLegalResponsible extends AbstractEntity {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true)
    private Integer id;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String name;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String governmentId1;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String governmentId2;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String phone;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String email;

    @NotEmpty
    @ApiModelProperty(readOnly = true)
    private String status = BuyerLegalResponsibleStatusEnum.ACTIVE.name();
}

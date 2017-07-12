package br.com.monkey.ecx.buyer.domain;

import br.com.monkey.ecx.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Buyer extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String shortName;

    @NotEmpty
    private String admName;

    @NotEmpty
    private String admGovernmentId;

    @NotEmpty
    private String companyName;
}

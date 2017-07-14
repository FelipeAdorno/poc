package br.com.monkey.ecx.buyer.domain;

import br.com.monkey.ecx.buyer.enumeration.BuyerStatusEnum;
import br.com.monkey.ecx.core.auditing.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "Buyer")
@EqualsAndHashCode(callSuper = true)
public class Buyer extends AbstractEntity {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true)
    private Integer id;

    @NotEmpty
    @ReadOnlyProperty
    @ApiModelProperty(required = true,
            notes = "After create you can't update this field, to update please contact Monkey Exchange")
    private String companyName;

    @NotEmpty
    @ReadOnlyProperty
    @Column(unique = true)
    @Size(min = 14, max = 14)
    @ApiModelProperty(required = true,
            notes = "After create you can't update this field, to update please contact Monkey Exchange")
    private String fundGovernmentId;

    @NotEmpty
    @ReadOnlyProperty
    @ApiModelProperty(required = true,
            notes = "After create you can't update this field, to update please contact Monkey Exchange")
    private String fundName;

    @ReadOnlyProperty
    @ApiModelProperty(readOnly = true)
    private String status = BuyerStatusEnum.APPROVAL.name();

    @NotEmpty
    @ApiModelProperty(required = true)
    private String custodianBank;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String admName;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String shortName;

    @NotEmpty
    @ReadOnlyProperty
    @Size(min = 14, max = 14)
    @ApiModelProperty(required = true,
            notes = "After create you can't update this field, to update please contact Monkey Exchange")
    private String admGovernmentId;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorName;

    @NotEmpty
    @Size(min = 11, max = 11)
    @ApiModelProperty(required = true)
    private String fundDirectorCpf;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorAddress;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorAddressNumber;

    private String fundDirectorAddressComplement;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorNeighborhood;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorCity;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorState;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorCountry;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorCep;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundDirectorPhone;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String fundAccountManagerName;

    @NotEmpty
    @ReadOnlyProperty
    @Size(min = 14, max = 14)
    @ApiModelProperty(required = true,
            notes = "After create you can't update this field, to update please contact Monkey Exchange")
    private String fundAccountManagerGovernmentId;


    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementName;

    @NotEmpty
    @Size(min = 11, max = 11)
    @ApiModelProperty(required = true)
    private String directorManagementCpf;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementAddress;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementAddressNumber;

    private String directorManagementAddressComplement;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementNeighborhood;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementCity;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementState;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementCountry;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementCep;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String directorManagementPhone;

    @Valid
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "buyerId")
    @ApiModelProperty(required = true)
    private List<BuyerLegalResponsible> companyLegalResponsibles;
}

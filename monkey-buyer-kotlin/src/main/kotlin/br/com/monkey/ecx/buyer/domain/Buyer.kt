package br.com.monkey.ecx.buyer.domain

import br.com.monkey.ecx.core.AbstractEntity
import org.hibernate.validator.constraints.NotEmpty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Buyer(

        @Id
        @GeneratedValue
        var id: Long? = null,

        @NotEmpty
        var shortName: String? = "",

        @NotEmpty
        var admName: String? = "",

        @NotEmpty
        var admGovernmentId: String? = "",

        @NotEmpty
        var companyName: String? = ""

) : AbstractEntity()


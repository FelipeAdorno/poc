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
        val id: Long? = null,

        @NotEmpty
        val shortName: String? = "",

        @NotEmpty
        val admName: String? = "",

        @NotEmpty
        val admGovernmentId: String? = "",

        @NotEmpty
        val companyName: String? = ""

) : AbstractEntity()


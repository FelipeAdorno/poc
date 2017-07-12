package br.com.monkey.ecx.buyer.api.v1

import br.com.monkey.ecx.buyer.domain.Buyer
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ResourceAssemblerSupport
import org.springframework.stereotype.Component

@Component
class BuyerResourceAssemblerSupport :
        ResourceAssemblerSupport<Buyer, Resource<*>>(Buyer::class.java, Resource::class.java) {

    override fun toResource(entity: Buyer?): Resource<*> {
        return createResourceWithId(entity?.id, entity)
    }

    override fun instantiateResource(entity: Buyer?): Resource<Buyer> {
        return Resource<Buyer>(entity)
    }
}


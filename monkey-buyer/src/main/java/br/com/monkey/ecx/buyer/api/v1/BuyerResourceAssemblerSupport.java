package br.com.monkey.ecx.buyer.api.v1;

import br.com.monkey.ecx.buyer.domain.Buyer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BuyerResourceAssemblerSupport extends ResourceAssemblerSupport<Buyer, Resource> {

    public BuyerResourceAssemblerSupport() {
        super(BuyerRestService.class, Resource.class);
    }

    @Override
    public Resource toResource(Buyer entity) {
        //add links e etc
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected Resource<Buyer> instantiateResource(Buyer entity) {
        //instancia o resource
        return new Resource<>(entity);
    }
}

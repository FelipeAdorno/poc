package br.com.monkey.ecx.buyer.api.v1;

import br.com.monkey.ecx.buyer.domain.Buyer;
import br.com.monkey.ecx.buyer.domain.BuyerRepository;
import br.com.monkey.ecx.core.MonkeyPagedResourceAssembler;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@ExposesResourceFor(Buyer.class)
@AllArgsConstructor
@RequestMapping("v1/buyers")
public class BuyerRestService {

    private BuyerRepository buyerRepository;
    private BuyerResourceAssemblerSupport buyerResourceAssemblerSupport;
    private MonkeyPagedResourceAssembler<Buyer> pagedResourcesAssembler;

    @PostMapping
    private Resource<?> create(@RequestBody Buyer buyer) {
        return buyerResourceAssemblerSupport.toResource(buyerRepository.save(buyer));
    }

    @GetMapping
    private PagedResources<?> list() {
        return pagedResourcesAssembler.toResource(Buyer.class, buyerRepository
                .findAll(new PageRequest(0, 10)), buyerResourceAssemblerSupport);
    }
}

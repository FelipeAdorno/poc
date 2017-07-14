package br.com.monkey.ecx.buyer.api.v1;

import br.com.monkey.ecx.buyer.domain.Buyer;
import br.com.monkey.ecx.buyer.domain.BuyerRepository;
import br.com.monkey.ecx.core.Message;
import br.com.monkey.ecx.core.MonkeyMessageSource;
import br.com.monkey.ecx.core.exception.ConflictException;
import br.com.monkey.ecx.core.exception.ResourceNotFoundException;
import br.com.monkey.ecx.producer.CompanyCreatedEvent;
import br.com.monkey.ecx.producer.CompanyCreatedEventProducer;
import br.com.monkey.ecx.security.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static br.com.monkey.ecx.buyer.domain.BuyerSpecification.governmentId;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@RestController
@AllArgsConstructor
@RequestMapping("v1/buyers")
@ExposesResourceFor(Buyer.class)
public class BuyerRestService {

    private static final String ADMIN = "ADMIN";
    private static final String ACTIVE = "ACTIVE";

    private final SecurityUtils securityUtils;
    private final BuyerRepository buyerRepository;
    private final MonkeyMessageSource monkeyMessageSource;
    private final CompanyCreatedEventProducer companyCreatedEventProducer;
    private final BuyerResourceAssemblerSupport buyerResourceAssemblerSupport;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<?> create(@RequestBody Buyer buyer) {
        buyer.setId(null);
        if(nonNull(buyerRepository.findOne(governmentId(buyer.getFundGovernmentId())))) {
            throw new ConflictException(monkeyMessageSource.getMessage(
                    "buyer.conflict", new Object[]{buyer.getFundGovernmentId()}));
        }
        buyer = buyerRepository.save(buyer);
        companyCreatedEventProducer.produce(new Message<>(
                CompanyCreatedEvent.builder()
                        .companyId(buyer.getId())
                        .userId(securityUtils.getUser().getId())
                        .role(ADMIN)
                        .status(ACTIVE)
                        .createUserIp(buyer.getCreateUserIp())
                        .build())
        );
        return buyerResourceAssemblerSupport.toResource(buyer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<?> getById(@PathVariable Integer id) {
        return buyerResourceAssemblerSupport.toResource(ofNullable(
                buyerRepository.findOne(id)).orElseThrow(this::instantiateExceptionWhenBuyerNotFound));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<?> update(@PathVariable Integer id, @RequestBody Buyer buyer) {
        buyer.setId(id);
        if(!buyerRepository.exists(id)) {
            throw instantiateExceptionWhenBuyerNotFound();
        }
        return buyerResourceAssemblerSupport.toResource(buyerRepository.save(buyer));
    }

    private ResourceNotFoundException instantiateExceptionWhenBuyerNotFound() {
        return new ResourceNotFoundException(monkeyMessageSource.getMessage("buyer.not.found"));
    }
}

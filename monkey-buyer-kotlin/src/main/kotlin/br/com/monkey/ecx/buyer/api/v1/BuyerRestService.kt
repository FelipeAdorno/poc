package br.com.monkey.ecx.buyer.api.v1

import br.com.monkey.ecx.buyer.domain.Buyer
import br.com.monkey.ecx.buyer.domain.BuyerRepository
import br.com.monkey.ecx.core.MonkeyPagedResourceAssembler
import org.springframework.data.domain.PageRequest
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/buyers")
class BuyerRestService(val buyerRepository: BuyerRepository,
                       val buyerResourceAssemblerSupport: BuyerResourceAssemblerSupport,
                       val monkeyPagedResourceAssembler: MonkeyPagedResourceAssembler<Buyer>) {

    @PostMapping
    fun create(@RequestBody buyer: Buyer) : Resource<*> {
        return buyerResourceAssemblerSupport.toResource(buyerRepository.save(buyer))
    }

    @GetMapping
    fun list() : PagedResources<*> {
        return monkeyPagedResourceAssembler.toResource(
                Buyer::class.java, buyerRepository.findAll(PageRequest(0, 10)), buyerResourceAssemblerSupport)
    }
}


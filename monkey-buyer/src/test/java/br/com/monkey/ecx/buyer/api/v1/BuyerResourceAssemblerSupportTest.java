package br.com.monkey.ecx.buyer.api.v1;

import br.com.monkey.ecx.buyer.domain.Buyer;
import br.com.monkey.ecx.support.TestSupportMvc;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;

public class BuyerResourceAssemblerSupportTest extends TestSupportMvc {

    private BuyerResourceAssemblerSupport buyerResourceAssemblerSupport;

    private Buyer buyer;

    @Override
    public void start() {
        buyerResourceAssemblerSupport = new BuyerResourceAssemblerSupport();
        buyer = Fixture.from(Buyer.class).gimme(VALID.name());
    }

    @Test
    public void toResource() throws Exception {
        Resource<Buyer> buyerResource = expectedResource();
        buyerResource.add(new Link("http://localhost/v1/buyers/"+buyer.getId()));
        assertEquals(buyerResource, buyerResourceAssemblerSupport.toResource(buyer));
    }

    @Test
    public void instantiateResource() throws Exception {
        assertEquals(expectedResource(), buyerResourceAssemblerSupport.instantiateResource(buyer));
    }

    private Resource<Buyer> expectedResource() {
        return new Resource<>(buyer);
    }
}
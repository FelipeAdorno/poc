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
import br.com.monkey.ecx.security.User;
import br.com.monkey.ecx.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

public class BuyerRestServiceTest extends TestSupport {

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private MonkeyMessageSource monkeyMessageSource;

    @Mock
    private CompanyCreatedEventProducer companyCreatedEventProducer;

    @Mock
    private BuyerResourceAssemblerSupport buyerResourceAssemblerSupport;

    private BuyerRestService buyerRestService;

    private Buyer buyer;

    @Override
    public void init() {
        buyerRestService = new BuyerRestService(securityUtils, buyerRepository,
                monkeyMessageSource, companyCreatedEventProducer, buyerResourceAssemblerSupport);
        buyer = Fixture.from(Buyer.class).gimme(VALID.name());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create() throws Exception {
        User user = new User();
        user.setId(Integer.MAX_VALUE);
        Resource<Buyer> buyerResource = expectedResource();
        when(buyerRepository.findOne(any(Specification.class))).thenReturn(null);
        when(buyerRepository.save(buyer)).thenReturn(buyer);
        when(securityUtils.getUser()).thenReturn(user);
        when(buyerResourceAssemblerSupport.toResource(buyer)).thenReturn(buyerResource);

        assertEquals(buyerResource, buyerRestService.create(buyer));

        InOrder inOrder = inOrder(buyerRepository, buyerResourceAssemblerSupport, companyCreatedEventProducer);
        inOrder.verify(buyerRepository, times(1)).findOne(any(Specification.class));
        inOrder.verify(buyerRepository, times(1)).save(buyer);
        inOrder.verify(companyCreatedEventProducer, times(1)).produce(new Message<>(
                CompanyCreatedEvent.builder()
                        .companyId(buyer.getId())
                        .userId(Integer.MAX_VALUE)
                        .role("ADMIN")
                        .status("ACTIVE")
                        .createUserIp(buyer.getCreateUserIp())
                        .build())
        );
        inOrder.verify(buyerResourceAssemblerSupport, times(1)).toResource(buyer);
        inOrder.verifyNoMoreInteractions();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ConflictException.class)
    public void createWhenBuyerAlreadyExist() throws Exception {
        when(buyerRepository.findOne(any(Specification.class))).thenReturn(buyer);
        buyerRestService.create(buyer);
    }

    @Test
    public void getById() throws Exception {
        Resource<Buyer> buyerResource = expectedResource();
        when(buyerRepository.findOne(buyer.getId())).thenReturn(buyer);
        when(buyerResourceAssemblerSupport.toResource(buyer)).thenReturn(buyerResource);

        assertEquals(buyerResource, buyerRestService.getById(buyer.getId()));

        InOrder inOrder = inOrder(buyerRepository, buyerResourceAssemblerSupport);
        inOrder.verify(buyerRepository, times(1)).findOne(buyer.getId());
        inOrder.verify(buyerResourceAssemblerSupport, times(1)).toResource(buyer);
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdNotFound() throws Exception {
        when(buyerRepository.findOne(buyer.getId())).thenReturn(null);
        buyerRestService.getById(buyer.getId());
    }

    @Test
    public void update() throws Exception {
        Resource<Buyer> buyerResource = expectedResource();
        when(buyerRepository.exists(buyer.getId())).thenReturn(true);
        when(buyerRepository.save(buyer)).thenReturn(buyer);
        when(buyerResourceAssemblerSupport.toResource(buyer)).thenReturn(buyerResource);

        assertEquals(buyerResource, buyerRestService.update(buyer.getId(), buyer));

        InOrder inOrder = inOrder(buyerRepository, buyerResourceAssemblerSupport);
        inOrder.verify(buyerRepository, times(1)).save(buyer);
        inOrder.verify(buyerResourceAssemblerSupport, times(1)).toResource(buyer);
        inOrder.verifyNoMoreInteractions();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNotFound() throws Exception {
        when(buyerRepository.exists(buyer.getId())).thenReturn(false);
        buyerRestService.update(buyer.getId(), buyer);
    }

    private Resource<Buyer> expectedResource() {
        Resource<Buyer> buyerResource = new Resource<>(buyer);
        buyerResource.add(new Link("http://localhost/v1/buyers/"+buyer.getId()));
        return buyerResource;
    }
}
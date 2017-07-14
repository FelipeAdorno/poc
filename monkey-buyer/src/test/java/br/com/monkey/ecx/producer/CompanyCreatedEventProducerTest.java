package br.com.monkey.ecx.producer;

import br.com.monkey.ecx.configuration.properties.CloudAWSSNSProperties;
import br.com.monkey.ecx.core.Message;
import br.com.monkey.ecx.support.TestSupport;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;

import static br.com.monkey.ecx.templates.FixtureTemplates.VALID;
import static org.mockito.Mockito.times;

public class CompanyCreatedEventProducerTest extends TestSupport {

    @Mock
    private CloudAWSSNSProperties cloudAWSSNSProperties;

    @Mock
    private NotificationMessagingTemplate notificationMessagingTemplate;

    private CompanyCreatedEventProducer companyCreatedEventProducer;

    private CompanyCreatedEvent companyCreatedEvent;

    @Override
    public void init() {
        companyCreatedEventProducer = new CompanyCreatedEventProducer(
                cloudAWSSNSProperties, notificationMessagingTemplate);
        companyCreatedEvent = Fixture.from(CompanyCreatedEvent.class).gimme(VALID.name());
    }

    @Test
    public void produce() throws Exception {
        Message<CompanyCreatedEvent> message = new Message<>(companyCreatedEvent, "UNKNOWN", "UNKNOWN");
        when(cloudAWSSNSProperties.getCompanyCreatedEventProducer()).thenReturn("companyCreatedEventProducer");
        companyCreatedEventProducer.produce(message);
        InOrder inOrder = inOrder(cloudAWSSNSProperties, notificationMessagingTemplate);
        inOrder.verify(cloudAWSSNSProperties, times(1)).getCompanyCreatedEventProducer();
        inOrder.verify(notificationMessagingTemplate, times(1))
                .convertAndSend("companyCreatedEventProducer", message);
        inOrder.verifyNoMoreInteractions();
    }
}
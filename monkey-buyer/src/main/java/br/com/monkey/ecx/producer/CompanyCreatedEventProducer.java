package br.com.monkey.ecx.producer;

import br.com.monkey.ecx.configuration.properties.CloudAWSSNSProperties;
import br.com.monkey.ecx.core.Message;
import lombok.AllArgsConstructor;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompanyCreatedEventProducer {

    private final CloudAWSSNSProperties cloudAWSSNSProperties;
    private final NotificationMessagingTemplate notificationMessagingTemplate;

    public void produce(Message<CompanyCreatedEvent> companyCreatedEventMessage) {
        notificationMessagingTemplate.convertAndSend(cloudAWSSNSProperties
                .getCompanyCreatedEventProducer(), companyCreatedEventMessage);
    }
}

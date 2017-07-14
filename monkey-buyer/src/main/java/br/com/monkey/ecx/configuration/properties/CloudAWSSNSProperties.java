package br.com.monkey.ecx.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cloud.aws.sns")
public class CloudAWSSNSProperties {
    private String companyCreatedEventProducer;
}

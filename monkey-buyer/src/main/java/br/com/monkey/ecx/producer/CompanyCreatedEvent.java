package br.com.monkey.ecx.producer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = CompanyCreatedEvent.CompanyCreatedEventBuilder.class)
public class CompanyCreatedEvent {

    private String role, status, createUserIp;
    private Integer companyId, userId;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class CompanyCreatedEventBuilder{}
}

package br.com.monkey.ecx.core.exception;

import br.com.monkey.ecx.core.exception.model.MessageType;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.util.Collections.singletonList;

public class BusinessLogicException extends MonkeyRuntimeException {

    public BusinessLogicException(String notifications) {
        super(MessageType.Business_Logic_Error, singletonList(notifications), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public BusinessLogicException(List<String> notifications) {
        super(MessageType.Business_Logic_Error, notifications, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public BusinessLogicException(List<String> notifications, Throwable cause) {
        super(MessageType.Business_Logic_Error, notifications, cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

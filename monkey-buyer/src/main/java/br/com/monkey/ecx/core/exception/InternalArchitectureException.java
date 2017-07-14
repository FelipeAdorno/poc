package br.com.monkey.ecx.core.exception;

import br.com.monkey.ecx.core.exception.model.MessageType;
import org.springframework.http.HttpStatus;

import java.util.List;


public class InternalArchitectureException extends MonkeyRuntimeException {

    public InternalArchitectureException(List<String> notifications) {
        super(MessageType.Internal_Architecture_Error, notifications, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public InternalArchitectureException(List<String> notifications, Throwable cause) {
        super(MessageType.Internal_Architecture_Error, notifications, cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

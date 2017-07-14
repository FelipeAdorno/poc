package br.com.monkey.ecx.core.exception;


import br.com.monkey.ecx.core.exception.model.MessageType;
import org.springframework.http.HttpStatus;

import static java.util.Collections.singletonList;

public class BadCredentialsException extends MonkeyRuntimeException {
    public BadCredentialsException(String message){
        super(MessageType.Invalid_Token, singletonList(message), HttpStatus.UNAUTHORIZED.value());
    }
}

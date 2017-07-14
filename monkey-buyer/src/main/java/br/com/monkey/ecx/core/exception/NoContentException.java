package br.com.monkey.ecx.core.exception;


import br.com.monkey.ecx.core.exception.model.MessageType;
import org.springframework.http.HttpStatus;

import static java.util.Collections.emptyList;

public class NoContentException extends MonkeyRuntimeException {

    public NoContentException() {
        super(MessageType.No_Content_Error, emptyList(), HttpStatus.NO_CONTENT.value());
    }
}

package br.com.monkey.ecx.core.exception.handler;

import br.com.monkey.ecx.core.exception.*;
import br.com.monkey.ecx.core.exception.model.Message;
import br.com.monkey.ecx.core.exception.model.MessageType;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandler {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    private static final String ERROR = "error";

    @ExceptionHandler({ClientException.class})
    public HttpEntity<Message> handlerClientException(ClientException ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Integration_Error, MessageType.Integration_Error.getDescription());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity<Message> handlerValidationException(MethodArgumentTypeMismatchException ex) {;
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Parameter_Error,
                MessageType.Parameter_Error.getDescription(),
                singletonList("Invalid parameter: " + ex.getParameter().getParameterName() +
                        " with value: " + ex.getValue()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpEntity<Message> handlerValidationException(ConstraintViolationException ex) {
        log.debug(ERROR, ex);
        Message message = processConstraintViolation(ex);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<Message> handlerValidationException(MethodArgumentNotValidException ex) {
        log.debug(ERROR, ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Message message = processFieldErrors(fieldErrors);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class})
    public HttpEntity<Message> handlerMissingServletRequestParameterException(Exception ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Parameter_Error,
                MessageType.Parameter_Error.getDescription(), singletonList(ex.getMessage()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public HttpEntity<Message> handlerHttpMediaTypeNotSupportedException(Exception ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Unsupported_Media_Type, MessageType.Unsupported_Media_Type.getDescription());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpEntity<Message> handlerHttpRequestMethodNotSupportedException(Exception ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Method_Not_Allowed, MessageType.Method_Not_Allowed.getDescription());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public HttpEntity<Message> handlerResourceNotFoundException(final ResourceNotFoundException ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Resource_Not_Found,
                MessageType.Resource_Not_Found.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public HttpEntity<Message> handlerBadCredentialsException(final BadCredentialsException ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Invalid_Token,
                MessageType.Invalid_Token.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public HttpEntity<Message> handlerBusinessLogicException(BusinessLogicException ex) {
        log.error(ERROR, ex);
        Message message = new Message(MessageType.Business_Logic_Error,
                MessageType.Business_Logic_Error.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.PRECONDITION_FAILED);
    }

//    @ExceptionHandler({AccessDeniedException.class, AccountExpiredException.class,
//            InvalidTokenException.class, AuthenticationException.class,
//            AuthenticationCredentialsNotFoundException.class,
//            AuthenticationServiceException.class, InsufficientAuthenticationException.class,
//            InternalAuthenticationServiceException.class, LockedException.class, ProviderNotFoundException.class})
//    public HttpEntity<Message> handlerAccessDeniedException(AccessDeniedException ex) {
//        return handleSecurityException(ex);
//    }

    @ExceptionHandler(BadRequestException.class)
    public HttpEntity<Message> handlerBadRequestException(BadRequestException ex) {
        log.error(ERROR, ex);
        Message message = new Message(MessageType.Bad_Request_Error,
                MessageType.Bad_Request_Error.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, Error.class})
    public HttpEntity<Message> handlerException(Exception ex) {
        log.error(ERROR, ex);
        Message message = new Message(MessageType.Internal_Architecture_Error,
                MessageType.Internal_Architecture_Error.getDescription());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalArchitectureException.class)
    public HttpEntity<Message> handlerInternalArchitectureException(InternalArchitectureException ex) {
        log.error(ERROR, ex);
        Message message = new Message(MessageType.Internal_Architecture_Error,
                MessageType.Internal_Architecture_Error.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public HttpEntity<Message> handlerResourceConflictException(ConflictException ex) {
        log.debug(ERROR, ex);
        Message message = new Message(MessageType.Conflict_Error,
                MessageType.Conflict_Error.getDescription(), ex.getNotifications());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handlerNoContentException(NoContentException ex) {
        log.debug(ERROR, ex);
    }

    private Message processFieldErrors(List<FieldError> fieldErrors) {
        Message message = new Message(MessageType.Parameter_Error, MessageType.Parameter_Error.getDescription());
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = fieldError.getDefaultMessage();
            message.addNotification(fieldError.getField().concat(":").concat(localizedErrorMessage));
        }
        return message;
    }

    private Message processConstraintViolation(ConstraintViolationException ex) {
        Message message = new Message(MessageType.Parameter_Error, MessageType.Parameter_Error.getDescription());
        Set<ConstraintViolation<?>> set =  ex.getConstraintViolations();
        for (ConstraintViolation<?> next : set) {
            message.addNotification(((PathImpl) next.getPropertyPath())
                    .getLeafNode().getName() + ":" + next.getMessage());
        }
        return message;
    }

    private HttpEntity<Message> handleSecurityException(Exception ex) {
        log.error(ERROR, ex);
        Message message = new Message(MessageType.Access_Denied,
                MessageType.Access_Denied.getDescription(), singletonList(ex.getMessage()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.FORBIDDEN);
    }
}
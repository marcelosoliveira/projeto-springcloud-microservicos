package com.product.catalog.handler;

import com.product.catalog.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ProductProblem.Field> fields = new ArrayList<>();
        List<ObjectError> getAllErrors = ex.getBindingResult().getAllErrors();
        getAllErrors.forEach(error -> {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new ProductProblem.Field(name, message));
        });

        ProductProblem productProblem = new ProductProblem();
        productProblem.setStatus(status.value());
        productProblem.setDateTime(LocalDateTime.now());
        productProblem.setTitle("Campo inválido. Preencha corretamente e tente novamente!");
        productProblem.setFields(fields);

        return handleExceptionInternal(ex, productProblem, headers, status, request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handlerProductNotFoundException(ProductNotFoundException ex,
                                                                  WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProductProblem productProblem = new ProductProblem();
        productProblem.setDateTime(LocalDateTime.now());
        productProblem.setStatus(status.value());
        productProblem.setTitle(ex.getMessage());

        return handleExceptionInternal(ex, productProblem, new HttpHeaders(), status, request);
    }
}

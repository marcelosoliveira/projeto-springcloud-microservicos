package com.shopping.cart.handler;

import com.shopping.cart.exception.ShoppingCartNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ShoppinpCartExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handlerHttpClientErrorException(HttpClientErrorException ex,
                                                                  WebRequest request) {
        ShoppingProblem shoppingProblem = new ShoppingProblem();
        shoppingProblem.setProductException(ex.getMessage());
        shoppingProblem.setTitle("Produto não encontrado!");
        shoppingProblem.setStatus(ex.getStatusCode().value());
        shoppingProblem.setDateTime(LocalDateTime.now());

        return handleExceptionInternal(ex, shoppingProblem, new HttpHeaders(), ex.getStatusCode(), request);
    }

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<Object> handlerShoppingCartException(ShoppingCartNotFoundException ex,
                                                               WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ShoppingProblem shoppingProblem = new ShoppingProblem();
        shoppingProblem.setTitle(ex.getMessage());
        shoppingProblem.setStatus(status.value());
        shoppingProblem.setDateTime(LocalDateTime.now());

        return handleExceptionInternal(ex, shoppingProblem, new HttpHeaders(), status, request);
    }
}

package com.slyszmarta.bemygoods.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, AlbumNotFoundException.class})
    public final ResponseEntity handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException ue = (UserNotFoundException) ex;

            return handleException(ex, request);
        } else if (ex instanceof AlbumNotFoundException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            AlbumNotFoundException albumNotFoundException = (AlbumNotFoundException) ex;

            return handleException(albumNotFoundException, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleException(ex, request);
        }
    }

}

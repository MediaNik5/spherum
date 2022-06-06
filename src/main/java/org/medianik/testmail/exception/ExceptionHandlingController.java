package org.medianik.testmail.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlingController{

    @ExceptionHandler({RequestException.class})
    public ResponseEntity<?> commonException(@NotNull RequestException exception){
        return ResponseEntity
            .status(exception.getCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                    "timestamp": "%s",
                    "status": %d,
                    "message": "%s",
                    "path": "%s"
                }
                """.formatted(
                    LocalDateTime.now(),
                    exception.getCode(),
                    exception.getMessage(),
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI()
                )
            );
    }
}

package com.ide.back.exception;

import com.ide.back.api.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(IdeApplicationException.class)
    public ResponseEntity<ApiResponse<String>> applicationHandler(IdeApplicationException e) {
        log.error("error occurs {}", e.toString());

        ApiResponse<String> response = new ApiResponse<>(false, e.getApiError());

        return ResponseEntity.status(e.getApiError().getType().getHttpStatusCode())
                .body(response);
    }
}

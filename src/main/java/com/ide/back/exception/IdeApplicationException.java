package com.ide.back.exception;

import com.ide.back.api.response.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdeApplicationException extends RuntimeException {

    private ApiError apiError;
    private String message;

    public IdeApplicationException(ApiError apiError) {
        this.apiError = apiError;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return apiError.getMessage();
        }

        return String.format("%s. %s", apiError.getMessage(), message);
    }
}

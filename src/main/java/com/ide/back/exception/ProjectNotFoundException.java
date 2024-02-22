package com.ide.back.exception;

import com.ide.back.api.response.ApiError;
import com.ide.back.shared.type.ApiErrorType;

public class ProjectNotFoundException extends IdeApplicationException{

    public ProjectNotFoundException(String message) {
        super(new ApiError(ApiErrorType.NOT_FOUND, "PROJECT_NOT_FOUND", "Project not found: "  + message));
    }
}
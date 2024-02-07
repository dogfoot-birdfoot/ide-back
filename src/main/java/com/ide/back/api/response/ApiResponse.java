package com.ide.back.api.response;

import com.ide.back.shared.ResponseContext;
import com.ide.back.shared.type.ApiErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private boolean success = true;
    private ApiError error;

    private T data;

    private long cost = -1;

    private boolean checkedContext(){
        return ResponseContext.requestAt.get() != null && ResponseContext.requestAt.get() != null;
    }

    public ApiResponse() {
        if(checkedContext()){
            this.cost = System.currentTimeMillis() - ResponseContext.requestAt.get();
        }
    }

    public ApiResponse(boolean success, ApiError error) {
        this.success = success;
        this.error = error;
        if(checkedContext()){
            this.cost = System.currentTimeMillis() - ResponseContext.requestAt.get();
        }
    }

    public ApiResponse(T data) {
        this.data = data;
        if(checkedContext()){
            this.cost = System.currentTimeMillis() - ResponseContext.requestAt.get();
        }
    }

    public ApiResponse(Throwable e, String errorCode, String message){
        this.error = new ApiError(ApiErrorType.UNKNOWN, errorCode, message, e);
        this.success = false;

        if(checkedContext()){
            this.cost = System.currentTimeMillis() - ResponseContext.requestAt.get();
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", error=" + error +
                ", data=" + data +
                ", cost=" + cost +
                '}';
    }
}

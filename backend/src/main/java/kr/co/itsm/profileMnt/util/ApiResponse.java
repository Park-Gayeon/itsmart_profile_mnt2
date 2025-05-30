package kr.co.itsm.profileMnt.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String errorMessage;
    private final List<String> errors;

    private ApiResponse(boolean success, T data, String errorMessage, List<String> errors) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    /** 성공 응답 (데이터 포함) */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    /** 성공 응답 (데이터 없음) */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /** 실패 응답 (단일 메시지) */
    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(false, null, errorMessage, null);
    }

    /** 실패 응답 (단일 메시지 + 데이터 포함) */
    public static <T> ApiResponse<T> error(String errorMessage, T data) {
        return new ApiResponse<>(false, data, errorMessage, null);
    }

    /** 실패 응답 (다중 메시지) */
    public static <T> ApiResponse<T> error(String errorMessage, List<String> errors) {
        return new ApiResponse<>(false, null, errorMessage, errors);
    }
}

package com.teamdefinex.dfxsurvey.dto.result;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {
    private String code;
    private String message;
    private Boolean success;
    private String error;
    private T data;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .success(true)
                .data(data)
                .code("200")
                .message("Operation successful")
                .build();
    }

    public static <T> Result<T> failure(String errorMessage) {
        return Result.<T>builder()
                .success(false)
                .error(errorMessage)
                .code("400") // internal değil business hata çünkü
                .message("Operation failed")
                .build();
    }

    public static <T> Result<T> failure(String code, String message, String error) {
        return Result.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .error(error)
                .build();
    }
}

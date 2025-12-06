package com.sunmight.erp.global;

import org.springframework.http.HttpStatus;

/**
 * 더존 응답 스펙
 *
 * @param <T>
 */
public class ApiResponseDTO<T> {

    private int resultCode;
    private String resultMsg;
    private T resultData;

    public ApiResponseDTO(HttpStatus status, T data) {
        this.resultCode = status.value();
        this.resultMsg = status.getReasonPhrase();
        this.resultData = data;
    }

    // static 은 클래스의 인스턴스에 종속되지 않아 메서드 맨 앞에 제네릭 <T> 사용을 명시해줘야함, 그리고 인자로 받을때 그 타입을 받음
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<T>(HttpStatus.OK, data);
    }

    public static <T> ApiResponseDTO<T> error(HttpStatus status) {
        return new ApiResponseDTO<>(status, null);
    }
}

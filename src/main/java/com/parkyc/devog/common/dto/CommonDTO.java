package com.parkyc.devog.common.dto;

import com.parkyc.devog.common.code.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

public class CommonDTO {

    /**
     * 공통 API Response DTO -API는 무조건 아래의 형태로 반환-
     * @param <T>
     */
    @Getter
    public static class Response<T> {
        private final ResponseCode code;
        private final String message;
        private final T data;

        @Builder
        public Response(ResponseCode code, T data){
            this.code = code;
            this.message = code.getMessage();
            this.data = data;
        }

        public ResponseEntity<Response<T>> toEntity() {
            return ResponseEntity.status(code.getStatus()).body(this);
        }
    }

    /**
     * 공통 Response DTO 팩토리 메서드
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Response<T> success(T data){
        return new Response<T>(ResponseCode.API_SUCCESS, data);
    }

    /**
     * 공통 Response DTO 팩토리 메서드
     * @param code
     * @return
     * @param <T>
     */
    public static <T> Response<T> fail (ResponseCode code){
        return new Response<T>(code, null);
    }

}

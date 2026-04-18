package com.parkyc.devog.config.http;

import com.parkyc.devog.common.dto.CommonDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    /** Controller에서 반환타입을 확인하기 쉽게 하기 위해서 사용하는 Advice
     * ResponseEntity<CommonDTO.Response<DTO>> -> CommonDTO.Response<DTO>로 줄여줌
     * ---
     * API Response를 내려주기 전에 CommonDTO에 있는 ResponseCode를 확인해서
     * httpStatus, message를 세팅하도록 설정.
     */

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return CommonDTO.Response.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof CommonDTO.Response<?> apiResponse) {
            response.setStatusCode(apiResponse.getCode().getStatus());
        }

        return body;
    }
}

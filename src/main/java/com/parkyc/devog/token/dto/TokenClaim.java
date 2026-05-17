package com.parkyc.devog.token.dto;

import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.exception.DevogErrorCode;
import com.parkyc.devog.token.exception.TokenErrorCode;
import com.parkyc.devog.token.exception.TokenException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record TokenClaim(
    Map<String, Object> map
) {

    public TokenClaim(Map<String, Object> map){
        this.map = validObjectMap(map);
    }

    private Map<String, Object> validObjectMap(Map<String, Object> map){
        if (map == null || map.isEmpty()) {
            return Map.of();
        }

        Map<String, Object> copy = new HashMap<>();
        for(String key : map.keySet()){
            Object obj = map.get(key);

            if(!isAllowedType(obj)){
                throw new IllegalArgumentException("TokenClaim가 지원하지 않는 매개변수입니다. > " + obj);
            }

            if(obj instanceof List<?> list){
                copy.put(key, List.copyOf(list));
            } else {
                copy.put(key, obj);
            }
        }

        return Map.copyOf(map);
    }

    private boolean isAllowedType(Object obj){
        if(obj instanceof String){
            return true;
        }
        if(obj instanceof Number){
            return true;
        }
        if(obj instanceof Boolean){
            return true;
        }
        if(obj instanceof List<?> list){
            // List<List<String>> 이런거 허용할 것인가?
            // 재귀를 돌면서 깊은복사를 해줘야 함.
            // 그런데 login말고 JWT를 쓸만한 곳이 있나?
            return list.stream().allMatch(this::isAllowedType);
        }

        return false;
    }

}

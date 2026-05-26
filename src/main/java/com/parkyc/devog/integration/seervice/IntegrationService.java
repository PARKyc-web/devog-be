package com.parkyc.devog.integration.seervice;

import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.exception.DevogErrorCode;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.security.DevogPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final MemberRepository memberRepository;
    private final MemberOAuthRepository oAuthRepository;

    // 계정 및 외부 서비스 연결
    // Think : 각 OAuth 로그인마다 넘어오는 Key : Value가 다르기 때문에 메소드를 하나씩 만들어 줘야 함.
    public void connectGithub(OAuth2AuthenticationToken token,
                                       OAuth2AuthorizedClient client,
                                       DevogPrincipal user){

        Optional<Member> member = memberRepository.findByLoginId(user.loginId());
        if(member.isEmpty()){
            throw new DevogApiException(DevogErrorCode.BUSINESS_ERROR);
            // 나중에 통합연계 관련 에러코드로 수정할 것.
        }

        String provider = token.getAuthorizedClientRegistrationId();
        String aToken = client.getAccessToken().getTokenValue();
        String rToken = (client.getRefreshToken() == null) ? null : client.getRefreshToken().getTokenValue();
        Object id = token.getPrincipal().getAttribute("id");
        Object username = token.getPrincipal().getAttribute("login");

        MemberOAuth auth = MemberOAuth.of(member.get(),
                OAuthProvider.from(provider),
                aToken, rToken,
                String.valueOf(id) , String.valueOf(username));

        oAuthRepository.save(auth);
    }



}

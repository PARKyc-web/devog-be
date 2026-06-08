package com.parkyc.devog.member.service;

import com.parkyc.devog.common.exception.DevogApiException;
import com.parkyc.devog.common.exception.DevogErrorCode;
import com.parkyc.devog.member.domain.code.OAuthProvider;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberOAuth;
import com.parkyc.devog.member.exception.MemberErrorCode;
import com.parkyc.devog.member.exception.MemberException;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.member.service.command.SignUpCommand;
import com.parkyc.devog.member.service.result.SignUpResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입 메소드
    @Transactional
    public SignUpResult signUp(SignUpCommand command){

        boolean check = memberRepository.existsByLoginId(command.loginId());
        if(check){
            throw new MemberException(MemberErrorCode.ALREADY_USED_ID);
        }
        String encodePassword = passwordEncoder.encode(command.password());
        Member member = Member.signup(command.loginId(),
                encodePassword,
                command.nickname());

        memberRepository.save(member);

        return new SignUpResult(member.getMemberId(),
                member.getLoginId(),
                member.getNickname(),
                member.getStatus(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public MemberOAuth getOAuthInfo(String loginId, OAuthProvider provider){
        Optional<Member> oMember = memberRepository.findByLoginId(loginId);
        if(oMember.isEmpty()){
            // Think : 에러코드 정리하기.
            throw new DevogApiException(DevogErrorCode.BUSINESS_ERROR);
        }
        Member member = oMember.get();
        List<MemberOAuth> oauthList = member.getOauths();

        return oauthList.stream()
                .filter(o -> o.getOauthProvider() == provider)
                .findFirst()
                .orElseThrow(() -> new DevogApiException(DevogErrorCode.BUSINESS_ERROR));
    }
}

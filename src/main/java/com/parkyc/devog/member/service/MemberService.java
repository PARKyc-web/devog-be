package com.parkyc.devog.member.service;

import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.exception.MemberErrorCode;
import com.parkyc.devog.member.exception.MemberException;
import com.parkyc.devog.member.repository.MemberRepository;
import com.parkyc.devog.member.service.command.SignUpCommand;
import com.parkyc.devog.member.service.result.SignUpResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 비밀번호 변경 메소드
    public void changePassword(){

    }

    // 닉네임 변경 메소드
    public void changeNickname(){

    }
}

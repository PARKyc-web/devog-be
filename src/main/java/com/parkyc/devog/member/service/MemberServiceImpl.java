package com.parkyc.devog.member.service;

import com.parkyc.devog.common.code.MemberStatus;
import com.parkyc.devog.common.code.ResponseCode;
import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.config.exception.DevogApiException;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.member.domain.dto.MemberDTO;
import com.parkyc.devog.member.domain.entity.Member;
import com.parkyc.devog.member.domain.entity.MemberDetails;
import com.parkyc.devog.member.repository.MemberDetailsRepository;
import com.parkyc.devog.member.repository.MemberOAuthRepository;
import com.parkyc.devog.member.repository.MemberRepository;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberDetailsRepository detailsRepository;
    private final MemberOAuthRepository oAuthRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public CommonDTO.Response<MemberDTO.SignUp> signUp(MemberDTO.SignUp signUp) {

        boolean isDup = memberRepository.existsByLoginId(signUp.getLoginId());
        if(isDup){
            throw new DevogApiException(ResponseCode.DUPLICATE_LOGIN_ID);
        }

        Member member = Member.builder()
                .loginId(signUp.getLoginId())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .status(MemberStatus.ACTIVE)
                .build();
        memberRepository.saveAndFlush(member);

        MemberDetails details = MemberDetails.builder()
                .member(member)
                .nickname(createRandNickname())
                .build();
        detailsRepository.saveAndFlush(details);

        return CommonDTO.success(signUp);
    }

    @Override
    public String createRandNickname() {
        String[] prefix = {"용감한", "냉정한", "가벼운", "무거운", "뚱뚱한", "비열한", "냉소적인", "계산적인"};
        String[] suffix = {"강아지", "고양이", "부엉이", "펭귄", "의자", "가방", "책상", "안경"};

        Random random = new Random(new Date().getTime());
        int preRand = random.nextInt(prefix.length);
        int sufRand = random.nextInt(suffix.length);

        return prefix[preRand] + " " + suffix[sufRand];
    }
}

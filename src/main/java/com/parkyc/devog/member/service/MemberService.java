package com.parkyc.devog.member.service;

import com.parkyc.devog.common.dto.CommonDTO;
import com.parkyc.devog.login.domain.dto.LoginDTO;
import com.parkyc.devog.member.domain.dto.MemberDTO;

public interface MemberService {

    CommonDTO.Response<MemberDTO.SignUp> signUp(MemberDTO.SignUp signUp);

    String createRandNickname();

}

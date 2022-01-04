package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.form.LoginForm;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    // null이면 로그인 실패
    public Member login(LoginForm loginForm) throws Exception {
        return memberRepository.findByLoginId(loginForm.getLoginId())
                .filter( m->m.getPassword().equals(loginForm.getPassword()))
                .orElse(null);
    }
}

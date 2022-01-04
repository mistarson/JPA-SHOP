package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home() {
        log.info("홈 컨트롤러");
        return "home";
    }

    @GetMapping("/")
    public String homelogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) throws Exception {

        //로그인 실패한 사용자
        if (memberId == null) {
            return "home";
        }

        //로그인 성공한 사용자
        Member loginMember = memberService.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}

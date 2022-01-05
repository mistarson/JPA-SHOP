package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.form.LoginForm;
import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.session.SessionConst;
import jpabook.jpashop.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    //    @PostMapping("/login")
    public String loginV1(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) throws Exception {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        //쿠키에 시간 정보를 주지 않으면 세션 쿠키
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";
    }

    //    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) throws Exception {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        //세션 관리자를 통해 세션을 생성하고 회원 데이터를 보관

        //쿠키에 시간 정보를 주지 않으면 세션 쿠키
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm loginForm,
                          BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request) throws Exception {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession(); //세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성해서 반환
        /**
         * getSession(default: true)
         * true  : 세션이 있으면 기존 세션을 반환, 없으면 새로 생성
         * false : 세션이 있으면 기존 세션을 반환, 없으면 null 반환 (신규 session X)
         */
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); //세션에 로그인 회원 정보를 보관

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm loginForm,
                          BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          @RequestParam(defaultValue = "/") String redirectURL) throws Exception {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession(); //세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성해서 반환
        /**
         * getSession(default: true)
         * true  : 세션이 있으면 기존 세션을 반환, 없으면 새로 생성
         * false : 세션이 있으면 기존 세션을 반환, 없으면 null 반환 (신규 session X)
         */
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); //세션에 로그인 회원 정보를 보관

        return "redirect:" + redirectURL;
    }

    //    @PostMapping("/logout")
    public String logoutV1(HttpServletResponse response) {
        expireCookie(response, "memberId");

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request, HttpServletResponse response) {

        sessionManager.expire(request, response);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

package jpabook.jpashop.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    public static final String SETTION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStoer = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     * sessionId 생성 (임의의 추정 불가능한 랜덤 값)
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */
    public void createSession(Object value, HttpServletResponse response) {

        //sessionId 생성하고, 값을 세션에 저장 (임의의 추정 불가능한 랜덤 값)
        String sessionId = UUID.randomUUID().toString();
        sessionStoer.put(sessionId, value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SETTION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);


    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {

        Cookie sessionCookie = findCookie(request, SETTION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStoer.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request, HttpServletResponse response) {
        Cookie sessionCookie = findCookie(request, SETTION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStoer.remove(sessionCookie.getValue());
            Cookie cookie = new Cookie(SETTION_COOKIE_NAME, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        ;

    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SETTION_COOKIE_NAME))
                .findAny().orElse(null);
    }
}

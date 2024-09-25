package com.linkdom.MyCustomInterceptor;

import com.linkdom.services.userServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class intercept implements HandlerInterceptor { // Typo

    private final userServices services;

    public intercept(userServices services) {
        this.services = services;
    }

    @Override
    public boolean preHandle(HttpServletRequest request
                             ,HttpServletResponse response, Object handler) throws Exception {


        Cookie[] cookies = request.getCookies();
        String email = null;
        String password = "";
        String id = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email")) {
                    email = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }else if (cookie.getName().equals("id")) {
                    id = cookie.getValue();
                }
            }
            if (id==null){
                response.sendRedirect("/login");
            }
            if (!password.equals(services.getUser(email).getPassword())) {
                System.out.println(password+" "+services.getUser(email).getPassword());
                response.sendRedirect("/login");
            }
        }else {
            response.sendRedirect("login");
        }
        System.out.println("prehandler");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

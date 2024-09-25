package com.linkdom.controller;

import com.linkdom.Entity.User;
import com.linkdom.services.userServices;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class mainController {

    private final userServices service;

    public mainController(userServices service) {
        this.service = service;
    }

    //Login Handler
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @PostMapping("/auth")
    public String authentication(@RequestParam String email, @RequestParam String password, Model model, RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        if (email.contains("@")) {
            user = service.getUser(email);
        } else {
            user = service.findByUsername(email);
        }
        if(encoder.matches(password,user.getPassword())){
            Cookie cookie = new Cookie("email", user.getEmail());
            Cookie cookie2 = new Cookie("password", user.getPassword());
            Cookie cookie3 = new Cookie("id", user.getId().toString());
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie2.setMaxAge(60 * 60 * 24 * 30);
            cookie3.setMaxAge(60 * 60 * 24 * 30);
            httpServletResponse.addCookie(cookie);
            httpServletResponse.addCookie(cookie2);
            httpServletResponse.addCookie(cookie3);

            return "redirect:/profile";
        }else {
            return "redirect:/login";
        }
    }


    //SignUp Handler
    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/onboard",method = RequestMethod.POST)
    public String onBoard(User user, Model model, RedirectAttributes attributes, HttpServletResponse response){
        if (user.getNewsletter() == null){
            user.setNewsletter("false");
        } else if (user.getNewsletter().equals("on")) {
            user.setNewsletter("true");
        }

        if (service.isUserExist(user.getEmail())){
            if (service.isUserNameExist(user.getUsername())){
                model.addAttribute("usernameMessage", "memeName isn't unique my fren's");
            }
            model.addAttribute("message", "This email already exists");
            return "signup";
        } else if (service.isUserNameExist(user.getUsername())) {
            model.addAttribute("usernameMessage", "memeName isn't unique my fren's");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Cookie cookie = new Cookie("email", user.getEmail());
        cookie.setMaxAge(60 * 60 * 24 * 30); //seconds in a month (approx)
        response.addCookie(cookie);
        service.saveUser(user);
        return "redirect:/login";
    }

    @RequestMapping("verify/{email}")
    public String verify(Model model, @PathVariable String email, @CookieValue(value = "email",
            defaultValue = "emailNone")String emailCookie,
            @CookieValue(value = "isOtpVerify",defaultValue = "false")String isOtpVerify){
        if (isOtpVerify.equals("false")){
            return "redirect:/error";
        }
        System.out.println(emailCookie);
        return "verify";
    }

    @RequestMapping("/profile")
    public String profile(Model model){
        return "profile";
    }

}

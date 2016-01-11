package ru.tsystems.railway.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.tsystems.railway.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HttpSession session = req.getSession();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!userEmail.equals("anonymousUser")) {
                Double balance = accountService.getUserByEmail(userEmail).getBalance();
                session.setAttribute("balance", balance);
            }
        }
        return super.preHandle(req, resp, handler);
    }

}

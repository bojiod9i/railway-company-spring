package ru.tsystems.railway.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.tsystems.railway.domain.accounts.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(UserRole.ROLE_CLIENT.name())) {
                response.sendRedirect("");
            } else if (grantedAuthority.getAuthority().equals(UserRole.ROLE_EMPLOYEE.name())) {
                response.sendRedirect("employee/index");
            } else if (grantedAuthority.getAuthority().equals(UserRole.ROLE_OWNER.name())) {
                response.sendRedirect("owner/index");
            }
        }

    }

}

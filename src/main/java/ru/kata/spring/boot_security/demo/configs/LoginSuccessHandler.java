package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        response.sendRedirect(isAdmin(userDetails.getAuthorities()) ? "/admin" : "/user");
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorityList){ //выясняем, является ли пользователь админом
        for (GrantedAuthority authority : authorityList) {
            if (authority instanceof Role) System.out.println(authority);
            String authStr = authority.getAuthority().toString();
            if(!authStr.contains("ROLE_")) continue;
            if(authStr.contains("_ADMIN")) return true;
        }
        return false;
    }

}

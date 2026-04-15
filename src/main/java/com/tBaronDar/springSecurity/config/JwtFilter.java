package com.tBaronDar.springSecurity.config;

import com.tBaronDar.springSecurity.service.JwtService;
import com.tBaronDar.springSecurity.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter will be called for every request
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext ctx;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        //check if the correct header exists if yes keep the relevant data
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //the actual token starts after "Bearer ", so 7th position
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }

        //if we have correct header and no authentication
        //get user details from db
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //this method gets username, searches the db and returns UserDetails obj
            UserDetails userDetails = ctx.getBean(MyUserDetailsService.class).loadUserByUsername(userName);

            //if method below returns true do the following steps
            if (jwtService.validateToken(token, userDetails)) {
                //create a new token with the user details
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //then link the token to the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //then inform the context that we are validated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //continue to the next filter....
        filterChain.doFilter(request, response);
    }
}

package marln.corp.ai.service.marln_user_service.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("INside filter");
        // get the token from request
        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        if( requestToken!=null && requestToken.startsWith("Bearer"))
        {
            token = requestToken.substring(7);
            try {
                userName = jwtTokenHelper.getUserNameFromToken(token);
            }catch (IllegalArgumentException e)
            {
                System.out.println("IllegalArgumentException : " +e.getMessage());
            }
            catch (ExpiredJwtException e)
            {
                System.out.println("ExpiredJwtException : " +e.getMessage());
            }
            catch (MalformedJwtException e)
            {
                System.out.println("MalformedJwtException : " +e.getMessage());
            }
        }else {
            System.out.println("JWT Token does not starts with Bearer");
        }
        //once we have retrived the token, we will validate now

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication() ==null)
        {
            UserDetails userDetails = null/*userDetailsService.loadUserByUsername(userName)*/;
            if(jwtTokenHelper.validateToken(token, userDetails))
            {
                //the token is valid and now we will authenticate

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userName, null, null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                System.out.println("Invalid token");
            }
        }else {
            System.out.println("Username is null or context is not null");
        }

       filterChain.doFilter(request,response);
    }
}

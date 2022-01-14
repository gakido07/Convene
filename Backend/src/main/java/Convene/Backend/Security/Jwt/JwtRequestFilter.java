package Convene.Backend.Security.Jwt;

import Convene.Backend.User.AppUserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JwtUtil jwtUtil;

    private static List<String> skipFilterUrls = Arrays.asList("" +
            "/sign-up/**");

    String email = "";
    String token = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            token = requestTokenHeader.substring(7);
            try{
                email = jwtUtil.getEmailFromToken(token);
            }
            catch (IllegalArgumentException exception){
                System.out.println("Unable to get Jwt Token");
            }
            catch (ExpiredJwtException exception){
                System.out.println("Jwt Token expired");
            }
        }
        else{
            logger.warn("Jwt Token does not begin with bearer");
        }

        if(email.length() > 0 && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = appUserService.loadUserByUsername(email);
            if(jwtUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return skipFilterUrls.stream()
                .anyMatch(
                        url -> new AntPathRequestMatcher(url)
                                .matches(request)
                );
    }
}

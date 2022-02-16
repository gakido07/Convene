package Convene.Backend.Security.Auth.Jwt;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.AppUser.AppUserService;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    String email = "";
    String token = "";
    final String userRoute = "/user/**/profile";
    private final List<String> skipFilterUrls = Arrays.asList("/auth/**");
    private final AppUserService appUserService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(AppUserService appUserService, JwtUtil jwtUtil) {
        this.appUserService = appUserService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        token = request.getHeader("Authorization").substring(7);

        try{
            email = jwtUtil.getEmailFromToken(token);
        }
        catch (IllegalArgumentException exception){
            logger.error("Unable to get jwt");
        }
        catch (ExpiredJwtException exception){
            logger.warn("Token expired");
        }

        if(email.length() > 0 && SecurityContextHolder.getContext().getAuthentication() == null){
            AppUser appUser = appUserService.findAppUserByEmail(email).orElseThrow(AuthExceptions.UserNotFoundException::new);
            if(jwtUtil.validateToken(token, appUser) && userRouteProtection(request, appUser)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        appUser.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info(request.getRequestURI() + " accessed by " + appUser.getEmail());
            }
        }
        filterChain.doFilter(request, response);
    }

    protected Boolean userRouteProtection(HttpServletRequest request, AppUser appUser) {
        if (new AntPathRequestMatcher(userRoute).matches(request)) {
            Long id = extractIdFromUrl(request.getRequestURI());
            return id == appUser.getId();
        }
        return true;
    }

    private long extractIdFromUrl(String url) {
        if(!url.contains("user")) {
            throw new AuthExceptions.InvalidAuthCredentialsException();
        }
        return  Long.valueOf(url.split("/")[2]);
    }
}

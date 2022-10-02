package uz.darico.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.darico.exception.exception.UniversalException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String secretWord;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretWord.getBytes());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            if (token.startsWith("Bearer")) {
                token = token.substring(7);
                if (validationToken(token)) {
                    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//                    Long sessionID = claims.get("id", Long.class);
//                    SessionUser sessionUser = utilsService.getUserInfo(sessionID);
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sessionUser, null, sessionUser.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new UniversalException("Access Denied", HttpStatus.FORBIDDEN);
                }
            }
        }
        filterChain.doFilter(request, response);
    }


    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

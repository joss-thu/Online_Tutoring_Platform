package de.thu.thutorium.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter for JWT authentication. This filter intercepts HTTP requests to validate JWT tokens and
 * set the authentication context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  /**
   * Filters incoming HTTP requests to validate JWT tokens.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @param filterChain the filter chain
   * @throws ServletException if a servlet error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    // missing authorization, return
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    // Get the payload
    jwt = authHeader.substring(7);
    try {
      // extract username/email
      userEmail = jwtService.extractUsername(jwt);
    } catch (ExpiredJwtException e) {
      // Token expired
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Token has expired");
      return;
    }
    // Extract roles
    List<String> roles = jwtService.extractRoles(jwt);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (userEmail != null && authentication == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      // Validate the token (not the password, just username)
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                // userDetails.getAuthorities() // This will still have the roles
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    try {
      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }
}

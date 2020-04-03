package edu.marconivr.jacopo.microblog.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthFilter extends AbstractAuthenticationProcessingFilter
{
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";


    @Override
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException 
    {
        // extracts from the request header the Authorization token 
        String token = Optional
                .ofNullable( request.getHeader( AUTHORIZATION ) )   // from the header, get the auth field
                .map( v -> v.replace(BEARER, "" ).trim() )          // retrieve only the token
                .orElseThrow( () -> new BadCredentialsException("Missing Token") );

        // authenticates. Since a token its used, both username and password are the token
        Authentication auth = new UsernamePasswordAuthenticationToken(token, token);

        return getAuthenticationManager().authenticate(auth);
        
    }


    //* NECESSARY to redirect, after a successful authentication, to the request URL
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException 
    {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }


    // default constructor
    public TokenAuthFilter(RequestMatcher defaultFilterProcessesUrl) 
    {
        super(defaultFilterProcessesUrl);
    }

}
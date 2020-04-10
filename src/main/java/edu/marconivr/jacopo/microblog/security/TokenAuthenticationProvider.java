package edu.marconivr.jacopo.microblog.security;

import static edu.marconivr.jacopo.microblog.entities.User.getHashedPasswordOf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.security.services.IAuthenticationService;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{
    @Autowired
    private IAuthenticationService userAuthService;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException 
    {
        Object token = authentication.getCredentials();

        return 
            Optional
            .ofNullable(token)
            .flatMap
            ( 
                t -> 
                    Optional
                    .of(userAuthService.authenticateByToken(String.valueOf(t)))
                    .map
                    (   
                        // generates UserDetails from microblog' s User
                        usr -> 
                            User.builder()
                            .username(usr.username)
                            .password( getHashedPasswordOf(usr) ) // microblog' s User 
                            .roles("user")
                            .build()
                    )
            )
            .orElseThrow(() -> new BadCredentialsException("Invalid token"));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) 
        throws AuthenticationException 
    {
        // this is a must implement method in the class signature. It does nothing because we don't need it to.
        return;

    }

}
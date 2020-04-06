package edu.marconivr.jacopo.microblog.security.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import edu.marconivr.jacopo.microblog.entities.User;

public interface IUserAuthenticationService
{
    /**
     * generates an authentication token 
     * @param username
     * @param password
     * @return
     * @throws BadCredentialsException
     */
    String login( String username, String password ) throws BadCredentialsException;

    /**
     * authenticates by a token
     * @param token
     * @return
     * @throws AuthenticationException
     */
    User authenticateByToken( String token ) throws AuthenticationException;

    /**
     * invalidates an user's token
     * @param username
     */
    void logout( String username );
    
    /**
     * invalidates a token, effectively logging a user out
     * @param token
     */
    void invalidateToken(String token);
}
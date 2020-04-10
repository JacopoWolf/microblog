package edu.marconivr.jacopo.microblog.security.services.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.security.services.IAuthenticationService;
import edu.marconivr.jacopo.microblog.security.services.IPasswordService;
import edu.marconivr.jacopo.microblog.services.IUsersService;

@Service
public class UUIDAuthenticationService implements IAuthenticationService 
{
    @Autowired
    private IUsersService userService;
    
    @Autowired
    private IPasswordService passwordService;

    @Override
    public String login(String username, String password) throws BadCredentialsException 
    {
        User user = this.userService.getByNickname(username);


        if ( ! passwordService.verify(user, password) )
            throw new BadCredentialsException("Wrong password");

        String token; 
        
        do 
        {
            token = UUID.randomUUID().toString();
        }
        while( this.userService.getByToken(token) != null );
        
        
        User.setTokenOf( user,token );

        userService.updateUser(user);

        return token;

    }

    @Override
    public User authenticateByToken(String token) throws AuthenticationException 
    {
        User user = this.userService.getByToken(token);
        if (user == null)
            throw new BadCredentialsException("invalid token");

        return user;
    }

    @Override
    public void logout(String username) 
    {
        User user = this.userService.getByNickname(username);

        User.setTokenOf(user, null);

        this.userService.updateUser(user);
    }

    @Override
    public void invalidateToken(String token)
    {
        User user = this.userService.getByToken(token);
        
        User.setTokenOf(user, null);

        this.userService.updateUser(user);
    }

}
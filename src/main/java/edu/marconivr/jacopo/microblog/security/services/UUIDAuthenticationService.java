package edu.marconivr.jacopo.microblog.security.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.services.implementations.UsersService;

@Service
public class UUIDAuthenticationService implements IUserAuthenticationService 
{
    @Autowired
    private UsersService userService;
    

    @Override
    public String login(String username, String password) throws BadCredentialsException 
    {
        User user = this.userService.getByNickname(username);

        if ( !User.getPasswordOf(user).contentEquals(password) ) 
            throw new BadCredentialsException("Wrong password");

        String token = UUID.randomUUID().toString();
        
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

}
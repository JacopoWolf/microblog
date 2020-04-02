package edu.marconivr.jacopo.microblog.services.implementations;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;
import edu.marconivr.jacopo.microblog.services.IUsersService;

@Service
public class UsersService implements IUsersService 
{
    @Autowired
    private UserRepository usersRepo;

    @Override
    public List<User> getAll() 
    {
        return usersRepo.findAll();
    }

    @Override
    public User getByNickname(String nickname) 
    {
        return usersRepo.findByUsername(nickname);
    }

    @Override
    public void createNew(User user) 
    {
        if ( !user.username.matches("[a-zA-Z0-9_-]{3,30}" ) )
            throw new WebApplicationException( Response.Status.NOT_ACCEPTABLE );

        if ( usersRepo.findByUsername(user.username) != null )
            throw new WebApplicationException( Response.Status.CONFLICT );

        usersRepo.saveAndFlush( user );
    }

}
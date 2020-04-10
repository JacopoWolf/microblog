package edu.marconivr.jacopo.microblog.services.implementations;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repositories.IUserRepository;
import edu.marconivr.jacopo.microblog.services.IUsersService;
import edu.marconivr.jacopo.microblog.services.IValidationService;

@Service
public class UsersService implements IUsersService 
{
    @Autowired
    IValidationService validationService;

    @Autowired
    private IUserRepository usersRepo;

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
    public User getByToken(String token) 
    {
        return usersRepo.findByToken(token);
    }

    @Override
    public void createNew(User user) 
    {
        if ( ! validationService.validateUsername(user.username) )
            throw new WebApplicationException( Response.Status.NOT_ACCEPTABLE );

        if ( usersRepo.findByUsername(user.username) != null )
            throw new WebApplicationException( Response.Status.CONFLICT );

        usersRepo.saveAndFlush( user );
    }


    @Override
    public void updateUser(User user) 
    {
        this.usersRepo.saveAndFlush(user);
    }

}
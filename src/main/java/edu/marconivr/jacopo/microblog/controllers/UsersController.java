package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;

@Component
@Path("/users")
public class UsersController 
{

    @Autowired
    private UserRepository usersRepo;

    @GET @Path( "/get" )
    @Produces( "application/json" )
    public List<User> getallusers() 
    {
        return usersRepo.findAll();
    }

}
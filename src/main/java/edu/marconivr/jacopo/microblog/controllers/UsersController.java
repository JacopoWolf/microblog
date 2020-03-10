package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;

@Component
@Path("/user")
public class UsersController 
{

    @Autowired
    private UserRepository usersRepo;

    @GET
    @Produces( "application/json" )
    public List<User> getallusers() 
    {
        return usersRepo.findAll();
    }

    @GET @Path("/{id}")
    @Produces( "application/json" )
    public User getById( @PathParam( "id" ) Long id )
    {
        return usersRepo.findById(id).get();
    }

}
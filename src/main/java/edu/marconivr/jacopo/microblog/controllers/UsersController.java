package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;

@Component
@Path("/user")
public class UsersController 
{

    @Autowired
    private UserRepository usersRepo;

    @GET @Path("/all")
    @Produces("application/json")
    public List<User> getallusers() 
    {
        return usersRepo.findAll();
    }

    @GET
    @Produces("application/json")
    public User getById( @QueryParam("name") String username )
    {
        if (  username == null || username.isEmpty() )
           throw new WebApplicationException( Response.Status.BAD_REQUEST );

        return usersRepo.findByUsername(username);
    }

    @PUT
    @Consumes("application/json")
    public Response createUser( User user )
    {
        if (user == null)
            return Response.status(HttpStatus.NOT_FOUND.value()).build();

        usersRepo.saveAndFlush( user );
        return Response.status(HttpStatus.CREATED.value()).build();
    }


}
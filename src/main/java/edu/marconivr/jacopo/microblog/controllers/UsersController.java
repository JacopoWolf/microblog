package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component @Path("/users")
@Api(tags = {"user"} )
public class UsersController 
{

    @Autowired
    private UserRepository usersRepo;

    @GET @Path("/all")
    @Produces("application/json")
    @ApiOperation(value = "returns all registered users")
    public List<User> getallusers() 
    {
        return usersRepo.findAll();
    }

    @GET
    @Produces("application/json")
    @ApiOperation( value = "gets an user by its id" )
    public User getById( @ApiParam @QueryParam("name") String username )
    {
        if (  username == null || username.isEmpty() )
           throw new WebApplicationException( Response.Status.BAD_REQUEST );

        return usersRepo.findByUsername(username);
    }

    @POST
    @Consumes("application/json")
    @ApiOperation("generates a new user")
    public Response createUser( @ApiParam User user )
    {
        if (user == null)
            return Response.status( Response.Status.BAD_REQUEST ).build();

        if (usersRepo.findByUsername( user.username ) == null)
        {
            usersRepo.saveAndFlush( user );
            return Response.status( Response.Status.CREATED ).build();
        }
        else
        {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


}
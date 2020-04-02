package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.services.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component @Path("/users")
@Api(tags = {"user"} )
public class UsersController 
{
    @Autowired
    private IUsersService usersService;


    @GET @Path("/all")
    @Produces("application/json")
    @ApiOperation(value = "returns all registered users")
    public List<User> getAllUsers() 
    {
        return this.usersService.getAll();
    }

    @GET
    @Produces("application/json")
    @ApiOperation( value = "gets an user by its id" )
    public User getByUsername( @ApiParam @QueryParam("name") String username )
    {
        if (  username == null || username.isEmpty() )
           throw new WebApplicationException( Response.Status.BAD_REQUEST );

        return this.usersService.getByNickname(username);
    }

    @POST
    @Consumes("application/json")
    @ApiOperation("generates a new user")
    public Response createUser( @ApiParam User user )
    {
        if (user == null)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        this.usersService.createNew(user);
        return Response.status( Response.Status.CREATED ).build();
    }

}
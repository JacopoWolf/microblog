package edu.marconivr.jacopo.microblog.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.security.services.IUserAuthenticationService;
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

    @Autowired
    private IUserAuthenticationService userAuthService;

    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    @ApiOperation( value = "gets the user's info after an authentication" )
    public User getInfoWithToken( @HeaderParam("Authorization") String token )
    {
        try
        {
            return this.userAuthService.authenticateByToken(token);
        }
        catch ( AuthenticationException authE )
        {
            throw new WebApplicationException( Response.Status.UNAUTHORIZED );
        }
    }


    //todo rework this crap
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
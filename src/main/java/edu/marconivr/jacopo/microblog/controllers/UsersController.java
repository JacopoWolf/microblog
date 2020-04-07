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
import edu.marconivr.jacopo.microblog.security.services.IPasswordValidationService;
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

    @Autowired
    private IPasswordValidationService passwordValidation;

    @GET
    @Consumes("text/plain")
    @Produces("application/json")
    @ApiOperation( value = "gets the user's info after an authentication" )
    public User getInfoWithToken( @ApiParam @HeaderParam("Authorization") String token )
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


    @POST
    @Consumes( "application/json" )
    @ApiOperation(value = "registers a new user with the given info and the selected password. Doesn't log in.")
    public Response registerUser( @ApiParam User user, @ApiParam @HeaderParam("Password") String password )
    {
        if ( user == null || password == null)
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        
        if ( !this.passwordValidation.validatePassword(password) )
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);

        User.setPasswordOf(user, password);
        this.usersService.createNew(user);

        return Response.noContent().build();
    }

}
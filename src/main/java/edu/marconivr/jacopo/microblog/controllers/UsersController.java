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
import edu.marconivr.jacopo.microblog.security.services.IAuthenticationService;
import edu.marconivr.jacopo.microblog.security.services.IPasswordService;
import edu.marconivr.jacopo.microblog.services.IUsersService;
import edu.marconivr.jacopo.microblog.services.IValidationService;
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
    private IAuthenticationService userAuthService;

    @Autowired
    private IValidationService validationService;

    @Autowired
    private IPasswordService passwordService;
    

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
        
        if ( ! this.validationService.validatePassword(password) )
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);

        // updates the user's password
        this.passwordService.setCredentialsOf(user, password);

        // persists the user in the database
        this.usersService.createNew(user);


        return Response.noContent().build();
    }

}
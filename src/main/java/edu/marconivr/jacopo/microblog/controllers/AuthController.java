package edu.marconivr.jacopo.microblog.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.security.services.IUserAuthenticationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//todo document

@Component
@Path("/auth")
public class AuthController
{
    @Autowired
    private IUserAuthenticationService authService;


    @ApiOperation( value = "logs in the specified user. Uses a plaintext format. Returns the access token." )
    @POST @Path("/login")
    @Consumes( "text/plain" )
    @Produces("text/plain")
    public String login 
    ( 
        @ApiParam( value = "information necessary to login", format = "{username};{password}" )
        String value 
    )
    {
        String[] values = value.split(";");

        String username = values[0];
        String password = values[1];

        try
        {
            return authService.login(username, password);
        }
        catch (BadCredentialsException bde)
        {
            throw new WebApplicationException( Response.Status.UNAUTHORIZED );
        }
    }

    @ApiOperation( value = "invalidates the specific token" )
    @POST @Path("/logout")
    @Consumes("text/plain")
    public Response logout( String token )
    {
        this.authService.invalidateToken(token);

        return Response.noContent().build();

    }
    

}
package edu.marconivr.jacopo.microblog.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import edu.marconivr.jacopo.microblog.security.services.IUserAuthService;

//todo document

@Component
@Path("/auth")
public class AuthController
{
    @Autowired
    private IUserAuthService authService;


    @POST @Path("/login/{name}")
    @Consumes( "text/plain" )
    @Produces("text/plain")
    public Object login ( @PathParam("name") String username, String password )
    {
        try
        {
            return authService.login(username, password);
        }
        catch (BadCredentialsException bde)
        {
            throw new WebApplicationException( Response.Status.UNAUTHORIZED );
        }
    }

}
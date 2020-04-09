package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.security.services.IAuthenticationService;
import edu.marconivr.jacopo.microblog.services.IPostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component @Path("/posts")
@Api(tags =  { "post" })
public class PostsController
{
    @Autowired
    private IPostsService postsService;

    @Autowired
    private IAuthenticationService authService;

    @GET 
    @Produces("application/json")
    @ApiOperation(value = "Returns posts based on specific parameters.")
    public List<Post> getAllBasedOn
    ( 
        @ApiParam( value = "select posts from the user with this specific name" )
        @QueryParam("from")         
        String  username, 
        
        @ApiParam ( value = "how many posts in this page", defaultValue = "1")
        @QueryParam("count")        
        int count , 

        @ApiParam ( value = "index of the results page", defaultValue = "0" )
        @QueryParam("page")         
        int page, 

        @ApiParam (value = "number of characters in the posts content. If not set, returns the whole content.")
        @QueryParam("limitcontent") 
        int limit 
    )
    {
        // preliminary checks
        if ( count < 1 || page < 0 || limit < 0)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        return this.postsService.getPage(username, page, count, limit);

    }

    @GET @Path("/{id}") 
    @Produces("application/json")
    @ApiOperation( value = "Returns a post by its Id" )
    public Post getById 
    ( 
        @ApiParam( value = "the id of the post you want" )
        @PathParam("id") 
        Long id 
    )
    {
        // preliminary checks
        if ( id == null )
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        return this.postsService.getById(id);

    }
    

    @POST
    @Consumes("application/json")
    @ApiOperation( "Create a new post" )
    public Response createNew 
    ( 
        @HeaderParam("Authorization")
        String token, 

        @ApiParam (value = "the post to submit")
        Post post 
    )
    {
        if (post == null )
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        try
        {
            User user = this.authService.authenticateByToken(token);
            this.postsService.createNew( user.username, post );  
            return Response.status(Response.Status.CREATED).build();
        }
        catch( Exception e )
        {
            throw new WebApplicationException( Response.Status.UNAUTHORIZED );
        }        
    }

}
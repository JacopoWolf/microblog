package edu.marconivr.jacopo.microblog.controllers;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.PostsRepository;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component @Path("/posts")
@Api(tags =  { "post" })
public class PostsController
{
    @Autowired
    private UserRepository usersRepo;
    
    @Autowired
    private PostsRepository postsRepo;

    @GET 
    @Produces("application/json")
    @ApiOperation(value = "Returns posts based on specific parameters.")
    public List<Post> getAllBasedOn
    ( 
        @ApiParam( value = "select posts from the user with this specific name" )
        @QueryParam("from")         String  username, 
        @ApiParam ( value = "how many posts in this page", defaultValue = "1")
        @QueryParam("count")        int     count , 
        @ApiParam ( value = "index of the results page", defaultValue = "0" )
        @QueryParam("page")         int     page, 
        @ApiParam (value = "number of characters in the posts content. If not set, returns the whole content.")
        @QueryParam("limitcontent") int     limit 
    )
    {
        // preliminary checks
        if (count < 1)
            count = 1;
        
        if (page < 0 || limit < 0)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        List<Post> posts;

        if ( username == null || username.isEmpty())
        {
            posts = postsRepo.findAll(PageRequest.of(page, count, Sort.by("date").descending())).toList();
        }
        else
        {
            User usr = usersRepo.findByUsername(username);
            posts = postsRepo.findByAuthor(usr, PageRequest.of(page, count, Sort.by("date").descending()));
        }

        if (limit > 0)
            posts.stream()
            .forEach
            ( 
                p -> 
                p.content = p.content.substring(0, Math.min(limit, p.content.length()))
            );

        return posts;

    }

    @GET @Path("/{id}") 
    @Produces("application/json")
    @ApiOperation( value = "Returns a post by its Id" )
    public Post getById 
    ( 
        @ApiParam( value = "the id of the post you want" )
        @PathParam("id") Long id 
    )
    {
        if (id == null)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );
        
        try
        {
            return postsRepo.findById(id).get();
        }
        catch (NoSuchElementException e)
        {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        }
    }
    

    @POST
    @Consumes("application/json")
    @ApiOperation( "Create a new post" )
    public Response createNew 
    ( 
        @ApiParam(value = "the username if the user who created this post")
        @QueryParam("from") String username, 
        @ApiParam (value = "the post to submit")
        Post post 
    )
    {
        if (post == null || username == null)
            return Response.status( Response.Status.BAD_REQUEST ).build();

        User user = usersRepo.findByUsername(username);
        if (user == null)
            return Response.status( Response.Status.NOT_FOUND ).build();

        post.date = new Date();
        post.author = user;

        postsRepo.saveAndFlush( post );

        return Response.status(Response.Status.CREATED).build();
    }

}
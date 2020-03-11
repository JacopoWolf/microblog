package edu.marconivr.jacopo.microblog.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

@Component
@Path("/post")
public class PostsController
{
    @Autowired
    private UserRepository usersRepo;
    
    @Autowired
    private PostsRepository postsRepo;

    @GET
    @Produces("application/json")
    public List<Post> getAll( @QueryParam("from") String username, @QueryParam("count") int count , @QueryParam("page") int page, @QueryParam("limitcontent") int limit )
    {
        // preliminary checks
        if (count < 1)
            count = 1;
        
        if (page < 0 || limit < 0)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );

        List<Post> posts;

        if ( username == null || username.isEmpty())
        {
            posts = postsRepo.findAll(PageRequest.of(page, count)).toList();
        }
        else
        {
            User usr = usersRepo.findByUsername(username);
            posts = postsRepo.findByAuthor(usr, PageRequest.of(page, count, Sort.by("date")));
        }

        if (limit > 0)
            posts.stream().forEach( p -> p.content = p.content.substring(0, (limit < p.content.length()) ? limit : p.content.length() ));

        return posts;

    }

    @GET @Path("/id/{id}") 
    @Produces("application/json")
    public Post getById ( @PathParam("id") Long id )
    {
        if (id == null)
            throw new WebApplicationException( Response.Status.BAD_REQUEST );
            
        return postsRepo.findById(id).get();
    }
    

}
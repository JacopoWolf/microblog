package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<Post> getAll( @QueryParam("from") String username )
    {
        if ( username == null || username.isEmpty())
            return postsRepo.findAll();

        User usr = usersRepo.findByUserName(username);
        return postsRepo.findByAuthor_Id(usr.id);
        

    }
}
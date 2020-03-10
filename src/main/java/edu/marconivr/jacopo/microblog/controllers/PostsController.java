package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.PostRemove;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    public List<Post> getAll( @QueryParam("from") String username, @QueryParam("date") String date,  @QueryParam("count") int count )
    {
        if ( username == null || username.isEmpty())
            return postsRepo.findAll(PageRequest.of(0, count)).toList();

        User usr = usersRepo.findByUserName(username);
        return postsRepo.findByAuthor_Id(usr.id, PageRequest.of(0, count, Sort.by("date")));
    }

    

}
package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.repos.CommentsRepository;
import edu.marconivr.jacopo.microblog.entities.repos.PostsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component @Path("/posts")
@Api
public class CommentsController
{
    @Autowired
    private CommentsRepository commentsRepo;

    @Autowired
    private PostsRepository postsRepo;

    @GET @Path("/{post}/comments")
    @Produces("application/json")
    @ApiOperation(value = "gets all comments under the specified post")
    public List<Comment> getAllUnder( @PathParam("post") Long id )
    {
        Post post = postsRepo.findById(id).get();

        return commentsRepo.findAllByUnder(post, Sort.by("date"));
    }


    @GET @Path("/comments/all")
    @Produces("application/json")
    @ApiOperation("gets all comments in the database")
    public List<Comment> getAll ()
    {
        return commentsRepo.findAll();
    }

}
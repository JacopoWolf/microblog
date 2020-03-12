package edu.marconivr.jacopo.microblog.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.CommentsRepository;
import edu.marconivr.jacopo.microblog.entities.repos.PostsRepository;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;

@Component
@Path("/post")
public class CommentsController
{
    @Autowired
    private CommentsRepository commentsRepo;

    @Autowired
    private PostsRepository postsRepo;

    @GET @Path("/{post}/comments")
    @Produces("application/json")
    public List<Comment> getAllUnder( @PathParam("post") Long id )
    {
        Post post = postsRepo.findById(id).get();

        return commentsRepo.findAllByUnder(post, Sort.by("date"));
    }


    @GET @Path("/comments/all")
    @Produces("application/json")
    public List<Comment> getAll ()
    {
        return commentsRepo.findAll();
    }

}
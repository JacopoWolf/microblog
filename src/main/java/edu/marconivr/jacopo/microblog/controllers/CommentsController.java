package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.security.services.IAuthenticationService;
import edu.marconivr.jacopo.microblog.services.ICommentsService;
import edu.marconivr.jacopo.microblog.services.IPostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component @Path("/posts")
@Api
public class CommentsController
{
    @Autowired
    private ICommentsService commentsService;

    @Autowired
    private IPostsService postsService;

    @Autowired
    private IAuthenticationService authService;


    @GET @Path("/{post}/comments")
    @Produces("application/json")
    @ApiOperation(value = "gets all comments under the specified post")
    public List<Comment> getAllUnder( @PathParam("post") Long id )
    {
        return this.commentsService.getAllUnder(id);
    }

    @POST @Path("/{post}/comments")
    @Consumes("text/plain")
    @ApiOperation(value = "post a new comment under the specified post")
    public Response createNew( @PathParam("post") Long id , @HeaderParam("Authorization") String token , String content )
    {
        User creator = authService.authenticateByToken(token);
        Post post = postsService.getById(id);

        Comment comment = new Comment( post );
            comment.author = creator;
            comment.content = content;

        this.commentsService.createNew(comment);

        return Response.noContent().build();

    }

}
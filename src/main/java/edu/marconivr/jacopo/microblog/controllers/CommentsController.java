package edu.marconivr.jacopo.microblog.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.services.ICommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component @Path("/posts")
@Api
public class CommentsController
{
    @Autowired
    private ICommentsService commentsService;

    @GET @Path("/{post}/comments")
    @Produces("application/json")
    @ApiOperation(value = "gets all comments under the specified post")
    public List<Comment> getAllUnder( @PathParam("post") Long id )
    {
        return this.commentsService.getAllUnder(id);
    }


    @GET @Path("/comments/all")
    @Produces("application/json")
    @ApiOperation("gets all comments in the database")
    public List<Comment> getAll ()
    {
        return commentsService.getAll();
    }

}
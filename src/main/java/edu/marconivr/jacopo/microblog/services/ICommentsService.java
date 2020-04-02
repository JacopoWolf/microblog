package edu.marconivr.jacopo.microblog.services;

import java.util.List;

import edu.marconivr.jacopo.microblog.entities.Comment;

public interface ICommentsService 
{
    /**
     * 
     * @return
     */
    List<Comment> getAll();


    /**
     * 
     * @return
     */
    List<Comment> getAllUnder( long id );

}
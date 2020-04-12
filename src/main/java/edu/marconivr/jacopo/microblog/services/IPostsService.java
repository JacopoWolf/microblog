package edu.marconivr.jacopo.microblog.services;

import java.util.List;

import edu.marconivr.jacopo.microblog.entities.Post;

public interface IPostsService 
{
    /**
     * returns a page given based on the current parameters
     * @param from
     * @param page
     * @param count
     * @param limit
     * @return a list of posts in the specified page
     */
    List<Post> getPage
    ( 
        String from, 
        int page, 
        int count,
        int limit 
    );

    /**
     * retrieves a post
     * @param id
     * @return the post with the specified id 
     */
    Post getById
    (
        long id
    );

    /**
     * persists a new post
     * @param author
     * @param post
     * @return
     */
    long createNew
    (
        String author,
        Post post
    );

}
package edu.marconivr.jacopo.microblog.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repos.PostsRepository;
import edu.marconivr.jacopo.microblog.entities.repos.UserRepository;
import edu.marconivr.jacopo.microblog.services.*;

@Service
public class PostsService implements IPostsService 
{
    
    @Autowired
    private UserRepository usersRepo;

    @Autowired
    private PostsRepository postsRepo;

    @Override
    public List<Post> getPage(String username, int page, int count, int limit) 
    {
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

    @Override
    public Post getById(long id) 
    {
    
        try
        {
            return postsRepo.findById(id).get();
        }
        catch (NoSuchElementException e)
        {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        }
    }

    @Override
    public void createNew(String author, Post post) 
    {
        User user = usersRepo.findByUsername(author);

        if (user == null)
            throw new WebApplicationException( Response.Status.NOT_FOUND );

        post.date = new Date();
        post.author = user;

        postsRepo.saveAndFlush( post );
    }
    

}
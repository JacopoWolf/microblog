package edu.marconivr.jacopo.microblog.services.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.repositories.*;
import edu.marconivr.jacopo.microblog.services.ICommentsService;

@Service
public class CommentsService implements ICommentsService 
{

    @Autowired
    private ICommentsRepository commentsRepo;

    @Autowired
    private IPostsRepository postsRepo;


    @Override
    public List<Comment> getAll() 
    {
        return commentsRepo.findAll();
    }

    @Override
    public List<Comment> getAllUnder(long id) 
    {
        Post post = postsRepo.findById(id).get();

        return commentsRepo.findAllByUnder(post, Sort.by("date"));
    }

    @Override
    public void createNew(Comment comment) 
    {
        comment.date = new Date();

        this.commentsRepo.saveAndFlush(comment);
    }

    
}
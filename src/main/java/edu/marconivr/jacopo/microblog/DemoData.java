package edu.marconivr.jacopo.microblog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.controllers.UsersController;
import edu.marconivr.jacopo.microblog.entities.Comment;
import edu.marconivr.jacopo.microblog.entities.Post;
import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.entities.repositories.ICommentsRepository;
import edu.marconivr.jacopo.microblog.entities.repositories.IPostsRepository;

@Component
public class DemoData implements ApplicationRunner
{
    @Autowired
    private UsersController usersController; // automatically fills in hashcode

    @Autowired
    private IPostsRepository postsRepo;

    @Autowired
    private ICommentsRepository commentsRepo;

	@Override
    public void run(ApplicationArguments args) throws Exception 
    {
    
        User mickey = new User("xxx_topolino_xxx", "topolino@fake");
        this.usersController.registerUser(mickey, "topo");

        User goofy = new User("Plut0","pluto@fake");
        this.usersController.registerUser(goofy, "ploto");

        User tortello = new User("tortello69", "tortello@fake");
        this.usersController.registerUser(tortello, "gargamella");
    
        
        Post post1 = new Post();
            post1.author = mickey;
            post1.date = new Date(2020, 1, 1, 12, 00, 00);  // that's why i hate java.
            post1.title = "kaffèè";
            post1.content = "buongiornissimo!!!!1!1!1";
        postsRepo.saveAndFlush(post1);
        
        Comment comment1 = new Comment(post1);
            comment1.author = tortello;
            comment1.date = new Date(2020, 01, 01, 12, 10, 00);
            comment1.content = "Buongiollo anche a te!!1!1!";
        commentsRepo.saveAndFlush(comment1);
            
        
    }

}
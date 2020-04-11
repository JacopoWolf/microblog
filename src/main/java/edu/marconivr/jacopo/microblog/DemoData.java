package edu.marconivr.jacopo.microblog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        
        Post post1 = new Post();
            post1.author = mickey;
            post1.date = format.parse("2020.01.01 12:00:00");
            post1.title = "kaffèè";
            post1.content = "buongiornissimo!!!!1!1!1";
        postsRepo.saveAndFlush(post1);
        
        Comment comment1 = new Comment(post1);
            comment1.author = tortello;
            comment1.date = format.parse("2020.01.01 12:10:00");
            comment1.content = "Buongiollo anche a te!!1!1!";
        commentsRepo.saveAndFlush(comment1);
            
        
    }

}
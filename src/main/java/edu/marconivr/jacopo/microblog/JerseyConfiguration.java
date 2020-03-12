package edu.marconivr.jacopo.microblog;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.controllers.CommentsController;
import edu.marconivr.jacopo.microblog.controllers.PostsController;
import edu.marconivr.jacopo.microblog.controllers.UsersController;


@Component
@ApplicationPath("/rest")
public class JerseyConfiguration extends ResourceConfig
{
    public JerseyConfiguration()
    {
        this.register(UsersController.class);
        this.register(PostsController.class);
        this.register(CommentsController.class);  
    }
}
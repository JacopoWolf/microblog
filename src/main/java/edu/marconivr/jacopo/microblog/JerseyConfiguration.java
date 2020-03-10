package edu.marconivr.jacopo.microblog;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.controllers.UsersController;


@Component
@ApplicationPath("/repo")
public class JerseyConfiguration extends ResourceConfig
{
    public JerseyConfiguration()
    {
        this.register(UsersController.class);
    }
}
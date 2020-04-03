package edu.marconivr.jacopo.microblog.configurations;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.marconivr.jacopo.microblog.controllers.CommentsController;
import edu.marconivr.jacopo.microblog.controllers.PostsController;
import edu.marconivr.jacopo.microblog.controllers.UsersController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;


@Component
public class JerseyConfiguration extends ResourceConfig
{
    @Value("${spring.jersey.application-path}")
    private String apiPath;

    public JerseyConfiguration(  )
    {
        this.register(UsersController.class);
        this.register(PostsController.class);
        this.register(CommentsController.class);  
    }

    @PostConstruct
    public void configureSwagger()
    {
        this.register( ApiListingResource.class );
        this.register( SwaggerSerializers.class );

        BeanConfig config = new BeanConfig();
        config.setBasePath(apiPath);
        config.setResourcePackage("edu.marconivr.jacopo.microblog.controllers");
        config.setVersion("v2");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
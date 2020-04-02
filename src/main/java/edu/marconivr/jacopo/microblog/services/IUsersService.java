package edu.marconivr.jacopo.microblog.services;

import java.util.List;

import edu.marconivr.jacopo.microblog.entities.User;

public interface IUsersService 
{
    /**
     * 
     * @return every user in the site
     */
    List<User> getAll();

    /**
     * 
     * @param nickname the nickname of the user 
     * @return
     */
    User getByNickname( String nickname );

    /**
     * creates a new user
     * @param user
     */
    void createNew( User user );
    

}
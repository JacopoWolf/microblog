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
     * 
     * @param token
     * @return
     */
    User getByToken( String token );

    /**
     * creates a new user
     * @param user
     */
    void createNew( User user );
    
    /**
     * 
     * @param user
     */
    void updateUser( User user );

}
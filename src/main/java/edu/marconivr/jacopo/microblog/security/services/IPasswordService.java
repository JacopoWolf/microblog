package edu.marconivr.jacopo.microblog.security.services;

import edu.marconivr.jacopo.microblog.entities.User;

public interface IPasswordService 
{
    /**
     * updates the user's password
     * @param password
     */
    void setCredentialsOf ( User user, String password );

    /**
     * verifies the given password is valid.
     * @param user
     * @param password
     */
    boolean verify( User user, String password );
}
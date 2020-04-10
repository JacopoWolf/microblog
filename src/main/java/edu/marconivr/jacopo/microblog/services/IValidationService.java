package edu.marconivr.jacopo.microblog.services;

public interface IValidationService 
{
    /**
     * 
     * @param password
     * @return
     */
    public boolean validatePassword( String password );

    /**
     * 
     * @param username
     * @return
     */
    public boolean validateUsername( String username );

}
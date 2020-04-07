package edu.marconivr.jacopo.microblog.security.services;

public interface IPasswordValidationService 
{
    public boolean validatePassword( String password );
}
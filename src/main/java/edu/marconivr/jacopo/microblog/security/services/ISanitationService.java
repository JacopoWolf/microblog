package edu.marconivr.jacopo.microblog.security.services;

public interface ISanitationService
{
    /**
     * 
     * @param original
     * @return
     */
    String escapeHTML (String original);
    
}
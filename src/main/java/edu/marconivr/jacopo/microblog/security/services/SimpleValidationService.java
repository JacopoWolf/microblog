package edu.marconivr.jacopo.microblog.security.services;

import org.springframework.stereotype.Service;

@Service
public class SimpleValidationService implements IValidationService
{
    @Override
    public boolean validatePassword(String password) 
    {
        // https://www.regextester.com/97402
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#$^+=!*()@%&]).{8,10}$");
    }

    @Override
    public boolean validateUsername(String username) 
    {
        return username.matches("[a-zA-Z0-9_-]{3,30}" );
    }
}
package edu.marconivr.jacopo.microblog.services.implementations;

import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.services.IValidationService;

@Service
public class SimpleValidationService implements IValidationService
{
    @Override
    public boolean validatePassword(String password) 
    {
        // https://www.regextester.com/97402
        //return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#$^+=!*()@%&]).{8,10}$");

        //? don't want to invent complex passwords
        return password.matches("[a-zA-Z0-1]{3,30}"); 
    }

    @Override
    public boolean validateUsername(String username) 
    {
        return username.matches("[a-zA-Z0-9_-]{3,30}" );
    }
}
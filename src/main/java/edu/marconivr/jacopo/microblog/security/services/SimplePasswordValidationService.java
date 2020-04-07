package edu.marconivr.jacopo.microblog.security.services;

import org.springframework.stereotype.Service;

@Service
public class SimplePasswordValidationService implements IPasswordValidationService
{
    @Override
    public boolean validatePassword(String password) 
    {
        // https://www.regextester.com/97402
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#$^+=!*()@%&]).{8,10}$");
    }
}
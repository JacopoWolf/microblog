package edu.marconivr.jacopo.microblog.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

public class NoRedirectStrategy implements RedirectStrategy 
{
    /*
        this strategy allows REST api to not redirect to a login page, as it would do by default, but to simply return a 
        401 UNAUTHORIZED error
    */

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException 
    {
       return;
    }

}
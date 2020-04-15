package edu.marconivr.jacopo.microblog.security.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import edu.marconivr.jacopo.microblog.security.services.ISanitationService;

@Service
public class SimpleSanitationService implements ISanitationService
{

    @Override
    public String escapeHTML(String original) 
    {
        return HtmlUtils.htmlEscape(original);
    }
    
}
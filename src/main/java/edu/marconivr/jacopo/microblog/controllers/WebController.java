package edu.marconivr.jacopo.microblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController
{
    @RequestMapping("/")
    public String index()
    {
        return "index.html";
    }

    @RequestMapping("/potato")
    @ResponseBody
    public String potato()
    {
        return "potato";
    }

}
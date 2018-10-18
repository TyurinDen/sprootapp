package com.example.demo.controller;

import com.example.demo.db.models.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping({"/"})
public class HomeController {
    @RequestMapping(method = GET)
    public ModelAndView home(@AuthenticationPrincipal CustomUser user) {
        ModelAndView modelAndView = new ModelAndView("add-client");
        modelAndView.addObject("status", "STATUS");
        modelAndView.addObject("states", new String[] {"STATE1", "STATE2"});
        return modelAndView;
    }
}

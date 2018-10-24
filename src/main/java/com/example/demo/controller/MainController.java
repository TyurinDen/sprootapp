package com.example.demo.controller;

import com.example.demo.db.models.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/test")
public class MainController {

    @RequestMapping(value = "/mailing", method = GET)
    public ModelAndView mailing() {
        ModelAndView mailing = new ModelAndView("mailing");
        return mailing;
    }

    @RequestMapping(value = "/add-client", method = GET)
    public ModelAndView home(@AuthenticationPrincipal CustomUser user) {
        ModelAndView addClient = new ModelAndView("add-client");
        addClient.addObject("status", "STATUS");
        addClient.addObject("states", new String[]{"STATE1", "STATE2"});
        return addClient;
    }

}

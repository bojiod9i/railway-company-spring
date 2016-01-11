package ru.tsystems.railway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

    @RequestMapping(value = "/pageNotFound", method = RequestMethod.GET)
    public String pageNotFound() {
        return "pageNotFound";
    }

    @RequestMapping(value = "/errorPage", method = RequestMethod.GET)
    public String error() {
        return "errorPage";
    }

}

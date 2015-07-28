package ru.tsystems.railway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.tsystems.railway.domain.service.Station;
import ru.tsystems.railway.service.SampleService;

@Controller
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/appServlet", method = RequestMethod.GET)
    public @ResponseBody
    Station sayHello() {
        return sampleService.createUser();
    }
}

package com.sjh.matzip.controllers;

import com.sjh.matzip.entities.data.PlaceEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.sjh.matzip.controllers.HomeController")
@RequestMapping(value = "/")
public class HomeController {
    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getIndex() {
        PlaceEntity place =new PlaceEntity();

        ModelAndView modelAndView = new ModelAndView("home/index");


        return modelAndView;
    }
}

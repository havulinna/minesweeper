package com.havulinna.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.havulinna.minesweeper.model.Difficulty;

@Controller
public class FrontPageController {

    @RequestMapping("/")
    public ModelAndView renderFrontPage() {
        ModelAndView response = new ModelAndView("index");
        response.addObject("difficulties", Difficulty.values());
        return response;
    }

}

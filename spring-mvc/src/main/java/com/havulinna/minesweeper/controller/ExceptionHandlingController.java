package com.havulinna.minesweeper.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.havulinna.minesweeper.controller.response.GameModelAndView;
import com.havulinna.minesweeper.exception.NotFoundException;

@Controller
@ControllerAdvice
public class ExceptionHandlingController implements ErrorController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException() {
        return new GameModelAndView("error404");
    }

    @RequestMapping("/error")
    public ModelAndView handleGenericErrors(HttpServletResponse response) {
        return new GameModelAndView("error").addObject("errorCode", response.getStatus());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

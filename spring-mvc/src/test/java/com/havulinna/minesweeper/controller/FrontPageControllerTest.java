package com.havulinna.minesweeper.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;


public class FrontPageControllerTest {

    @Test
    public void frontPageRendersTheIndexTemplate() {
        ModelAndView response = new FrontPageController().renderFrontPage();

        assertEquals("index", response.getViewName());
    }
}

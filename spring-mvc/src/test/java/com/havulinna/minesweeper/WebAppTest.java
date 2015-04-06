package com.havulinna.minesweeper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WebAppTest {

    private WebApp webApp = new WebApp();

    @Test
    public void testHome() {
        assertEquals("Hello World!", webApp.home());
    }
}

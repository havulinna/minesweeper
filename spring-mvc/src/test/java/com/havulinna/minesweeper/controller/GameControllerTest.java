package com.havulinna.minesweeper.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.havulinna.minesweeper.exception.NotFoundException;
import com.havulinna.minesweeper.model.Difficulty;
import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.service.GameRepository;


public class GameControllerTest {

    private static final String GENERATED_GAME_ID = "abc123";

    private GameRepository mockRepository = mock(GameRepository.class);
    private GameController controller = new GameController(mockRepository);
    private Game mockGame = mock(Game.class);

    @Before
    public void setUp() throws NotFoundException {
        when(mockRepository.store(any(Game.class))).thenReturn(GENERATED_GAME_ID);
        when(mockRepository.getGameById(GENERATED_GAME_ID)).thenReturn(mockGame);
    }

    @Test
    public void newGameHandlerStoresANewGameInRepository() throws Exception {
        controller.startNewGame(Difficulty.EASY);
        verify(mockRepository).store(any(Game.class));
    }

    @Test
    public void newGameRedirectsUserToTheCreatedGame() {
        RedirectView response = controller.startNewGame(Difficulty.FAIR);

        assertEquals("/game/" + GENERATED_GAME_ID, response.getUrl());
    }

    @Test
    public void showGameReturnsGameTemplateWithGameViewObject() throws NotFoundException {
        ModelAndView response = controller.showGame(GENERATED_GAME_ID);

        assertEquals("game", response.getViewName());
        assertTrue(response.getModel().containsKey("gameView"));
    }

    @Test
    public void openSquareHandlerOpensTheRequestedSquareInGameWhenGameIsNotOver() throws NotFoundException {
        controller.openSquare(GENERATED_GAME_ID, 1, 2);
        verify(mockGame).openSquare(eq(1), eq(2));
    }

    @Test
    public void squaresCannotBeOpenedWhenGameHasEnded() throws NotFoundException {
        when(mockGame.isOver()).thenReturn(Boolean.TRUE);
        controller.openSquare(GENERATED_GAME_ID, 1, 2);
        verify(mockGame, times(0)).openSquare(anyInt(), anyInt());
    }

    @Test
    public void openSquareRedirectsTheUserBackToShowGame() throws NotFoundException {
        RedirectView response = controller.openSquare(GENERATED_GAME_ID, 1, 2);

        assertEquals("/game/" + GENERATED_GAME_ID, response.getUrl());
    }
}

package com.havulinna.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.havulinna.minesweeper.model.Difficulty;
import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.service.GameRepository;
import com.havulinna.minesweeper.view.GameView;

@Controller
public class GameController {

    private final GameRepository repository;

    @Autowired
    public GameController(GameRepository repository) {
        this.repository = repository;
    }


    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public ModelAndView showGame(@PathVariable("gameId") String id) {
        Game game = repository.getGameById(id);

        if (game != null) {
            return new ModelAndView("game")
                .addObject("gameView", new GameView(game))
                .addObject("difficulties", Difficulty.values());
        } else {
            // TODO: Show an error page
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.POST)
    public RedirectView openSquare(
            @PathVariable("gameId") String id,
            @RequestParam("row") int row,
            @RequestParam("col") int col) {

        Game game = repository.getGameById(id);
        game.openSquare(row, col);
        return new RedirectView("/game/" + id);
    }
}

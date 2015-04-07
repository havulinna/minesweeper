package com.havulinna.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.havulinna.minesweeper.model.Difficulty;
import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.service.GameRepository;

@Controller
public class NewGameController {

    private final GameRepository repository;

    @Autowired
    public NewGameController(GameRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public RedirectView startNewGame(
            @RequestParam("difficulty") Difficulty difficulty) {

        Game newGame = new Game(difficulty);
        String gameId = repository.store(newGame);
        return new RedirectView("/game/" + gameId);
    }
}

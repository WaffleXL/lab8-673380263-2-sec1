package com.example.demo.service;

import com.example.demo.model.Game;
import com.example.demo.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + id));
    }

    @Transactional
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGame(Long id, Game game) {
        Game existing = getGameById(id);
        existing.setTitle(game.getTitle());
        existing.setGenre(game.getGenre());
        existing.setPlatform(game.getPlatform());
        existing.setRating(game.getRating());
        existing.setPrice(game.getPrice());
        existing.setDiscountType(game.getDiscountType());
        existing.setReleaseDate(game.getReleaseDate());
        return gameRepository.save(existing);
    }

    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }
}


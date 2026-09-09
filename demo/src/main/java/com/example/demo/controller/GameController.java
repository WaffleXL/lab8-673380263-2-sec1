package com.example.demo.controller;

import com.example.demo.model.Game;
import com.example.demo.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/games";
    }

    @GetMapping("/games")
    public String showGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "games/list";
    }

    @GetMapping("/games/add")
    public String showAddForm(Model model) {
        model.addAttribute("game", new Game());
        return "games/add";
    }

    @PostMapping("/games/save")
    public String saveGame(@ModelAttribute Game game, RedirectAttributes redirectAttributes) {
        gameService.saveGame(game);
        redirectAttributes.addFlashAttribute("message", "เกมถูกบันทึกแล้ว");
        return "redirect:/games";
    }

    @GetMapping("/games/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameService.getGameById(id));
        return "games/edit";
    }

    @PostMapping("/games/update/{id}")
    public String updateGame(@PathVariable Long id, @ModelAttribute Game game, RedirectAttributes redirectAttributes) {
        gameService.updateGame(id, game);
        redirectAttributes.addFlashAttribute("message", "อัปเดตข้อมูลเกมเรียบร้อยแล้ว");
        return "redirect:/games";
    }

    @GetMapping("/games/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameService.getGameById(id));
        return "games/delete";
    }

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        gameService.deleteGameById(id);
        redirectAttributes.addFlashAttribute("message", "ลบเกมเรียบร้อยแล้ว");
        return "redirect:/games";
    }
}


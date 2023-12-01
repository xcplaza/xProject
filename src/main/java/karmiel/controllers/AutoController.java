package karmiel.controllers;

import karmiel.entity.Auto;
import karmiel.repo.AutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AutoController {

    @Autowired
    private AutoRepo autoRepo;

    @GetMapping("/auto")
    public String auto(Model model) {
        Iterable<Auto> autos = autoRepo.findAll();
        model.addAttribute("autos", autos);
        return "auto";
    }

    @GetMapping("/auto/add")
    public String add(Model model) {
        model.addAttribute("title", "Добавление автомобиля");
        return "auto-add";
    }

    @PostMapping("/auto/add")
    public String addAuto(
            @RequestParam String name,
            @RequestParam String model,
            @RequestParam String year,
            @RequestParam String color,
            @RequestParam String price,
            Model models) {
        Auto auto = new Auto(name, model, year, color, price);
        autoRepo.save(auto);
        return "redirect:/auto";
    }

    @GetMapping("/auto/{id}")
    public String autoDetails(@PathVariable(value = "id") long id, Model model) {
        if (!autoRepo.existsById(id)) {
            return "redirect:/auto";
        }
        Optional<Auto> auto = autoRepo.findById(id);
        ArrayList<Auto> res = new ArrayList<>();
        auto.ifPresent(res::add);
        model.addAttribute("auto", res);
        return "auto-details";
    }

    @GetMapping("/auto/{id}/edit")
    public String autoEdit(@PathVariable(value = "id") long id, Model model) {
        if (!autoRepo.existsById(id)) {
            return "redirect:/auto";
        }
        Optional<Auto> auto = autoRepo.findById(id);
        ArrayList<Auto> res = new ArrayList<>();
        auto.ifPresent(res::add);
        model.addAttribute("auto", res);
        return "auto-edit";
    }

    @PostMapping("/auto/{id}/edit")
    public String autoUpdate(@PathVariable(value = "id") long id,
                             @RequestParam String name,
                             @RequestParam String model,
                             @RequestParam String year,
                             @RequestParam String color,
                             @RequestParam String price) {
        Auto auto = autoRepo.findById(id).orElseThrow();
        auto.setName(name);
        auto.setModel(model);
        auto.setYear(year);
        auto.setColor(color);
        auto.setPrice(price);
        autoRepo.save(auto);
        return "redirect:/auto";
    }
    @RequestMapping(value = "/auto/{id}/remove", method = RequestMethod.POST)
    public String autoDelete(@PathVariable(value = "id") long id) {
        Auto auto = autoRepo.findById(id).orElseThrow();
        autoRepo.delete(auto);
        return "redirect:/auto";
    }

}

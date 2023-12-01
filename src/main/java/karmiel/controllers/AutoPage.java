package karmiel.controllers;

import karmiel.entity.Auto;
import karmiel.repo.AutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AutoPage {

    @Autowired
    private AutoRepo autoRepo;

    @GetMapping("/auto")
    public String auto(Model model) {
        Iterable<Auto> autos = autoRepo.findAll();
        model.addAttribute("autos", autos);
        return "auto";
    }
}

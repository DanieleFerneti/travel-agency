package it.uniroma3.siw.controller;

import it.uniroma3.siw.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private WeekService weekService;

    @GetMapping("/contatti")
    public String getContatti(Model model) {
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        return "contatti";
    }

    @GetMapping("/chi-siamo")
    public String getChiSiamo(Model model) {
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        return "chi_siamo";
    }

    @GetMapping("/entra-nel-team")
    public String getEntraNelTeam(Model model) {
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        return "entra_nel_team";
    }


}

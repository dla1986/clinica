package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.atividade3.clinica.entities.Receituario;
import com.atividade3.clinica.repositories.RepositoryFacade;

@Controller
@RequestMapping("/receituario")
public class ReceituarioController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String index(Model model) {

        try {

            model.addAttribute("receituarios",
                    facade.readAllReceituarios());

        } catch (SQLException e) {

            e.printStackTrace();
        }

        model.addAttribute("receituario", new Receituario());

        return "receituario/receituario";
    }

    @PostMapping("/save")
    public String save(Receituario receituario) {

        try {

            if (receituario.getCodigo() == 0) {
                facade.create(receituario);
            } else {
                facade.update(receituario);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "redirect:/receituario";
    }

    @GetMapping("/edit/{codigo}")
    public String edit(@PathVariable int codigo, Model model) {

        try {

            model.addAttribute("receituario",
                    facade.readReceituario(codigo));

            model.addAttribute("receituarios",
                    facade.readAllReceituarios());

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "receituario/receituario";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable int codigo) {

        try {

            facade.deleteReceituario(codigo);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "redirect:/receituario";
    }
}
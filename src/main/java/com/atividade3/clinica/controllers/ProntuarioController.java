package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.atividade3.clinica.entities.Prontuario;
import com.atividade3.clinica.repositories.RepositoryFacade;

@Controller
@RequestMapping("/prontuario")
public class ProntuarioController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String index(Model model) {

        try {
            model.addAttribute("prontuarios",
                    facade.readAllProntuarios());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("prontuario", new Prontuario());

        return "prontuario/prontuario";
    }

    @PostMapping("/save")
    public String save(Prontuario prontuario) {

        try {

            if (prontuario.getCodigo() == 0) {
                facade.create(prontuario);
            } else {
                facade.update(prontuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/prontuario";
    }

    @GetMapping("/edit/{codigo}")
    public String edit(@PathVariable int codigo, Model model) {

        try {

            model.addAttribute("prontuario",
                    facade.readProntuario(codigo));

            model.addAttribute("prontuarios",
                    facade.readAllProntuarios());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "prontuario/prontuario";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable int codigo) {

        try {

            facade.deleteProntuario(codigo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/prontuario";
    }
    
}
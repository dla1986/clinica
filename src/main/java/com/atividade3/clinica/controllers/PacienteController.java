package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.atividade3.clinica.entities.Paciente;
import com.atividade3.clinica.repositories.RepositoryFacade;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private RepositoryFacade facade;

    // Rota padrão (Lista os pacientes com o modal fechado)
    @GetMapping({"","/"})
    public String index(Model model) {
        try {
            model.addAttribute("pacientes", facade.readAllPacientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("paciente", new Paciente());
        model.addAttribute("exibirModal", false); // Indica que o modal inicia fechado
        return "paciente/paciente";
    }

    // Rota para abrir o formulário para um NOVO cadastro de paciente limpo
    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        try {
            model.addAttribute("pacientes", facade.readAllPacientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("paciente", new Paciente()); // Instancia um objeto totalmente vazio
        model.addAttribute("exibirModal", true); // Força o modal a abrir limpo
        return "paciente/paciente";
    }

    @PostMapping("/save")
    public String save(Paciente paciente) {
        try {
            if (paciente.getCpf() != null && !paciente.getCpf().isBlank() && facade.readPaciente(paciente.getCpf()) != null) {
                facade.update(paciente);
            } else {
                facade.create(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/paciente";
    }

    @GetMapping("/delete/{cpf}")
    public String delete(@PathVariable String cpf) {
        try {
            facade.deletePaciente(cpf);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/paciente";
    }

    @GetMapping("/edit/{cpf}")
    public String edit(@PathVariable String cpf, Model model) {
        try {
            model.addAttribute("paciente", facade.readPaciente(cpf)); // Carrega os dados para edição
            model.addAttribute("pacientes", facade.readAllPacientes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("exibirModal", true); // Força o modal a abrir com os dados populados
        return "paciente/paciente";
    }
}
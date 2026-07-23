package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atividade3.clinica.entities.Medico;
import com.atividade3.clinica.repositories.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private HttpSession session;

	@Autowired
	private RepositoryFacade facade;

	 
	@GetMapping({"/", ""})
	public String index() {
		return "index";
	}

	 
	@GetMapping("/login")
	public String login() {
		return "medico/login";
	}

	
	@PostMapping("/login")
	public String login(Model m,
	        @RequestParam("login") String login,
	        @RequestParam("senha") String senha) {

	    try {

	        Medico medico = facade.loginMedico(login, senha);

	        if (medico == null) {
	            m.addAttribute("erro", "Login ou senha inválidos.");
	            return "medico/login";
	        }

	        session.setAttribute("medicoSessao", medico);

	        return "redirect:/medico/consultas";

	    } catch (SQLException e) {
	        e.printStackTrace();
	        m.addAttribute("erro",
	                "Não foi possível realizar o login devido a uma falha no banco.");
	        return "medico/login";
	    }
	}


	@GetMapping("/atendimento")
	public String atendimento() {
		return "atendimento/atendimento";
	}

	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login";
	}
}
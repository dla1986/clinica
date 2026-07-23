package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.atividade3.clinica.entities.Consulta;
import com.atividade3.clinica.repositories.RepositoryFacade;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	private RepositoryFacade facade;

	@GetMapping({ "", "/" })
	public String index(Model model) {
		try {
			model.addAttribute("consultas", facade.readAllConsultas());
			model.addAttribute("pacientes", facade.readAllPacientes());
			model.addAttribute("medicos", facade.readAllMedicos());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Consulta novaConsulta = new Consulta();
		novaConsulta.setCodigo(0);
		model.addAttribute("consulta", novaConsulta);

		return "consultas/consultas";
	}

	@PostMapping("/save")
	public String save(Consulta consulta) {
		try {
			if (consulta.getCodigo() == 0) {
				facade.create(consulta);
			} else {
				facade.update(consulta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/consultas";
	}

	@GetMapping("/edit/{codigo}")
	public String edit(@PathVariable int codigo, Model model) {
		try {
			model.addAttribute("consulta", facade.readConsulta(codigo));
			model.addAttribute("consultas", facade.readAllConsultas());
			model.addAttribute("pacientes", facade.readAllPacientes());
			model.addAttribute("medicos", facade.readAllMedicos());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "consultas/consultas";
	}

	@GetMapping("/delete/{codigo}")
	public String delete(@PathVariable int codigo) {
		try {
			facade.deleteConsulta(codigo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/consultas";
	}
}
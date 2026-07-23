package com.atividade3.clinica.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atividade3.clinica.entities.Medicamento;
import com.atividade3.clinica.repositories.RepositoryFacade;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

	@Autowired
	private RepositoryFacade facade;

	@GetMapping({ "", "/" })
	public String index(Model model) {
		try {
			model.addAttribute("medicamentos", facade.readAllMedicamentos());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Medicamento novoMedicamento = new Medicamento();
		novoMedicamento.setCodigo(0);
		model.addAttribute("medicamento", novoMedicamento);

		return "medicamentos/medicamentos";
	}

	@PostMapping("/save")
	public String save(Medicamento medicamento) {
		try {
			if (medicamento.getCodigo() == 0)
				facade.create(medicamento);
			else
				facade.update(medicamento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/medicamentos";
	}

	@GetMapping("/edit/{codigo}")
	public String edit(@PathVariable int codigo, Model model) {
		try {
			model.addAttribute("medicamento", facade.readMedicamento(codigo));
			model.addAttribute("medicamentos", facade.readAllMedicamentos());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "medicamentos/medicamentos";
	}

	@GetMapping("/delete/{codigo}")
	public String delete(@PathVariable int codigo) {
		try {
			facade.deleteMedicamento(codigo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/medicamentos";
	}
}
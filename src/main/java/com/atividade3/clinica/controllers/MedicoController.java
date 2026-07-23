package com.atividade3.clinica.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.atividade3.clinica.entities.*;
import com.atividade3.clinica.repositories.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private RepositoryFacade facade;

    @Autowired
    private HttpSession session;

  
    @GetMapping("/consultas")
    public String consultasMedico(Model model) {
        Medico medicoLogado = (Medico) session.getAttribute("medicoSessao");
        if (medicoLogado == null) return "redirect:/login";

        try {
            List<Consulta> todasConsultas = facade.readAllConsultas();
            List<Consulta> pendentes  = new ArrayList<>();
            List<Consulta> realizadas = new ArrayList<>();

            for (Consulta c : todasConsultas) {
                if (c.getMedicoCrm() == null) continue;
                if (!c.getMedicoCrm().trim().equalsIgnoreCase(
                        medicoLogado.getCrm().trim())) continue;

                Prontuario p = facade.readProntuarioByConsulta(c.getCodigo());
                if (p == null) pendentes.add(c);
                else           realizadas.add(c);
            }

            model.addAttribute("medico", medicoLogado);
            model.addAttribute("consultasPendentes",  pendentes);
            model.addAttribute("consultasRealizadas", realizadas);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medico/consultasmedico";
    }

    
    @GetMapping("/atender/{consultaCodigo}")
    public String atender(@PathVariable int consultaCodigo, Model model) {
        Medico medicoLogado = (Medico) session.getAttribute("medicoSessao");
        if (medicoLogado == null) return "redirect:/login";

        try {
            Consulta consulta = facade.readConsulta(consultaCodigo);
            if (consulta == null) return "redirect:/medico/consultas";

            model.addAttribute("consulta",     consulta);
            model.addAttribute("paciente",     facade.readPaciente(consulta.getPacienteCpf()));
            model.addAttribute("medicamentos", facade.readAllMedicamentos());
            model.addAttribute("medico",       medicoLogado);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medico/atenderconsulta";
    }

   
    @PostMapping("/consulta/finalizar")
    public String finalizar(
            @RequestParam("consultaCodigo")      int    consultaCodigo,
            @RequestParam("descricao")           String descricao,
            @RequestParam("observacao")          String observacao,
            // Receituário (opcional)
            @RequestParam(value = "receituarioObservacao", required = false)
                                                 String receituarioObservacao,
            
            @RequestParam(value = "medicamentoCodigo",    required = false)
                                                 List<Integer> medicamentoCodigos,
            @RequestParam(value = "itemDosagem",          required = false)
                                                 List<Integer> itemDosagens,
            @RequestParam(value = "itemIntervalo",        required = false)
                                                 List<Integer> itemIntervalos,
            @RequestParam(value = "itemObservacao",       required = false)
                                                 List<String>  itemObservacoes) {

        Medico medicoLogado = (Medico) session.getAttribute("medicoSessao");
        if (medicoLogado == null) return "redirect:/login";

        try {
           
            Prontuario prontuario = new Prontuario();
            prontuario.setConsultaCodigo(consultaCodigo);
            prontuario.setDescricao(descricao);
            prontuario.setObservacao(observacao);
            facade.create(prontuario);

           
            boolean temItens = medicamentoCodigos != null
                    && !medicamentoCodigos.isEmpty();
            boolean temObsReceituario = receituarioObservacao != null
                    && !receituarioObservacao.isBlank();

            if (temObsReceituario || temItens) {
                Prontuario salvo =
                        facade.readProntuarioByConsulta(consultaCodigo);

                if (salvo != null) {
                    Receituario receituario = new Receituario();
                    receituario.setProntuarioCodigo(salvo.getCodigo());
                    receituario.setObservacao(
                            temObsReceituario ? receituarioObservacao : "");
                    facade.create(receituario);

                    // 3. Busca o receituário recém-criado e salva os itens
                    Receituario recSalvo =
                            facade.readReceituarioByProntuario(salvo.getCodigo());

                    if (recSalvo != null && temItens) {
                        for (int i = 0; i < medicamentoCodigos.size(); i++) {
                            int medCod = medicamentoCodigos.get(i);
                            if (medCod == 0) continue; // linha vazia ignorada

                            ItemReceituario item = new ItemReceituario();
                            item.setReceituarioCodigo(recSalvo.getCodigo());
                            item.setMedicamentoCodigo(medCod);
                            item.setDosagem(
                                    itemDosagens != null && i < itemDosagens.size()
                                    ? itemDosagens.get(i) : 0);
                            item.setIntervaloEntreDoses(
                                    itemIntervalos != null && i < itemIntervalos.size()
                                    ? itemIntervalos.get(i) : 0);
                            item.setObservacao(
                                    itemObservacoes != null && i < itemObservacoes.size()
                                    ? itemObservacoes.get(i) : "");
                            facade.create(item);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medico/consultas";
    }

    
    @GetMapping("/prontuario/{consultaCodigo}")
    public String verProntuario(@PathVariable int consultaCodigo, Model model) {
        Medico medicoLogado = (Medico) session.getAttribute("medicoSessao");
        if (medicoLogado == null) return "redirect:/login";

        try {
            Consulta consulta       = facade.readConsulta(consultaCodigo);
            Prontuario prontuario   = facade.readProntuarioByConsulta(consultaCodigo);
            Receituario receituario = null;
            List<ItemReceituario> itens = new ArrayList<>();

            if (prontuario != null) {
                receituario = facade.readReceituarioByProntuario(
                        prontuario.getCodigo());
                if (receituario != null) {
                    itens = facade.readItensByReceituario(
                            receituario.getCodigo());
                }
            }

            model.addAttribute("consulta",    consulta);
            model.addAttribute("paciente",    facade.readPaciente(
                    consulta.getPacienteCpf()));
            model.addAttribute("prontuario",  prontuario);
            model.addAttribute("receituario", receituario);
            model.addAttribute("itens",       itens);
            model.addAttribute("medico",      medicoLogado);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medico/verprontuario";
    }


    @GetMapping({"", "/"})
    public String index(Model model) {
        try { model.addAttribute("medicos", facade.readAllMedicos()); }
        catch (SQLException e) { e.printStackTrace(); }
        model.addAttribute("medico", new Medico());
        model.addAttribute("exibirModal", false);
        return "medico/medicos";
    }

    @GetMapping("/novo")
    public String novoMedico(Model model) {
        try { model.addAttribute("medicos", facade.readAllMedicos()); }
        catch (SQLException e) { e.printStackTrace(); }
        model.addAttribute("medico", new Medico());
        model.addAttribute("exibirModal", true);
        return "medico/medicos";
    }

    @GetMapping("/edit/{crm}")
    public String edit(@PathVariable String crm, Model model) {

        try {
            Medico medico = facade.readMedico(crm.trim());

            if (medico == null) {
                return "redirect:/medico";
            }

            model.addAttribute("medico", medico);
            model.addAttribute("medicos", facade.readAllMedicos());

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/medico";
        }

        model.addAttribute("exibirModal", true);
        return "medico/medicos";
    }

    @PostMapping("/save")
    public String save(Medico medico) {
        try {
            if (medico.getCrm() != null && !medico.getCrm().isBlank()
                    && facade.readMedico(medico.getCrm()) != null)
                facade.update(medico);
            else
                facade.create(medico);
        } catch (SQLException e) { e.printStackTrace(); }
        return "redirect:/medico";
    }

    @GetMapping("/delete/{crm}")
    public String delete(@PathVariable String crm, Model model) {
        try {
            // Verifica se o médico possui consultas vinculadas
            for (Consulta c : facade.readAllConsultas()) {
                if (c.getMedicoCrm() != null &&
                    c.getMedicoCrm().equalsIgnoreCase(crm)) {

                    model.addAttribute("medicos", facade.readAllMedicos());
                    model.addAttribute("erro",
                            "Não é possível excluir este médico, pois ele possui consultas cadastradas.");

                    return "medico/medicos";
                }
            }

            facade.deleteMedico(crm);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medico";
    }
}
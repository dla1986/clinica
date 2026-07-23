package com.atividade3.clinica.repositories;

import java.sql.SQLException;
import java.util.List;

import com.atividade3.clinica.entities.*;

@org.springframework.stereotype.Repository
public class RepositoryFacade {

	private Repository<Paciente, String> pacienteRepository;
	private Repository<Medico, String> medicoRepository;
	private Repository<Medicamento, Integer> medicamentoRepository;
	private Repository<Consulta, Integer> consultaRepository;
	private ProntuarioRepository prontuarioRepository;
	private ReceituarioRepository receituarioRepository;
	private ItemReceituarioRepository itemReceituarioRepository;

	public RepositoryFacade() {
		pacienteRepository = new PacienteRepository();
		medicoRepository = new MedicoRepository();
		medicamentoRepository = new MedicamentoRepository();
		consultaRepository = new ConsultaRepository();
		prontuarioRepository = new ProntuarioRepository();
		receituarioRepository = new ReceituarioRepository();
		itemReceituarioRepository = new ItemReceituarioRepository();
	}

	
	public void create(Paciente p) throws SQLException {
		pacienteRepository.create(p);
	}

	public void update(Paciente p) throws SQLException {
		pacienteRepository.update(p);
	}

	public Paciente readPaciente(String cpf) throws SQLException {
		return pacienteRepository.read(cpf);
	}

	public void deletePaciente(String cpf) throws SQLException {
		pacienteRepository.delete(cpf);
	}

	public List<Paciente> readAllPacientes() throws SQLException {
		return pacienteRepository.readAll();
	}

	
	public void create(Medico m) throws SQLException {
		medicoRepository.create(m);
	}

	public void update(Medico m) throws SQLException {
		medicoRepository.update(m);
	}

	public Medico readMedico(String crm) throws SQLException {
		return medicoRepository.read(crm);
	}

	public void deleteMedico(String crm) throws SQLException {
		medicoRepository.delete(crm);
	}

	public List<Medico> readAllMedicos() throws SQLException {
		return medicoRepository.readAll();
	}

	public Medico loginMedico(String login, String senha) throws SQLException {
	    return ((MedicoRepository) medicoRepository).login(login, senha);
	}

	
	public void create(Medicamento m) throws SQLException {
		medicamentoRepository.create(m);
	}

	public void update(Medicamento m) throws SQLException {
		medicamentoRepository.update(m);
	}

	public Medicamento readMedicamento(int codigo) throws SQLException {
		return medicamentoRepository.read(codigo);
	}

	public void deleteMedicamento(int codigo) throws SQLException {
		medicamentoRepository.delete(codigo);
	}

	public List<Medicamento> readAllMedicamentos() throws SQLException {
		return medicamentoRepository.readAll();
	}

	
	public void create(Consulta c) throws SQLException {
		consultaRepository.create(c);
	}

	public void update(Consulta c) throws SQLException {
		consultaRepository.update(c);
	}

	public Consulta readConsulta(int codigo) throws SQLException {
		return consultaRepository.read(codigo);
	}

	public void deleteConsulta(int codigo) throws SQLException {
		consultaRepository.delete(codigo);
	}

	public List<Consulta> readAllConsultas() throws SQLException {
		return consultaRepository.readAll();
	}

	
	public void create(Prontuario p) throws SQLException {
		prontuarioRepository.create(p);
	}

	public void update(Prontuario p) throws SQLException {
		prontuarioRepository.update(p);
	}

	public Prontuario readProntuario(int codigo) throws SQLException {
		return prontuarioRepository.read(codigo);
	}

	public void deleteProntuario(int codigo) throws SQLException {
		prontuarioRepository.delete(codigo);
	}

	public List<Prontuario> readAllProntuarios() throws SQLException {
		return prontuarioRepository.readAll();
	}

	public Prontuario readProntuarioByConsulta(int consultaCodigo) throws SQLException {
		return prontuarioRepository.readByConsulta(consultaCodigo);
	}

	
	public void create(Receituario r) throws SQLException {
		receituarioRepository.create(r);
	}

	public void update(Receituario r) throws SQLException {
		receituarioRepository.update(r);
	}

	public Receituario readReceituario(int codigo) throws SQLException {
		return receituarioRepository.read(codigo);
	}

	public void deleteReceituario(int codigo) throws SQLException {
		receituarioRepository.delete(codigo);
	}

	public List<Receituario> readAllReceituarios() throws SQLException {
		return receituarioRepository.readAll();
	}

	public Receituario readReceituarioByProntuario(int prontuarioCodigo) throws SQLException {
		return receituarioRepository.readByProntuario(prontuarioCodigo);
	}


	public void create(ItemReceituario item) throws SQLException {
		itemReceituarioRepository.create(item);
	}

	public void update(ItemReceituario item) throws SQLException {
		itemReceituarioRepository.update(item);
	}

	public void deleteItemReceituario(int codigo) throws SQLException {
		itemReceituarioRepository.delete(codigo);
	}

	public List<ItemReceituario> readItensByReceituario(int receituarioCodigo) throws SQLException {
		return itemReceituarioRepository.readByReceituario(receituarioCodigo);
	}
}
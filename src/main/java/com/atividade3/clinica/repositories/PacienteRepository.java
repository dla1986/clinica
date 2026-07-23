package com.atividade3.clinica.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Paciente;


public class PacienteRepository implements Repository<Paciente, String> {

	@Override
	public void create(Paciente paciente) throws SQLException {

		String sql = """
				INSERT INTO paciente
				(cpf, nome, endereco, contato, plano_saude)
				VALUES (?,?,?,?,?)
				""";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		
		pstm.setString(1, paciente.getCpf());
		pstm.setString(2, paciente.getNome());
		pstm.setString(3, paciente.getEndereco());
		pstm.setString(4, paciente.getContato()); 
		pstm.setString(5, paciente.getPlanoSaude());

		pstm.execute();
	}

	@Override
	public Paciente read(String cpf) throws SQLException {

		String sql = "SELECT * FROM paciente WHERE cpf=?";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		pstm.setString(1, cpf); 

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			Paciente p = new Paciente();

			p.setCpf(rs.getString("cpf"));
			p.setNome(rs.getString("nome"));
			p.setEndereco(rs.getString("endereco"));
			p.setContato(rs.getString("contato")); 
			p.setPlanoSaude(rs.getString("plano_saude")); 

			return p;
		}

		return null;
	}

	@Override
	public void update(Paciente paciente) throws SQLException {

		String sql = """
				UPDATE paciente
				SET nome=?,
				    endereco=?,
				    contato=?,
				    plano_saude=?
				WHERE cpf=?
				""";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		pstm.setString(1, paciente.getNome());
		pstm.setString(2, paciente.getEndereco());
		pstm.setString(3, paciente.getContato());
		pstm.setString(4, paciente.getPlanoSaude());
		pstm.setString(5, paciente.getCpf()); 

		pstm.execute();
	}

	@Override
	public void delete(String cpf) throws SQLException {

		String sql = "DELETE FROM paciente WHERE cpf=?";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		pstm.setString(1, cpf);

		pstm.execute();
	}

	@Override
	public List<Paciente> readAll() throws SQLException {

		String sql = "SELECT * FROM paciente ORDER BY nome";

		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();

		List<Paciente> pacientes = new ArrayList<>();

		while (rs.next()) {
			Paciente p = new Paciente();

			
			p.setCpf(rs.getString("cpf"));
			p.setNome(rs.getString("nome"));
			p.setEndereco(rs.getString("endereco"));
			p.setContato(rs.getString("contato"));
			p.setPlanoSaude(rs.getString("plano_saude"));

			pacientes.add(p);
		}

		return pacientes;
	}
}
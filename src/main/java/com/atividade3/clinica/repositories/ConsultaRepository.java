package com.atividade3.clinica.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Consulta;
import com.atividade3.clinica.entities.Prontuario;

public class ConsultaRepository implements Repository<Consulta, Integer> {

	
	private Prontuario buscarProntuarioPorConsulta(int consultaCodigo) throws SQLException {
		String sql = "SELECT * FROM prontuario WHERE consulta_codigo = ?";
		
	
		try (PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
			pstm.setInt(1, consultaCodigo);
			try (ResultSet rs = pstm.executeQuery()) {
				if (rs.next()) {
					Prontuario p = new Prontuario();
					p.setCodigo(rs.getInt("codigo"));
					p.setDescricao(rs.getString("descricao"));
					p.setObservacao(rs.getString("observacao"));
	
					return p;
				}
			}
		}
		return null; 
	}


	private Consulta fromResultSet(ResultSet rs) throws SQLException {
		Consulta c = new Consulta();
		c.setCodigo(rs.getInt("codigo"));
		c.setPacienteCpf(rs.getString("paciente_cpf"));
		c.setMedicoCrm(rs.getString("medico_crm"));
		c.setDataHora(rs.getString("data_hora"));
		c.setDataHoraVolta(rs.getString("data_hora_volta"));
		c.setObservacao(rs.getString("observacao"));
		
		
		c.setProntuario(buscarProntuarioPorConsulta(c.getCodigo()));
		
		return c;
	}

	@Override
	public void create(Consulta consulta) throws SQLException {
		String sql = """
				INSERT INTO consulta
				(paciente_cpf, medico_crm, data_hora, data_hora_volta, observacao)
				VALUES (?, ?, ?, ?, ?)
				""";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setString(1, consulta.getPacienteCpf());
		pstm.setString(2, consulta.getMedicoCrm());
		pstm.setString(3, consulta.getDataHora());
		pstm.setString(4, consulta.getDataHoraVolta());
		pstm.setString(5, consulta.getObservacao());
		pstm.execute();
	}

	@Override
	public Consulta read(Integer codigo) throws SQLException {
		String sql = "SELECT * FROM consulta WHERE codigo = ?";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setInt(1, codigo);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			return fromResultSet(rs);
		}
		return null;
	}

	@Override
	public void update(Consulta consulta) throws SQLException {
		String sql = """
				UPDATE consulta
				SET paciente_cpf    = ?,
				    medico_crm      = ?,
				    data_hora       = ?,
				    data_hora_volta = ?,
				    observacao      = ?
				WHERE codigo = ?
				""";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setString(1, consulta.getPacienteCpf());
		pstm.setString(2, consulta.getMedicoCrm());
		pstm.setString(3, consulta.getDataHora());
		pstm.setString(4, consulta.getDataHoraVolta());
		pstm.setString(5, consulta.getObservacao());
		pstm.setInt(6, consulta.getCodigo());
		pstm.execute();
	}

	@Override
	public void delete(Integer codigo) throws SQLException {
		String sql = "DELETE FROM consulta WHERE codigo = ?";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setInt(1, codigo);
		pstm.execute();
	}

	@Override
	public List<Consulta> readAll() throws SQLException {
		String sql = "SELECT * FROM consulta ORDER BY `data_hora`";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Consulta> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(fromResultSet(rs));
		}
		return lista;
	}
}
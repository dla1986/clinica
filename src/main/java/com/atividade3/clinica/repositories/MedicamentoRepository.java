package com.atividade3.clinica.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Medicamento;

public class MedicamentoRepository implements Repository<Medicamento, Integer> {


	private Medicamento fromResultSet(ResultSet rs) throws SQLException {
		Medicamento m = new Medicamento();
		m.setCodigo(rs.getInt("codigo"));
		m.setNome(rs.getString("nome"));
		m.setDosagem(rs.getInt("dosagem"));
		m.setTipoDosagem(rs.getString("tipo_dosagem"));
		m.setDescricao(rs.getString("descricao"));
		m.setObservacao(rs.getString("observacao"));
		return m;
	}

	@Override
	public void create(Medicamento medicamento) throws SQLException {
		String sql = """
				INSERT INTO medicamento
				(nome, dosagem, tipo_dosagem, descricao, observacao)
				VALUES (?, ?, ?, ?, ?)
				""";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setString(1, medicamento.getNome());
		pstm.setInt(2, medicamento.getDosagem());
		pstm.setString(3, medicamento.getTipoDosagem());
		pstm.setString(4, medicamento.getDescricao());
		pstm.setString(5, medicamento.getObservacao());
		pstm.execute();
	}

	@Override
	public Medicamento read(Integer codigo) throws SQLException {
		String sql = "SELECT * FROM medicamento WHERE codigo = ?";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setInt(1, codigo);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			return fromResultSet(rs);
		}
		return null;
	}

	@Override
	public void update(Medicamento medicamento) throws SQLException {
		String sql = """
				UPDATE medicamento
				SET nome        = ?,
				    dosagem     = ?,
				    tipo_dosagem = ?,
				    descricao   = ?,
				    observacao  = ?
				WHERE codigo = ?
				""";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setString(1, medicamento.getNome());
		pstm.setInt(2, medicamento.getDosagem());
		pstm.setString(3, medicamento.getTipoDosagem());
		pstm.setString(4, medicamento.getDescricao());
		pstm.setString(5, medicamento.getObservacao());
		pstm.setInt(6, medicamento.getCodigo());
		pstm.execute();
	}

	@Override
	public void delete(Integer codigo) throws SQLException {
		String sql = "DELETE FROM medicamento WHERE codigo = ?";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		pstm.setInt(1, codigo);
		pstm.execute();
	}

	@Override
	public List<Medicamento> readAll() throws SQLException {
		String sql = "SELECT * FROM medicamento ORDER BY nome";
		PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Medicamento> lista = new ArrayList<>();
		while (rs.next()) {
			lista.add(fromResultSet(rs));
		}
		return lista;
	}
}
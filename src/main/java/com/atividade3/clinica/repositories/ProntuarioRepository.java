package com.atividade3.clinica.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Prontuario;

public class ProntuarioRepository implements Repository<Prontuario, Integer> {

    @Override
    public void create(Prontuario prontuario) throws SQLException {

        String sql = """
                INSERT INTO prontuario
                (consulta_codigo, descricao, observacao)
                VALUES (?, ?, ?)
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, prontuario.getConsultaCodigo());
        pstm.setString(2, prontuario.getDescricao());
        pstm.setString(3, prontuario.getObservacao());

        pstm.execute();
    }

    @Override
    public Prontuario read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM prontuario WHERE codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, codigo);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {

            Prontuario p = new Prontuario();

            p.setCodigo(rs.getInt("codigo"));
            p.setConsultaCodigo(rs.getInt("consulta_codigo"));
            p.setDescricao(rs.getString("descricao"));
            p.setObservacao(rs.getString("observacao"));

            return p;
        }

        return null;
    }

 
    public Prontuario readByConsulta(int consultaCodigo)
            throws SQLException {

        String sql =
                "SELECT * FROM prontuario WHERE consulta_codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection()
                                 .prepareStatement(sql);

        pstm.setInt(1, consultaCodigo);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {

            Prontuario p = new Prontuario();

            p.setCodigo(rs.getInt("codigo"));
            p.setConsultaCodigo(rs.getInt("consulta_codigo"));
            p.setDescricao(rs.getString("descricao"));
            p.setObservacao(rs.getString("observacao"));

            return p;
        }

        return null;
    }

    @Override
    public void update(Prontuario prontuario) throws SQLException {

        String sql = """
                UPDATE prontuario
                SET consulta_codigo = ?,
                    descricao = ?,
                    observacao = ?
                WHERE codigo = ?
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, prontuario.getConsultaCodigo());
        pstm.setString(2, prontuario.getDescricao());
        pstm.setString(3, prontuario.getObservacao());
        pstm.setInt(4, prontuario.getCodigo());

        pstm.execute();
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM prontuario WHERE codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, codigo);

        pstm.execute();
    }

    @Override
    public List<Prontuario> readAll() throws SQLException {

        String sql = "SELECT * FROM prontuario ORDER BY codigo DESC";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Prontuario> lista = new ArrayList<>();

        while (rs.next()) {

            Prontuario p = new Prontuario();

            p.setCodigo(rs.getInt("codigo"));
            p.setConsultaCodigo(rs.getInt("consulta_codigo"));
            p.setDescricao(rs.getString("descricao"));
            p.setObservacao(rs.getString("observacao"));

            lista.add(p);
        }

        return lista;
    }
}
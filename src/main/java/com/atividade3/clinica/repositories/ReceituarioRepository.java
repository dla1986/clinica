package com.atividade3.clinica.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Receituario;

public class ReceituarioRepository implements Repository<Receituario, Integer> {

    @Override
    public void create(Receituario receituario) throws SQLException {

        String sql = """
                INSERT INTO receituario
                (prontuario_codigo, observacao)
                VALUES (?, ?)
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, receituario.getProntuarioCodigo());
        pstm.setString(2, receituario.getObservacao());

        pstm.execute();
    }

    @Override
    public Receituario read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM receituario WHERE codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, codigo);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {

            Receituario r = new Receituario();

            r.setCodigo(rs.getInt("codigo"));
            r.setProntuarioCodigo(rs.getInt("prontuario_codigo"));
            r.setObservacao(rs.getString("observacao"));

            return r;
        }

        return null;
    }

    public Receituario readByProntuario(int prontuarioCodigo)
            throws SQLException {

        String sql =
                "SELECT * FROM receituario WHERE prontuario_codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection()
                                 .prepareStatement(sql);

        pstm.setInt(1, prontuarioCodigo);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {

            Receituario r = new Receituario();

            r.setCodigo(rs.getInt("codigo"));
            r.setProntuarioCodigo(rs.getInt("prontuario_codigo"));
            r.setObservacao(rs.getString("observacao"));

            return r;
        }

        return null;
    }

    @Override
    public void update(Receituario receituario) throws SQLException {

        String sql = """
                UPDATE receituario
                SET prontuario_codigo = ?,
                    observacao = ?
                WHERE codigo = ?
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, receituario.getProntuarioCodigo());
        pstm.setString(2, receituario.getObservacao());
        pstm.setInt(3, receituario.getCodigo());

        pstm.execute();
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM receituario WHERE codigo = ?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, codigo);

        pstm.execute();
    }

    @Override
    public List<Receituario> readAll() throws SQLException {

        String sql = "SELECT * FROM receituario ORDER BY codigo DESC";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Receituario> lista = new ArrayList<>();

        while (rs.next()) {

            Receituario r = new Receituario();

            r.setCodigo(rs.getInt("codigo"));
            r.setProntuarioCodigo(rs.getInt("prontuario_codigo"));
            r.setObservacao(rs.getString("observacao"));

            lista.add(r);
        }

        return lista;
    }
}
package com.atividade3.clinica.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.Medico;

public class MedicoRepository implements Repository<Medico, String> {

    @Override
    public void create(Medico medico) throws SQLException {

        String sql = """
                INSERT INTO medico
                (crm, nome, especialidade, contato, login, senha)
                VALUES (?,?,?,?,?,?)
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, medico.getCrm());
        pstm.setString(2, medico.getNome());
        pstm.setString(3, medico.getEspecialidade());
        pstm.setString(4, medico.getContato());
        pstm.setString(5, medico.getLogin());
        pstm.setString(6, medico.getSenha());

        pstm.execute();
    }

    @Override
    public Medico read(String crm) throws SQLException {

        String sql = "SELECT * FROM medico WHERE crm=?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, crm);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            Medico m = new Medico();
            m.setCrm(rs.getString("crm"));
            m.setNome(rs.getString("nome"));
            m.setEspecialidade(rs.getString("especialidade"));
            m.setContato(rs.getString("contato"));
            m.setLogin(rs.getString("login"));
            m.setSenha(rs.getString("senha"));
            return m;
        }

        return null;
    }

    @Override
    public void update(Medico medico) throws SQLException {

        String sql = """
                UPDATE medico
                SET nome=?,
                    especialidade=?,
                    contato=?,
                    login=?,
                    senha=?
                WHERE crm=?
                """;

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, medico.getNome());
        pstm.setString(2, medico.getEspecialidade());
        pstm.setString(3, medico.getContato());
        pstm.setString(4, medico.getLogin());
        pstm.setString(5, medico.getSenha());
        pstm.setString(6, medico.getCrm());

        pstm.execute();
    }

    @Override
    public void delete(String crm) throws SQLException {

        String sql = "DELETE FROM medico WHERE crm=?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, crm);

        pstm.execute();
    }

    @Override
    public List<Medico> readAll() throws SQLException {

        String sql = "SELECT * FROM medico ORDER BY nome";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();

        List<Medico> medicos = new ArrayList<>();

        while (rs.next()) {
            Medico m = new Medico();
            m.setCrm(rs.getString("crm"));
            m.setNome(rs.getString("nome"));
            m.setEspecialidade(rs.getString("especialidade"));
            m.setContato(rs.getString("contato"));
            m.setLogin(rs.getString("login"));
            m.setSenha(rs.getString("senha"));
            medicos.add(m);
        }

        return medicos;
    }

    public Medico login(String crm, String senha) throws SQLException {

        String sql = "SELECT * FROM medico WHERE crm=? AND senha=?";

        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, crm);
        pstm.setString(2, senha);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            Medico medico = new Medico();
            medico.setCrm(rs.getString("crm"));
            medico.setNome(rs.getString("nome"));
            medico.setEspecialidade(rs.getString("especialidade"));
            medico.setContato(rs.getString("contato"));
            medico.setLogin(rs.getString("login"));
            medico.setSenha(rs.getString("senha"));
            return medico;
        }

        return null;
    }
}
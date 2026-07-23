package com.atividade3.clinica.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.atividade3.clinica.entities.ItemReceituario;

public class ItemReceituarioRepository {


    private ItemReceituario fromResultSet(ResultSet rs) throws SQLException {
        ItemReceituario item = new ItemReceituario();
        item.setCodigo(rs.getInt("codigo"));
        item.setReceituarioCodigo(rs.getInt("receituario_codigo"));
        item.setMedicamentoCodigo(rs.getInt("medicamento_codigo"));
        item.setDosagem(rs.getInt("dosagem"));
        item.setIntervaloEntreDoses(rs.getInt("intervalo_entre_doses"));
        item.setObservacao(rs.getString("observacao"));
        return item;
    }

    public void create(ItemReceituario item) throws SQLException {
        String sql = """
                INSERT INTO item_receituario
                (receituario_codigo, medicamento_codigo, dosagem,
                 intervalo_entre_doses, observacao)
                VALUES (?, ?, ?, ?, ?)
                """;
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,    item.getReceituarioCodigo());
        pstm.setInt(2,    item.getMedicamentoCodigo());
        pstm.setInt(3,    item.getDosagem());
        pstm.setInt(4,    item.getIntervaloEntreDoses());
        pstm.setString(5, item.getObservacao());
        pstm.execute();
    }

    public ItemReceituario read(int codigo) throws SQLException {
        String sql = "SELECT * FROM item_receituario WHERE codigo = ?";
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, codigo);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return fromResultSet(rs);
        return null;
    }

    public List<ItemReceituario> readByReceituario(int receituarioCodigo)
            throws SQLException {
        String sql =
                "SELECT * FROM item_receituario WHERE receituario_codigo = ?";
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, receituarioCodigo);
        ResultSet rs = pstm.executeQuery();
        List<ItemReceituario> lista = new ArrayList<>();
        while (rs.next()) lista.add(fromResultSet(rs));
        return lista;
    }

    public void update(ItemReceituario item) throws SQLException {
        String sql = """
                UPDATE item_receituario
                SET receituario_codigo   = ?,
                    medicamento_codigo   = ?,
                    dosagem              = ?,
                    intervalo_entre_doses = ?,
                    observacao           = ?
                WHERE codigo = ?
                """;
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1,    item.getReceituarioCodigo());
        pstm.setInt(2,    item.getMedicamentoCodigo());
        pstm.setInt(3,    item.getDosagem());
        pstm.setInt(4,    item.getIntervaloEntreDoses());
        pstm.setString(5, item.getObservacao());
        pstm.setInt(6,    item.getCodigo());
        pstm.execute();
    }

    public void delete(int codigo) throws SQLException {
        String sql = "DELETE FROM item_receituario WHERE codigo = ?";
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, codigo);
        pstm.execute();
    }

    public void deleteByReceituario(int receituarioCodigo) throws SQLException {
        String sql =
                "DELETE FROM item_receituario WHERE receituario_codigo = ?";
        PreparedStatement pstm =
                ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, receituarioCodigo);
        pstm.execute();
    }
}
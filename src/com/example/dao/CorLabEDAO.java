package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.CorLAB;
import com.example.model.CorLabE;
import com.example.model.Estagio;
import com.example.util.DatabaseUtil;

public class CorLabEDAO  
{
    public void salvar(CorLabE cor)  throws SQLException
    {
        String sql = "INSERT INTO padroes_lab (estagio_id, l_value, a_value, b_value) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, cor.estagio.getId());
        ps.setDouble(2, cor.cor.getL());
        ps.setDouble(3, cor.cor.getA());
        ps.setDouble(4, cor.cor.getB());

        ps.executeUpdate();
    }

    public List<CorLabE> listar() throws SQLException
    {
        String sql = "SELECT * FROM padroes_media_lab";
        List<CorLabE> cores = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            EstagioDao estagioDao = new EstagioDao();
            List<Estagio> estagios = estagioDao.listar();

            while (rs.next()) 
            {
                int estagio_id = rs.getInt("estagio_id");
                float l_value = rs.getFloat("l_value");
                float a_value = rs.getFloat("a_value");
                float b_value = rs.getFloat("b_value");
              
                CorLAB cor = new CorLAB(l_value, a_value, b_value);
                Estagio estagio = new Estagio();
                estagio.setId(estagio_id);

                for (Estagio e: estagios)
                {
                    if(e.getId() == estagio_id)
                    {
                        estagio.setNome(e.getNome());
                    }
                }
                CorLabE corEstagio = new CorLabE(cor, estagio);
                cores.add(corEstagio);
            }
        }
        return cores;
    }

    public void salvarListaLabEstagio(List<CorLabE> cores) throws SQLException 
    {
        if (cores == null || cores.isEmpty()) 
        {
            System.out.println("Lista de cores vazia. Nenhum dado será inserido.");
            return;
        }
    
        String sql = "INSERT INTO padroes_lab (estagio_id, l_value, a_value, b_value) VALUES (?, ?, ?, ?)";
    
 
        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        for (CorLabE cor : cores) 
        {
            ps.setInt(1, cor.getEstagio().getId());
            ps.setDouble(2, cor.getCor().getL());
            ps.setDouble(3, cor.getCor().getA());
            ps.setDouble(4, cor.getCor().getB());

            ps.addBatch();
        }

        ps.executeBatch();
    }

    public void salvarListaLabEMedia(List<CorLabE> cores) throws SQLException 
    {
        if (cores == null || cores.isEmpty()) 
        {
            System.out.println("Lista de cores vazia. Nenhum dado será inserido.");
            return;
        }
    
        String sql = "INSERT INTO padroes_media_lab (estagio_id, l_value, a_value, b_value) VALUES (?, ?, ?, ?)";
    
 
        Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        for (CorLabE cor : cores) 
        {
            ps.setInt(1, cor.getEstagio().getId());
            ps.setDouble(2, cor.getCor().getL());
            ps.setDouble(3, cor.getCor().getA());
            ps.setDouble(4, cor.getCor().getB());

            ps.addBatch();
        }

        ps.executeBatch();
    } 
}

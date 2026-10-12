package com.example.dao;

import com.example.model.Cor;
import com.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CorDAO 
{
    public void salvar(Cor cor)  throws SQLException
    {
        String sql = "INSERT INTO cores (r, g, b, h, s, v, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, cor.getR());
            ps.setInt(2, cor.getG());
            ps.setInt(3, cor.getB());
            ps.setFloat(4, cor.getH());
            ps.setFloat(5, cor.getS());
            ps.setFloat(6, cor.getV());
            ps.setInt(7, cor.getDescricao());
            
            ps.executeUpdate();
      
        } 
    }

    public List<Cor> listar() throws SQLException
    {
        String sql = "SELECT * FROM cores";
        List<Cor> cores = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) 
            {
                int r = rs.getInt("r");
                int g = rs.getInt("g");
                int b = rs.getInt("b");
                float h = rs.getFloat("h");
                float s = rs.getFloat("s");
                float v = rs.getFloat("v");
                int descricao = rs.getInt("descricao");
                
                Cor cor = new Cor(r, g, b, h, s, v, descricao);
                cores.add(cor);
            }
        }
        return cores;
    }
}
        
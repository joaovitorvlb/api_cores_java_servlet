package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Estagio;
import com.example.util.DatabaseUtil;

public class EstagioDao 
{
    private Connection connection;

    public EstagioDao() throws SQLException
    {
        this.connection = DatabaseUtil.getConnection();
    }  

    public List<Estagio> listar() throws SQLException
    {
        List<Estagio> estagios = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM estagios");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) 
        {
            Estagio estagio = new Estagio();
            estagio.setId(resultSet.getInt("id"));
            estagio.setNome(resultSet.getString("nome"));
            estagios.add(estagio);
        }

        return estagios;
    }
}


package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil 
{
    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/api_cores"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";
    private static final String USER = "api-cores";
    private static final String PASSWORD = "6052026";
    
    private static Connection conexao;

    public static Connection getConnection() throws SQLException
    {
        if (conexao != null && !conexao.isClosed())
        {
            return conexao;
        }

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new SQLException("Driver MySQL não encontrado: " + e.getMessage(), e);
        }

        conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        return conexao;
    }
}
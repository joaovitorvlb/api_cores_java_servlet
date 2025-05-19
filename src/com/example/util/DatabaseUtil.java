package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil 
{
    private static final String URL = "jdbc:postgresql://localhost:5432/aula_poo2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";
    
    private static Connection conexao;

    public static Connection getConnection() 
    {
        if (conexao == null) 
        {

            try 
            {
                Class.forName("org.postgresql.Driver");
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexão bem-sucedida!");
            } 
            catch (SQLException e) 
            {
                System.err.println("❌ Erro ao conectar: " + e.getMessage());
            }
            catch (ClassNotFoundException e) 
            {
                System.err.println("❌ Erro ao carregar o driver: " + e.getMessage());
            }
        }
        return conexao;
    }
}
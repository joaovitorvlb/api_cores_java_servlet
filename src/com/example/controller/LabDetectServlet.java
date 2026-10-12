package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import com.example.model.CorSratus;
import com.example.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cor_detect")
public class LabDetectServlet   extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001"); // Permitir apenas localhost:3000
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        // Read JSON data from the request body
        StringBuilder jsonBuffer = new StringBuilder();
        try 
        {
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) 
            {
                jsonBuffer.append(line);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        JSONObject requestData = new JSONObject(jsonBuffer.toString());

        // Extrair dados RGB e HSV
        int r = requestData.getInt("r");
        int g = requestData.getInt("g");
        int b = requestData.getInt("b");

        CorSratus status;
        try
        {
            status = CorController.testarCores(r, g, b);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            ResponseUtil.sendErrorResponse(response, 500, "Erro ao consultar o banco de dados", e.getMessage());
            return;
        }

        // Create JSON response with status
        JSONObject jsonResponse = new JSONObject();
        
        double distancia = status.getDistancia();
        String padrao = status.getEstagio();

        jsonResponse.put("distancia", distancia);
        jsonResponse.put("padrao", padrao);

        // Write the JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001"); // Permitir apenas localhost:3000
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}

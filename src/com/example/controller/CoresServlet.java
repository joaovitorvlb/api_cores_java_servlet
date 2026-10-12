package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import com.example.dao.CorDAO;
import com.example.model.Cor;
import com.example.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/color")
public class CoresServlet  extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3001"); // Permitir apenas localhost:3000
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read JSON data from the request body
        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                jsonBuffer.append(line);
            }
        }

        // Parse the JSON data
        JSONObject requestData = new JSONObject(jsonBuffer.toString());



        // Extrair dados RGB e HSV
        int r = requestData.getInt("r");
        int g = requestData.getInt("g");
        int b = requestData.getInt("b");
        float h = requestData.getFloat("h");
        float s = requestData.getFloat("s");
        float v = requestData.getFloat("v");
        int descricao = requestData.getInt("descricao");


        // Criar objeto Cor
        Cor cor = new Cor(r, g, b, h, s, v, descricao);


        try {
            CorDAO corDAO = new CorDAO();
            corDAO.salvar(cor);
        } catch (SQLException e) {
            e.printStackTrace();
            ResponseUtil.sendErrorResponse(response, 500, "Erro ao salvar no banco de dados", e.getMessage());
            return;
        }

        // Create a JSON response
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "Data received successfully");
        jsonResponse.put("receivedData", requestData);

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

        try 
        {
            CorDAO corDAO = new CorDAO();
            // Retrieve the list of colors from the database
            var cores = corDAO.listar();

            // Convert the list of colors to JSON
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("cores", cores);

            // Write the JSON response
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
            ResponseUtil.sendErrorResponse(response, 500, "Erro ao recuperar dados do banco de dados", e.getMessage());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        // Responder ao preflight
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3001"); // ou "http://localhost:3001"
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}


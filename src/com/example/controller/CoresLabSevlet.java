package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.dao.CorLabEDAO;
import com.example.model.CorLabE;
import com.example.util.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cores")
public class CoresLabSevlet  extends HttpServlet
{
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // Permitir apenas localhost:3000
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        CorLabEDAO dao = new CorLabEDAO();

        List<CorLabE> cores;
        try
        {
            cores = dao.listar();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            ResponseUtil.sendErrorResponse(response, 500, "Erro ao consultar o banco de dados", e.getMessage());
            return;
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < cores.size(); i++) {
            CorLabE cor = cores.get(i);
            jsonBuilder.append("{")
                       .append("\"L\": ").append(cor.cor.getL()).append(", ")
                       .append("\"A\": ").append(cor.cor.getA()).append(", ")
                       .append("\"B\": ").append(cor.cor.getB())
                       .append("}");

            if (i < cores.size() - 1) {
                jsonBuilder.append(", ");
            }
        }

        jsonBuilder.append("]");

        // Escrever a resposta JSON
        response.getWriter().write(jsonBuilder.toString());
        response.getWriter().flush();
    }
}

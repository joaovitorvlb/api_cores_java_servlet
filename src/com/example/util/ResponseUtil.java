package com.example.util;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil 
{
    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String message, String details) throws IOException 
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);

        JSONObject errorResponse = new JSONObject();
        errorResponse.put("status", statusCode);
        errorResponse.put("error", message);
        if (details != null && !details.isEmpty()) 
        {
            errorResponse.put("details", details);
        }

        response.getWriter().write(errorResponse.toString());
        response.getWriter().flush();
    }
}

/*
try {
    CorDAO corDAO = new CorDAO();
    corDAO.salvar(cor);
} catch (SQLException e) {
    e.printStackTrace();
    // Enviar resposta de erro em JSON
    ResponseUtil.sendErrorResponse(response, 500, "Erro ao salvar no banco de dados", e.getMessage());
    return;
}
{
    "status": 500,
    "error": "Erro ao salvar no banco de dados",
    "details": "Column 'descricao' not found"
} 
 */
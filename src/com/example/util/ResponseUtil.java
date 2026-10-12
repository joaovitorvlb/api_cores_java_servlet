package com.example.util;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil 
{
    public static String jsonErro(int statusCode, String message, String details)
    {
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("status", statusCode);
        errorResponse.put("error", message);
        if (details != null && !details.isEmpty())
        {
            errorResponse.put("details", details);
        }
        return errorResponse.toString();
    }

    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String message, String details) throws IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(jsonErro(statusCode, message, details));
        response.getWriter().flush();
    }
}
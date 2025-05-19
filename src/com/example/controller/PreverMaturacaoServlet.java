package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/preverMaturacao")
public class PreverMaturacaoServlet extends HttpServlet 
{

    private static class Tomate 
    {
        int r, g, b;
        String maturacao;

        public Tomate(int r, int g, int b, String maturacao) 
        {
            this.r = r;
            this.g = g;
            this.b = b;
            this.maturacao = maturacao;
        }

        public double distancia(int r2, int g2, int b2) 
        {
            return Math.sqrt(Math.pow(r - r2, 2) + Math.pow(g - g2, 2) + Math.pow(b - b2, 2));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        List<Tomate> tomates = new ArrayList<>();

        // Ler JSON
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());

        int r = json.getInt("r");
        int g = json.getInt("g");
        int b = json.getInt("b");

        // Buscar dados do banco
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/bd", "usuario", "senha")) {
            PreparedStatement ps = conn.prepareStatement("SELECT r, g, b, maturacao FROM tomates WHERE maturacao IS NOT NULL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tomates.add(new Tomate(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Erro no banco");
            return;
        }

        // KNN
        int k = 5;
        tomates.sort(Comparator.comparingDouble(t -> t.distancia(r, g, b)));
        Map<String, Integer> contagem = new HashMap<>();
        for (int i = 0; i < k && i < tomates.size(); i++) {
            contagem.put(tomates.get(i).maturacao, contagem.getOrDefault(tomates.get(i).maturacao, 0) + 1);
        }

        // Maturação mais frequente
        String resultado = contagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Desconhecido");

        // Responder
        JSONObject resposta = new JSONObject();
        resposta.put("maturacao_prevista", resultado);

        response.setContentType("application/json");
        response.getWriter().write(resposta.toString());
    }
}
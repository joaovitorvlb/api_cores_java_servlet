package com.example.util;

import java.sql.SQLException;

import org.json.JSONObject;

public class TesteRespostaErro
{
    public static void main(String[] args)
    {
        String mensagemSql = new SQLException(
                "Access denied for user 'api-cores'@'172.17.0.1' (using password: YES)").getMessage();

        String corpo = ResponseUtil.jsonErro(500, "Erro ao salvar no banco de dados", mensagemSql);
        JSONObject json = new JSONObject(corpo);

        exigir(json.getInt("status") == 500, "status deve ser 500");
        exigir("Erro ao salvar no banco de dados".equals(json.getString("error")), "error deve descrever a operação");
        exigir(json.getString("details").equals(mensagemSql), "details deve trazer a mensagem do banco");

        JSONObject semDetalhe = new JSONObject(ResponseUtil.jsonErro(500, "Erro no banco", null));
        exigir(!semDetalhe.has("details"), "details fica de fora quando o banco não manda texto");

        System.out.println("TesteRespostaErro ok");
    }

    private static void exigir(boolean condicao, String falha)
    {
        if (!condicao)
        {
            System.err.println(falha);
            System.exit(1);
        }
    }
}

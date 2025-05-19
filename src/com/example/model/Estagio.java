package com.example.model;

public class Estagio 
{
    int id;
    String nome;

    public Estagio() 
    {
        
    }

    public Estagio(int id, String nome) 
    {
        this.id = id;
        this.nome = nome;
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }   

    @Override
    public String toString() 
    {
        return "Estagio{" + "id=" + id + ", nome=" + nome + '}';
    }
}

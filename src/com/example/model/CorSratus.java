package com.example.model;

public class CorSratus 
{
    double distancia;
    String estagio;

    public CorSratus() 
    {
    }

    public CorSratus(double distancia, String estagio) 
    {
        this.distancia = distancia;
        this.estagio = estagio;
    }

    public double getDistancia() 
    {
        return distancia;
    }

    public void setDistancia(double distancia) 
    {
        this.distancia = distancia;
    }

    public String getEstagio() 
    {
        return estagio;
    }

    public void setEstagio(String estagio) 
    {
        this.estagio = estagio;
    }

    @Override
    public String toString() 
    {
        return "CorSratus{" +
                "distancia=" + distancia +
                ", estagio='" + estagio + '\'' +
                '}';
    }
}

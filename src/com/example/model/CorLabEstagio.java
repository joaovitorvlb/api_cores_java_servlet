package com.example.model;

public class CorLabEstagio 
{
    public CorLAB cor;
    public Estagio estagio;

    public CorLabEstagio()
    {
        
    }

    public CorLabEstagio(CorLAB cor, Estagio estagio) 
    {
        this.cor = cor;
        this.estagio = estagio;
    }

    public CorLAB getCor() 
    {
        return cor;
    }

    public void setCor(CorLAB cor) 
    {
        this.cor = cor;
    }

    public Estagio getEstagio() 
    {
        return estagio;
    }
    
    public void setEstagio(Estagio estagio) 
    {
        this.estagio = estagio;
    }

    @Override
    public String toString() 
    {
        return "CorLabEstagio{" + "cor=" + cor + ", estagio=" + estagio + '}';
    }
    
}





package com.example.model;

public class CorRgbE 
{
    public CorRGB cor;
    public Estagio estagio;

    public CorRgbE()
    {
        
    }

    public CorRgbE(CorRGB cor, Estagio estagio) 
    {
        this.cor = cor;
        this.estagio = estagio;
    }

    public CorRGB getCor() 
    {
        return cor;
    }

    public void setCor(CorRGB cor) 
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
        return "CorRgbEstagio{" + "cor=" + cor + ", estagio=" + estagio + '}';
    }
}


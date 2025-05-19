package com.example.model;

public class Cor 
{
    private int r;
    private int g;
    private int b;
    private float h;
    private float s;
    private float v;
    private int descricao;

    public Cor(int r, int g, int b, float h, float s, float v, int descricao) 
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.h = h;
        this.s = s;
        this.v = v;
        this.descricao = descricao;
    }

    // Getters and setters
    public int getR() 
    { 
        return r; 
    }

    public void setR(int r) 
    { 
        this.r = r; 
    }

    public int getG() 
    { 
        return g; 
    }

    public void setG(int g) 
    { 
        this.g = g; 
    }

    public int getB() 
    { 
        return b; 
    }

    public void setB(int b) 
    { 
        this.b = b; 
    }

    public float getH() 
    { 
        return h; 
    }

    public void setH(float h) 
    { 
        this.h = h; 
    }

    public float getS() 
    { 
        return s; 
    }

    public void setS(float s) 
    { 
        this.s = s; 
    }

    public float getV() 
    { 
        return v; 
    }

    public void setV(float v) 
    { 
        this.v = v; 
    }

    public int getDescricao() 
    { 
        return descricao; 
    }

    public void setDescricao(int descricao) 
    { 
        this.descricao = descricao; 
    }
}

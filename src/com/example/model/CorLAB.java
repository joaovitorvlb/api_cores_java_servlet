package com.example.model;

public class CorLAB 
{
    private double l;
    private double a;
    private double b;

    public CorLAB() 
    {
        this.l = 0;
        this.a = 0;
        this.b = 0;
    }

    public CorLAB(double l, double a, double b) 
    {
        this.l = l;
        this.a = a;
        this.b = b;;
    }

    // Getters and setters
    public double getL() 
    { 
        return l; 
    }

    public void setL(double l) 
    { 
        this.l = l; 
    }

    public double getA() 
    { 
        return a; 
    }

    public void setA(double a) 
    { 
        this.a = a; 
    }

    public double getB() 
    { 
        return b; 
    }

    public void setB(double b) 
    { 
        this.b = b; 
    }

    @Override
    public String toString() 
    {
        return "CorLAB [L=" + l + ", A=" + a + ", B=" + b + "]";
    }  
    
}

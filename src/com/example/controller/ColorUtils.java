package com.example.controller;

import com.example.model.CorLAB;

public class ColorUtils 
{
     // Etapa completa: RGB → LAB
    public static CorLAB rgbToLab(int R, int G, int B) 
    {
        CorLAB corLAB = new CorLAB();
        corLAB.setL(rgbToXyz(R, G, B)[0]);

        double[] xyz = rgbToXyz(R, G, B);
        double[] lab = xyzToLab(xyz[0], xyz[1], xyz[2]);

        corLAB.setA(lab[1]);
        corLAB.setB(lab[2]);
        corLAB.setL(lab[0]);

        return corLAB;
    }

    public static double[] rgbToXyz(int R, int G, int B) {
        double r = pivotRgb(R / 255.0);
        double g = pivotRgb(G / 255.0);
        double b = pivotRgb(B / 255.0);

        // Matriz de transformação sRGB → XYZ (D65)
        double X = r * 0.4124 + g * 0.3576 + b * 0.1805;
        double Y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        double Z = r * 0.0193 + g * 0.1192 + b * 0.9505;

        // Escala XYZ de 0 a 100
        return new double[]{X * 100.0, Y * 100.0, Z * 100.0};
    }

    public static double[] xyzToLab(double X, double Y, double Z) {
        // Ponto branco de referência D65
        final double Xn = 95.047;
        final double Yn = 100.000;
        final double Zn = 108.883;

        double fx = pivotXyz(X / Xn);
        double fy = pivotXyz(Y / Yn);
        double fz = pivotXyz(Z / Zn);

        double L = 116 * fy - 16;
        double a = 500 * (fx - fy);
        double b = 200 * (fy - fz);

        return new double[]{L, a, b};
    }

    private static double pivotXyz(double t) 
    {
        return (t > 0.008856) ? Math.cbrt(t) : (7.787 * t + 16.0 / 116.0);
    }

    private static double pivotRgb(double c) 
    {
        return (c <= 0.04045) ? (c / 12.92) : Math.pow((c + 0.055) / 1.055, 2.4);
    }
}

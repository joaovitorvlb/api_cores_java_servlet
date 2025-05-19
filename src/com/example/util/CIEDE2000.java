package com.example.util;

public class CIEDE2000 
{
    public static double calculateCIEDE2000(double L1, double a1, double b1, double L2, double a2, double b2) {
        
        // Etapa 1: Chroma original
        double C1 = Math.hypot(a1, b1);
        double C2 = Math.hypot(a2, b2);
        double C_bar = (C1 + C2) / 2.0;

        // Etapa 2: Fator de correção G
        double G = 0.5 * (1 - Math.sqrt(Math.pow(C_bar, 7) / (Math.pow(C_bar, 7) + Math.pow(25.0, 7))));

        // Etapa 3: a' ajustado
        double a1Prime = (1 + G) * a1;
        double a2Prime = (1 + G) * a2;

        // Etapa 4: C' recalculado
        double C1Prime = Math.hypot(a1Prime, b1);
        double C2Prime = Math.hypot(a2Prime, b2);
        double C_bar_prime = (C1Prime + C2Prime) / 2.0;

        // Etapa 5: h'
        double h1Prime = Math.toDegrees(Math.atan2(b1, a1Prime)) % 360;
        double h2Prime = Math.toDegrees(Math.atan2(b2, a2Prime)) % 360;
        if (h1Prime < 0) h1Prime += 360;
        if (h2Prime < 0) h2Prime += 360;

        // Etapa 6: Diferenças ΔL', ΔC', ΔH'
        double deltaLPrime = L2 - L1;
        double deltaCPrime = C2Prime - C1Prime;

        double deltahPrime;
        if (Math.abs(h1Prime - h2Prime) <= 180) {
            deltahPrime = h2Prime - h1Prime;
        } else {
            deltahPrime = (h2Prime <= h1Prime) ? h2Prime - h1Prime + 360 : h2Prime - h1Prime - 360;
        }

        double deltaHPrime = 2 * Math.sqrt(C1Prime * C2Prime) *
            Math.sin(Math.toRadians(deltahPrime / 2.0));

        // Etapa 7: Médias L', C', h'
        double L_bar_prime = (L1 + L2) / 2.0;

        double h_bar_prime;
        if (Math.abs(h1Prime - h2Prime) <= 180) {
            h_bar_prime = (h1Prime + h2Prime) / 2.0;
        } else {
            h_bar_prime = (h1Prime + h2Prime + 360) / 2.0;
        }
        h_bar_prime %= 360;

        // Etapa 8: Correção T
        double T = 1
            - 0.17 * Math.cos(Math.toRadians(h_bar_prime - 30))
            + 0.24 * Math.cos(Math.toRadians(2 * h_bar_prime))
            + 0.32 * Math.cos(Math.toRadians(3 * h_bar_prime + 6))
            - 0.20 * Math.cos(Math.toRadians(4 * h_bar_prime - 63));

        // Etapa 9: Pesos SL, SC, SH
        double S_L = 1 + (0.015 * Math.pow(L_bar_prime - 50, 2)) /
                          Math.sqrt(20 + Math.pow(L_bar_prime - 50, 2));
        double S_C = 1 + 0.045 * C_bar_prime;
        double S_H = 1 + 0.015 * C_bar_prime * T;

        // Etapa 10: Correlação RT
        double deltaTheta = 30 * Math.exp(-Math.pow((h_bar_prime - 275) / 25.0, 2));
        double R_C = 2 * Math.sqrt(Math.pow(C_bar_prime, 7) /
                    (Math.pow(C_bar_prime, 7) + Math.pow(25.0, 7)));
        double R_T = -R_C * Math.sin(Math.toRadians(2 * deltaTheta));

        // Etapa 11: Resultado final
        double deltaE = Math.sqrt(
            Math.pow(deltaLPrime / S_L, 2) +
            Math.pow(deltaCPrime / S_C, 2) +
            Math.pow(deltaHPrime / S_H, 2) +
            R_T * (deltaCPrime / S_C) * (deltaHPrime / S_H)
        );

        return deltaE;
    }
}

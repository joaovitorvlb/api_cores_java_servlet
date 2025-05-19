package com.example.controller;

import java.util.List;

import com.example.dao.CorLabEDAO;
import com.example.model.CorLAB;
import com.example.model.CorRGB;
import com.example.model.CorSratus;
import com.example.util.CIEDE2000;
import com.example.model.CorLabE; // Ensure this import matches the actual package of CorLabE

public class CorController {

    public static CorSratus testarCores(int r, int g, int b) 
    {
        CorRGB cor = new CorRGB(r, g, b );

        System.out.println(cor);

        CorSratus statuss = CorController.verificarEstagioDistancia(cor);
    
        return statuss;
    }

    private static CorSratus verificarEstagioDistancia(CorRGB cor) 
    {
        double menorDistancia = Double.MAX_VALUE;
        CorSratus corSratus = new CorSratus();
        CorLabEDAO corLabEDAO = new CorLabEDAO();
        List<CorLabE> listaCores = corLabEDAO.listar();
        CorLAB corLab = ColorUtils.rgbToLab(cor.getR(), cor.getG(), cor.getB());
        

        for (CorLabE corLabEstagio : listaCores) 
        {
            double deltaE = CIEDE2000.calculateCIEDE2000(
                corLab.getL(), 
                corLab.getA(), 
                corLab.getB(), 
                corLabEstagio.cor.getL(), 
                corLabEstagio.cor.getA(), 
                corLabEstagio.cor.getB()
            );

            System.out.println("Delta E: " + deltaE);

            if (deltaE < menorDistancia) 
            {
                menorDistancia = deltaE; // Atualiza o menor deltaE
                corSratus.setDistancia(deltaE);
                corSratus.setEstagio(corLabEstagio.getEstagio().getNome());
            }
        }
        return corSratus;
    }
}

package MODELOS;

import java.util.Scanner;

public class MainEsquinaNoroeste {

    public static void main(String[] args) {
        int[] oferta = {5, 10, 15};
        int[] demanda = {3, 3, 12, 12};
        int costos[][] = {{20, 11, 3, 6},
        {5, 9, 10, 2},
        {18, 7, 4, 1}};

        EsquinaNoroesteNormal problema = new EsquinaNoroesteNormal(3, 4);
        problema.setS(oferta);
        problema.setD(demanda);
        problema.setC(costos);

        problema.Resolver_ENO();

        problema.Resultado();

    }
}

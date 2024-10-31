package MODELOS;

public class CostominimoMain {

    public static void main(String[] args) {
        
        int[][] costos = {
            {20, 11, 3, 6},
            {5, 9, 10, 2},
            {18, 7, 4, 1}
        };

        int[] oferta = {5, 10, 15}; 
        int[] demanda = {3, 3, 12, 12}; 

        
        CostoMinimo problema = new CostoMinimo();

        
        int[][] transporte = problema.resolverCostoMinimo(oferta, demanda, costos);

        
        problema.imprimirSolucion(transporte);
    }
}

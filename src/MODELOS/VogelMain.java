package MODELOS;

import java.util.Arrays;

public class VogelMain {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {

        int[][] costos = {
            {2, 3, 5, 6},
            {2, 1, 3, 5},
            {3, 8, 4, 6}
        };

        int[] suministro = {5, 10, 15};

        int[] demanda = {12, 8, 4, 6};

        int[][] resultado = aproximacionVogel(costos, suministro, demanda);
        imprimirSolucion(resultado, costos);
    }

    public static int[][] aproximacionVogel(int[][] costos, int[] suministro, int[] demanda) {
        int[][] asignacion = new int[suministro.length][demanda.length];
        for (int[] fila : asignacion) {
            Arrays.fill(fila, 0);
        }

        while (true) {

            int[] penalizacionSuministro = calcularPenalizacion(costos, suministro, true);
            int[] penalizacionDemanda = calcularPenalizacion(costos, demanda, false);

            int penalizacionMaxima = -1;
            int fila = -1, columna = -1;
            boolean esSuministro = true;

            for (int i = 0; i < penalizacionSuministro.length; i++) {
                if (suministro[i] > 0 && penalizacionSuministro[i] > penalizacionMaxima) {
                    penalizacionMaxima = penalizacionSuministro[i];
                    fila = i;
                    esSuministro = true;
                }
            }

            for (int j = 0; j < penalizacionDemanda.length; j++) {
                if (demanda[j] > 0 && penalizacionDemanda[j] > penalizacionMaxima) {
                    penalizacionMaxima = penalizacionDemanda[j];
                    columna = j;
                    esSuministro = false;
                }
            }

            if (esSuministro) {
                columna = encontrarIndiceMinCosto(costos[fila], demanda);
                asignar(fila, columna, suministro, demanda, asignacion);
            } else {
                fila = encontrarIndiceMinCosto(obtenerColumna(costos, columna), suministro);
                asignar(fila, columna, suministro, demanda, asignacion);
            }

            if (asignacionCompleta(suministro, demanda)) {
                break;
            }
        }
        return asignacion;
    }

    private static int[] calcularPenalizacion(int[][] costos, int[] array, boolean esFila) {
        int[] penalizaciones = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                int[] costosOrdenados = esFila ? Arrays.stream(costos[i]).filter(x -> x != INF).sorted().toArray()
                        : Arrays.stream(obtenerColumna(costos, i)).filter(x -> x != INF).sorted().toArray();
                penalizaciones[i] = costosOrdenados.length >= 2 ? costosOrdenados[1] - costosOrdenados[0] : costosOrdenados[0];
            }
        }
        return penalizaciones;
    }

    private static int encontrarIndiceMinCosto(int[] array, int[] restricciones) {
        int minIndice = -1, minValor = INF;
        for (int i = 0; i < array.length; i++) {
            if (restricciones[i] > 0 && array[i] < minValor) {
                minValor = array[i];
                minIndice = i;
            }
        }
        return minIndice;
    }

    private static int[] obtenerColumna(int[][] matriz, int col) {
        return Arrays.stream(matriz).mapToInt(fila -> fila[col]).toArray();
    }

    private static void asignar(int fila, int columna, int[] suministro, int[] demanda, int[][] asignacion) {
        int cantidad = Math.min(suministro[fila], demanda[columna]);
        asignacion[fila][columna] = cantidad;
        suministro[fila] -= cantidad;
        demanda[columna] -= cantidad;
    }

    private static boolean asignacionCompleta(int[] suministro, int[] demanda) {
        return Arrays.stream(suministro).allMatch(s -> s == 0) && Arrays.stream(demanda).allMatch(d -> d == 0);
    }

    private static void imprimirSolucion(int[][] asignacion, int[][] costos) {
        int costoTotal = 0;
        System.out.println("Matriz de Asignación:");
        for (int i = 0; i < asignacion.length; i++) {
            for (int j = 0; j < asignacion[i].length; j++) {
                System.out.print(asignacion[i][j] + "\t");
                costoTotal += asignacion[i][j] * costos[i][j];
            }
            System.out.println();
        }
        System.out.println("Costo Total: " + costoTotal);
    }
}

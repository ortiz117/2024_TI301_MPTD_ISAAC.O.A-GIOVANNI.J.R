package MODELOS;

import java.util.Arrays;

public class VogelMODELO {

    private int N_suministro;
    private int N_demanda;
    private int[] s;
    private int[] d;
    private int[][] c;
    private int[][] estado;

    public VogelMODELO(int N_suministro, int N_demanda) {
        this.N_suministro = N_suministro;
        this.N_demanda = N_demanda;
        this.estado = new int[N_suministro][N_demanda];
    }

    public void setS(int[] s) {
        this.s = s;
    }

    public void setD(int[] d) {
        this.d = d;
    }

    public void setC(int[][] c) {
        this.c = c;
    }

    public void resolverVogel() {
        while (!todosSatisfechos(s, d)) {
            int[] penalizacionSuministro = calcularPenalizacion(true);
            int[] penalizacionDemanda = calcularPenalizacion(false);

            int penalizacionMaxima = -1;
            int fila = -1, columna = -1;
            boolean esSuministro = true;

            for (int i = 0; i < penalizacionSuministro.length; i++) {
                if (s[i] > 0 && penalizacionSuministro[i] > penalizacionMaxima) {
                    penalizacionMaxima = penalizacionSuministro[i];
                    fila = i;
                    esSuministro = true;
                }
            }

            for (int j = 0; j < penalizacionDemanda.length; j++) {
                if (d[j] > 0 && penalizacionDemanda[j] > penalizacionMaxima) {
                    penalizacionMaxima = penalizacionDemanda[j];
                    columna = j;
                    esSuministro = false;
                }
            }

            if (esSuministro) {
                columna = encontrarIndiceMinCosto(c[fila], d);
                asignar(fila, columna);
            } else {
                fila = encontrarIndiceMinCosto(obtenerColumna(c, columna), s);
                asignar(fila, columna);
            }

            if (asignacionCompleta(s, d)) {
                break;
            }
        }
    }

    private static boolean todosSatisfechos(int[] suministro, int[] demanda) {
        return Arrays.stream(suministro).allMatch(s -> s == 0) && Arrays.stream(demanda).allMatch(d -> d == 0);
    }

    private int[] calcularPenalizacion(boolean esFila) {
        int[] penalizaciones = new int[esFila ? N_suministro : N_demanda];
        for (int i = 0; i < penalizaciones.length; i++) {
            if ((esFila ? s[i] : d[i]) > 0) {
                int[] costosOrdenados = esFila ? Arrays.stream(c[i]).filter(x -> x != Integer.MAX_VALUE).sorted().toArray()
                        : Arrays.stream(obtenerColumna(c, i)).filter(x -> x != Integer.MAX_VALUE).sorted().toArray();
                penalizaciones[i] = costosOrdenados.length >= 2 ? costosOrdenados[1] - costosOrdenados[0] : costosOrdenados[0];
            }
        }
        return penalizaciones;
    }

    private int encontrarIndiceMinCosto(int[] array, int[] restricciones) {
        int minIndice = -1;
        int minValor = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (restricciones[i] > 0 && array[i] < minValor) {
                minValor = array[i];
                minIndice = i;
            }
        }
        return minIndice;
    }

    private int[] obtenerColumna(int[][] matriz, int col) {
        return Arrays.stream(matriz).mapToInt(fila -> fila[col]).toArray();
    }

    private void asignar(int fila, int columna) {
        int cantidad = Math.min(s[fila], d[columna]);
        estado[fila][columna] = cantidad;
        s[fila] -= cantidad;
        d[columna] -= cantidad;
    }

    private boolean asignacionCompleta(int[] suministro, int[] demanda) {
        return Arrays.stream(suministro).allMatch(s -> s == 0) && Arrays.stream(demanda).allMatch(d -> d == 0);
    }

    public StringBuilder Resultado() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < N_suministro; i++) {
            for (int j = 0; j < N_demanda; j++) {
                str.append(estado[i][j]).append(" ");
            }
            str.append("\n");
        }
        return str;
    }
}

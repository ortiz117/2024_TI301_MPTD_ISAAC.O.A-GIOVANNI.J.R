package MODELOS;

public class CostoMinimoModelo {

    private int N_suministro;
    private int N_demanda;
    private int[] s;
    private int[] d;
    private int[][] c;
    private int[][] estado;

    public CostoMinimoModelo(int N_suministro, int N_demanda) {
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

    public void Resolver_CM() {
        while (!todosSatisfechos()) {

            int[] posicion = encontrarCeldaMenorCosto();
            int i = posicion[0];
            int j = posicion[1];

            int asignacion = Math.min(s[i], d[j]);
            estado[i][j] = asignacion;

            s[i] -= asignacion;
            d[j] -= asignacion;

            if (s[i] == 0) {
                eliminarFila(i);
            }
            if (d[j] == 0) {
                eliminarColumna(j);
            }
        }
    }

    public int[] encontrarCeldaMenorCosto() {
        int minCosto = Integer.MAX_VALUE;
        int[] posicion = new int[2];

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (s[i] > 0 && d[j] > 0 && c[i][j] < minCosto) {
                    minCosto = c[i][j];
                    posicion[0] = i;
                    posicion[1] = j;
                }
            }
        }
        return posicion;
    }

    public boolean todosSatisfechos() {
        for (int o : s) {
            if (o > 0) {
                return false;
            }
        }
        for (int d : this.d) {
            if (d > 0) {
                return false;
            }
        }
        return true;
    }

    public void eliminarFila(int fila) {
        for (int j = 0; j < c[0].length; j++) {
            c[fila][j] = Integer.MAX_VALUE;
        }
    }

    public void eliminarColumna(int columna) {
        for (int i = 0; i < c.length; i++) {
            c[i][columna] = Integer.MAX_VALUE;
        }
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

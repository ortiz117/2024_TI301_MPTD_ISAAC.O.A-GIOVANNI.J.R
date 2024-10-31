package MODELOS;

public class EsquinaNoroesteNormal {

    private int N_suministro;
    private int N_demanda;
    private int[] s;
    private int[] d;
    private int[][] c;
    private int[][] estado;

    public EsquinaNoroesteNormal(int N_suministro, int N_demanda) {
        this.N_suministro = N_suministro;
        this.N_demanda = N_demanda;
        this.estado = new int [N_suministro][N_demanda];
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

    public int[][] getEstado() {
        return estado;
    }

    public void Resolver_ENO() {
        int i = 0, j = 0;

        while (i < N_suministro && j < N_demanda) {
           if(this.s[i] < this.d[j]){
           this.estado[i][j] = s[i];
           d[j] = (d[j] - s[i]);
           s[i] = s[i] - s[i];
           i++;
           }else{
           this.estado[i][j] = d[j];
           s[i] = (s[i] - d[j]);
           d[j] = d[j] - d[j];
           j++;
           }
        }
    }

    public void Resultado() {
        System.out.println("\nMatriz de estado:");
        for (int i = 0; i < N_suministro; i++) {
            for (int j = 0; j < N_demanda; j++) {
                System.out.print(estado[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

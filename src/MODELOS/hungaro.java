package MODELOS;

import java.util.Arrays;

public class hungaro {
    
    private final int[][] matrizCostos;
    private final int filas, columnas, dimension;
    private final int[] etiquetaPorTrabajador, etiquetaPorTarea;
    private final int[] trabajadorMinimaHolguraPorTarea;
    private final int[] valorMinimaHolguraPorTarea;
    private final int[] tareaAsignadaPorTrabajador, trabajadorAsignadoPorTarea;
    private final int[] trabajadorPadrePorTareaComprometida;
    private final boolean[] trabajadoresComprometidos;

    public hungaro(int[][] matrizCostos) {
        this.dimension = Math.max(matrizCostos.length, matrizCostos[0].length);
        this.filas = matrizCostos.length;
        this.columnas = matrizCostos[0].length;
        this.matrizCostos = new int[this.dimension][this.dimension];
        
        for (int w = 0; w < this.dimension; w++) {
            if (w < matrizCostos.length) {
                if (matrizCostos[w].length != this.columnas) throw new IllegalArgumentException("La matriz no es rectangular");
                this.matrizCostos[w] = Arrays.copyOf(matrizCostos[w], this.dimension);
            } else {
                this.matrizCostos[w] = new int[this.dimension];
            }
        }
        
        etiquetaPorTrabajador = new int[this.dimension];
        etiquetaPorTarea = new int[this.dimension];
        trabajadorMinimaHolguraPorTarea = new int[this.dimension];
        valorMinimaHolguraPorTarea = new int[this.dimension];
        trabajadoresComprometidos = new boolean[this.dimension];
        trabajadorPadrePorTareaComprometida = new int[this.dimension];
        tareaAsignadaPorTrabajador = new int[this.dimension];
        Arrays.fill(tareaAsignadaPorTrabajador, -1);
        trabajadorAsignadoPorTarea = new int[this.dimension];
        Arrays.fill(trabajadorAsignadoPorTarea, -1);
    }

    public int[] ejecutar() {
        reducir();
        calcularSolucionInicialFactible();
        asignacionCodiciosa();
        
        int w = obtenerTrabajadorNoAsignado();
        while (w < dimension) {
            inicializarFase(w);
            ejecutarFase();
            w = obtenerTrabajadorNoAsignado();
        }
        
        int[] resultado = Arrays.copyOf(trabajadorAsignadoPorTarea, filas);
        for (w = 0; w < resultado.length; w++) {
            if (resultado[w] >= columnas) {
                resultado[w] = -1;
            }
        }
        return resultado;
    }

    private void reducir() {
        for (int w = 0; w < dimension; w++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < dimension; j++) {
                if (matrizCostos[w][j] < min) {
                    min = matrizCostos[w][j];
                }
            }
            for (int j = 0; j < dimension; j++) {
                matrizCostos[w][j] -= min;
            }
        }

        int[] min = new int[dimension];
        for (int j = 0; j < dimension; j++) {
            min[j] = Integer.MAX_VALUE;
        }

        for (int w = 0; w < dimension; w++) {
            for (int j = 0; j < dimension; j++) {
                if (matrizCostos[w][j] < min[j]) {
                    min[j] = matrizCostos[w][j];
                }
            }
        }
        
        for (int w = 0; w < dimension; w++) {
            for (int j = 0; j < dimension; j++) {
                matrizCostos[w][j] -= min[j];
            }
        }
    }

    private void calcularSolucionInicialFactible() {
        for (int j = 0; j < dimension; j++) {
            etiquetaPorTarea[j] = Integer.MAX_VALUE;
        }
        
        for (int w = 0; w < dimension; w++) {
            for (int j = 0; j < dimension; j++) {
                if (matrizCostos[w][j] < etiquetaPorTarea[j]) {
                    etiquetaPorTarea[j] = matrizCostos[w][j];
                }
            }
        }
    }

    private void asignacionCodiciosa() {
        for (int w = 0; w < dimension; w++) {
            for (int j = 0; j < dimension; j++) {
                if (tareaAsignadaPorTrabajador[w] == -1 && trabajadorAsignadoPorTarea[j] == -1 && matrizCostos[w][j] - etiquetaPorTrabajador[w] - etiquetaPorTarea[j] == 0) {
                    asignar(w, j);
                }
            }
        }
    }

    private void inicializarFase(int w) {
        Arrays.fill(trabajadoresComprometidos, false);
        Arrays.fill(trabajadorPadrePorTareaComprometida, -1);
        trabajadoresComprometidos[w] = true;
        
        for (int j = 0; j < dimension; j++) {
            valorMinimaHolguraPorTarea[j] = matrizCostos[w][j] - etiquetaPorTrabajador[w] - etiquetaPorTarea[j];
            trabajadorMinimaHolguraPorTarea[j] = w;
        }
    }

    private void ejecutarFase() {
        while (true) {
            int trabajadorMinimaHolgura = -1, tareaMinimaHolgura = -1;
            int valorMinimaHolgura = Integer.MAX_VALUE;
            
            for (int j = 0; j < dimension; j++) {
                if (trabajadorPadrePorTareaComprometida[j] == -1) {
                    if (valorMinimaHolguraPorTarea[j] < valorMinimaHolgura) {
                        valorMinimaHolgura = valorMinimaHolguraPorTarea[j];
                        trabajadorMinimaHolgura = trabajadorMinimaHolguraPorTarea[j];
                        tareaMinimaHolgura = j;
                    }
                }
            }
            
            if (valorMinimaHolgura > 0) {
                actualizarEtiquetas(valorMinimaHolgura);
            }
            
            trabajadorPadrePorTareaComprometida[tareaMinimaHolgura] = trabajadorMinimaHolgura;
            if (trabajadorAsignadoPorTarea[tareaMinimaHolgura] == -1) {
                int tareaComprometida = tareaMinimaHolgura;
                int trabajadorPadre = trabajadorPadrePorTareaComprometida[tareaComprometida];
                
                while (true) {
                    int temp = tareaAsignadaPorTrabajador[trabajadorPadre];
                    asignar(trabajadorPadre, tareaComprometida);
                    tareaComprometida = temp;
                    if (tareaComprometida == -1) {
                        break;
                    }
                    trabajadorPadre = trabajadorPadrePorTareaComprometida[tareaComprometida];
                }
                return;
            } else {
                int trabajador = trabajadorAsignadoPorTarea[tareaMinimaHolgura];
                trabajadoresComprometidos[trabajador] = true;
                
                for (int j = 0; j < dimension; j++) {
                    if (trabajadorPadrePorTareaComprometida[j] == -1) {
                        int holgura = matrizCostos[trabajador][j] - etiquetaPorTrabajador[trabajador] - etiquetaPorTarea[j];
                        if (valorMinimaHolguraPorTarea[j] > holgura) {
                            valorMinimaHolguraPorTarea[j] = holgura;
                            trabajadorMinimaHolguraPorTarea[j] = trabajador;
                        }
                    }
                }
            }
        }
    }

    private void actualizarEtiquetas(int holgura) {
        for (int w = 0; w < dimension; w++) {
            if (trabajadoresComprometidos[w]) {
                etiquetaPorTrabajador[w] += holgura;
            }
        }
        for (int j = 0; j < dimension; j++) {
            if (trabajadorPadrePorTareaComprometida[j] != -1) {
                etiquetaPorTarea[j] -= holgura;
            } else {
                valorMinimaHolguraPorTarea[j] -= holgura;
            }
        }
    }

    private void asignar(int w, int j) {
        tareaAsignadaPorTrabajador[w] = j;
        trabajadorAsignadoPorTarea[j] = w;
    }

    private int obtenerTrabajadorNoAsignado() {
        int w;
        for (w = 0; w < dimension; w++) {
            if (tareaAsignadaPorTrabajador[w] == -1) {
                break;
            }
        }
        return w;
    }
}

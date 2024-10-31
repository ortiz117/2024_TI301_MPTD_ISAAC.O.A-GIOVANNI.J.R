package MODELOS;

public class hungaromain {

    public static void main(String[] args) {
        int[][] costMatrix = {
            {54, 54, 51, 53},
            {51, 57, 52, 52},
            {50, 53, 54, 56},
            {56, 54, 55, 53},};

        hungaro ha = new hungaro(costMatrix);
        int[] result = ha.ejecutar();

        System.out.println("Tareas asignadas a trabajadores:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("Trabajador " + i + " -> Tarea " + result[i]);
        }
    }
}

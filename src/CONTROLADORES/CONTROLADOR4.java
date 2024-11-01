package CONTROLADORES;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import MODELOS.hungaro;
import mpdfx.Main;

public class CONTROLADOR4 {

    @FXML
    private Button AGREGARbtn;

    @FXML
    private TextField COSTOS; 

    @FXML
    private Button LIMPIARbtn;

    @FXML
    private TextArea MOSTRARM; 

    @FXML
    private Button REGRESARbtn;

    @FXML
    private TextField nCOLUMNAS; 

    @FXML
    private TextField nFILAS; 

    @FXML
    void AGREGAR(ActionEvent event) {
        
        int filas = Integer.parseInt(nFILAS.getText());
        int columnas = Integer.parseInt(nCOLUMNAS.getText());

       
        String input = COSTOS.getText();

        
        String[] costosArray = input.split(",");
        int[][] matrizCostos = new int[filas][columnas];

       
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                
                int index = i * columnas + j;
                if (index < costosArray.length) {
                    try {
                        matrizCostos[i][j] = Integer.parseInt(costosArray[index].trim());
                    } catch (NumberFormatException e) {
                        MOSTRARM.setText("Error: Entrada no válida en la matriz de costos.");
                        return;
                    }
                } else {
                    MOSTRARM.setText("Error: No hay suficientes valores para llenar la matriz.");
                    return;
                }
            }
        }

        
        hungaro algoritmoHungaro = new hungaro(matrizCostos);
        int[] resultado = algoritmoHungaro.ejecutar();

       
        StringBuilder resultados = new StringBuilder("Asignaciones:\n");
        for (int i = 0; i < resultado.length; i++) {
            resultados.append("Trabajador ").append(i + 1).append(" asignado a tarea ").append(resultado[i] + 1).append("\n");
        }
        MOSTRARM.setText(resultados.toString());
    }

    @FXML
    void LIMPIAR(ActionEvent event) {
        
        COSTOS.clear();
        MOSTRARM.clear();
        nFILAS.clear();
        nCOLUMNAS.clear();
    }

    @FXML
    void REGRESAR(ActionEvent event) {
        try {
            Main.showView("VISTA3.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

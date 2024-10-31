package CONTROLADORES;

import MODELOS.VogelMODELO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mpdfx.Main;

public class CONTROLADOR3 {

    @FXML
    private Button AGREGARbtn;
    
    @FXML
    private Button SIGUIENTEbtn;

    @FXML
    private Button BORRARbtn;

    @FXML
    private TextField COSTOStxt;

    @FXML
    private TextField DEMANDAtxt;

    @FXML
    private Button LIMPIARbtn;

    @FXML
    private TextArea MOSTRARtxt;

    @FXML
    private TextField OFERTAtxt;

    @FXML
    private Button REGRESARbtn;

    @FXML
    private TextField nDEMANDA;

    @FXML
    private TextField nSUMINISTRO;

    @FXML
    void AGREGAR(ActionEvent event) {

        int nSuministro = Integer.parseInt(nSUMINISTRO.getText());
        int nDemanda = Integer.parseInt(nDEMANDA.getText());

        String[] OfertaString = OFERTAtxt.getText().split(",");
        int[] Oferta = new int[OfertaString.length];
        for (int i = 0; i < OfertaString.length; i++) {
            Oferta[i] = Integer.parseInt(OfertaString[i].trim());
        }

        String[] DemandaString = DEMANDAtxt.getText().split(",");
        int[] Demanda = new int[DemandaString.length];
        for (int i = 0; i < DemandaString.length; i++) {
            Demanda[i] = Integer.parseInt(DemandaString[i].trim());
        }

        String[] CostosString = COSTOStxt.getText().split(",");
        int[][] Costos = new int[nSuministro][nDemanda];
        int lugar = 0;
        for (int i = 0; i < nSuministro; i++) {
            for (int j = 0; j < nDemanda; j++) {
                Costos[i][j] = Integer.parseInt(CostosString[lugar].trim());
                lugar++;
            }
        }

        VogelMODELO modelo = new VogelMODELO(nSuministro, nDemanda);
        modelo.setS(Oferta);
        modelo.setD(Demanda);
        modelo.setC(Costos);

        modelo.resolverVogel();

        StringBuilder resultado = modelo.Resultado();
        MOSTRARtxt.setText(resultado.toString());
    }

    @FXML
    void LIMPIAR(ActionEvent event) {
        OFERTAtxt.clear();
        DEMANDAtxt.clear();
        nDEMANDA.clear();
        nSUMINISTRO.clear();
        COSTOStxt.clear();
    }

    @FXML
    void BORRAR(ActionEvent event) {
        MOSTRARtxt.clear();
    }

    @FXML
    void REGRESAR(ActionEvent event) {
        try {
            Main.showView("VISTA2.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SIGUIENTE(ActionEvent event) {
try {
            Main.showView("VISTA4.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package CONTROLADORES;

import MODELOS.CostoMinimoModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mpdfx.Main;

public class CONTROLADOR2 {

    @FXML
    private Button AGREGARbtn;

    @FXML
    private Button ATRASbtn;

    @FXML
    private Button BORRARbtn;

    @FXML
    private TextField COSTOStxt;

    @FXML
    private TextField DEMANDAtxt;

    @FXML
    private Button LIMPIARbtn;

    @FXML
    private TextArea MOSTRARM;

    @FXML
    private TextField OFERTAtxt;

    @FXML
    private Button SIGUIENTEbtn;

    @FXML
    private TextField nDEMANDA;

    @FXML
    private TextField nOFERTA;

    @FXML
    void AGREGAR(ActionEvent event) {
        int nSuministro = Integer.parseInt(nOFERTA.getText());
        int nDemanda = Integer.parseInt(nDEMANDA.getText());
        String[] Suministros = OFERTAtxt.getText().split(",");
        int[] Oferta = new int[Suministros.length];
        for (int i = 0; i < Suministros.length; i++) {
            Oferta[i] = Integer.parseInt(Suministros[i]);
        }
        String[] demanda = DEMANDAtxt.getText().split(",");
        int[] demandas = new int[demanda.length];
        for (int i = 0; i < demandas.length; i++) {
            demandas[i] = Integer.parseInt(demanda[i]);
        }
        String CostosString[] = new String[nSuministro * nDemanda];
        int Costos[][] = new int[nSuministro][nDemanda];
        CostosString = COSTOStxt.getText().split(",");
        int lugar = 0;
        for (int i = 0; i < Costos.length; i++) {
            for (int j = 0; j < Costos[i].length; j++) {
                Costos[i][j] = Integer.parseInt(CostosString[lugar]);
                lugar++;
            }
        }
        CostoMinimoModelo p1  = new CostoMinimoModelo(nSuministro, nDemanda);
        p1.setC(Costos);
        p1.setD(demandas);
        p1.setS(Oferta);
        p1.Resolver_CM();
        MOSTRARM.setText(p1.Resultado().toString());
        
    }

    @FXML
    void ATRAS(ActionEvent event) {
        try {
            Main.showView("VISTA1.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BORRAR(ActionEvent event) {
        MOSTRARM.clear();
    }

    @FXML
    void LIMPIAR(ActionEvent event) {
        COSTOStxt.clear();
        OFERTAtxt.clear();
        DEMANDAtxt.clear();
        nDEMANDA.clear();
        nOFERTA.clear();
    }

    @FXML
    void SIGUIENTE(ActionEvent event) {
        try {
            Main.showView("VISTA3.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package CONTROLADORES;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import MODELOS.EsquinaNoroesteModelo;

import java.util.Arrays;
import mpdfx.Main;

public class CONTROLADOR1 {

    @FXML
    private Button AGREGARbtn;

    @FXML
    private Button SIGUIENTEbtn;

    @FXML
    private Button LIMPIARBTN;

    @FXML
    private Button LIMPIARbtn;

    @FXML
    private TextArea MOSTRARM;

    @FXML
    private TextField txtCOSTO;

    @FXML
    private TextField txtDEMANDA;

    @FXML
    private TextField txtDEMANDAn;

    @FXML
    private TextField txtOFERTA;

    @FXML
    private TextField txtSUMINISTRO;

    @FXML
    void AGREGAR(ActionEvent event) {
        int nSuministro = Integer.parseInt(txtSUMINISTRO.getText());
        int ndemanda = Integer.parseInt(txtDEMANDAn.getText());

        String OfertaString[] = txtOFERTA.getText().split(",");
        int Oferta[] = new int[OfertaString.length];
        for (int i = 0; i < OfertaString.length; i++) {
            Oferta[i] = Integer.parseInt(OfertaString[i].trim());
        }

        String DemandaString[] = txtDEMANDA.getText().split(",");
        int Demanda[] = new int[DemandaString.length];
        for (int i = 0; i < DemandaString.length; i++) {
            Demanda[i] = Integer.parseInt(DemandaString[i].trim());
        }

        String CostosString[] = new String[nSuministro * ndemanda];
        int Costos[][] = new int[nSuministro][ndemanda];
        CostosString = txtCOSTO.getText().split(",");
        int lugar = 0;
        for (int i = 0; i < Costos.length; i++) {
            for (int j = 0; j < Costos[i].length; j++) {
                Costos[i][j] = Integer.parseInt(CostosString[lugar]);
                lugar++;
            }
        }
        EsquinaNoroesteModelo p1 = new EsquinaNoroesteModelo(nSuministro, ndemanda);
        p1.setS(Oferta);
        p1.setD(Demanda);
        p1.setC(Costos);
        p1.Resolver_ENO();

        MOSTRARM.setText(p1.Resultado().toString());
    }

    @FXML
    void LIMIPIARtxt(ActionEvent event) {
        txtSUMINISTRO.clear();
        txtDEMANDA.clear();
        txtOFERTA.clear();
        txtCOSTO.clear();
    }

    @FXML
    void LIMPIARAREA(ActionEvent event) {
        MOSTRARM.clear();
    }

    @FXML
    void SIGUIENTE(ActionEvent event) {
        try {
            Main.showView("VISTA2.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dad.Main.controllers;

import dad.Main.apis.TextoApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

    private ChoiceController choiceController;
    private TextoApi textoApi;

    @FXML
    private Button introButton;

    @FXML
    private VBox introRoot;

    @FXML
    private TextArea introText;

    private RootController rootController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onStartAction(ActionEvent event) {
        try {
            String texto = introText.getText().trim();
            if (texto.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Introducción vacía");
                alert.setContentText("Por favor introduzca la introducción");
                alert.showAndWait();
                return;
            }

            // Cargar choiceview.fxml correctamente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choiceview.fxml"));
            Parent newView = loader.load();
            ChoiceController choiceController = loader.getController(); // Obtener el controlador del FXML

            if (choiceController == null) {
                System.err.println("Error: choiceController es NULL después de cargar FXML.");
                return;
            }

            // Configurar la vista en RootController
            if (rootController != null) {
                rootController.setView(newView);
                choiceController.setRootController(rootController); // Pasar el RootController a ChoiceController

                // Crear TextoApi con ambos controladores y asignarlo al RootController
                TextoApi textoApi = new TextoApi(rootController, choiceController);
                rootController.setTextoApi(textoApi);

                // Llamar a textoApi con el texto ingresado
                textoApi.textoApi(texto);
            } else {
                System.err.println("Error: RootController es NULL.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Button getIntroButton() {
        return introButton;
    }

    public void setIntroButton(Button introButton) {
        this.introButton = introButton;
    }

    public VBox getIntroRoot() {
        return introRoot;
    }

    public void setIntroRoot(VBox introRoot) {
        this.introRoot = introRoot;
    }

    public TextArea getIntroText() {
        return introText;
    }

    public void setIntroText(TextArea introText) {
        this.introText = introText;
    }

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}

package dad.Main.controllers;

import dad.*;
import dad.Main.controllers.*;
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
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choiceview.fxml"));
                ChoiceController choiceController = new ChoiceController();
                loader.setController(choiceController);
                Parent newView = loader.load();
                if (rootController != null) {
                    rootController.setView(newView);
                    choiceController.setRootController(rootController);
                    String textoCapturado = introText.getText().trim();
                    TextoApi textoApi = new TextoApi();
                    TextoAPIResponse textoAPIResponse = new TextoAPIResponse(textoApi);
                    textoAPIResponse.generarHistoria(textoCapturado);

                } else {
                    System.err.println("RootController no está configurado.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

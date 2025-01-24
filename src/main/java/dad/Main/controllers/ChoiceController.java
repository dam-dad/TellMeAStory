package dad.Main.controllers;

import dad.Main.TellMeAStoryApp;
import dad.Main.apis.TextoApi;
import io.github.fvarrui.jeppetto.Chat;
import io.github.fvarrui.jeppetto.Jeppetto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    @FXML
    private TextArea IAOptionText;

    @FXML
    private VBox Vroot;

    @FXML
    private Button endButton;

    @FXML
    private Button newStoryButton;

    @FXML
    private Button option1Button;

    @FXML
    private Button option2Button;

    private dad.Main.controllers.RootController rootController;

    private Jeppetto jeppetto;

    private TextoApi textoApi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onEndAction(ActionEvent event) {

    }

    @FXML
    void onNewStoryAction(ActionEvent event) {
        try {
            // Obtener la ventana actual
            javafx.stage.Stage currentStage = (javafx.stage.Stage) rootController.getRoot().getScene().getWindow();
            // Cerrar la ventana actual
            currentStage.close();
            // Reiniciar la aplicación ejecutando el método `start` de la clase principal
            TellMeAStoryApp app = new TellMeAStoryApp();
            javafx.application.Platform.runLater(() -> {
                try {
                    app.start(new javafx.stage.Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onOptionOneAction(ActionEvent event) throws Exception {

        String option = "Opcion 1";
        rootController.getTextoApi().choices(option);

    }

    @FXML
    void onOptionTwoAction(ActionEvent event) throws Exception {

        String option = "Opcion 2";
        rootController.getTextoApi().choices(option);

    }


    public void mostrarHistoria(){

    }


    public Button getEndButton() {
        return endButton;
    }

    public void setEndButton(Button endButton) {
        this.endButton = endButton;
    }

    public TextArea getIAOptionText() {
        return IAOptionText;
    }

    public void setIAOptionText(TextArea IAOptionText) {
        this.IAOptionText = IAOptionText;
    }

    public Button getNewStoryButton() {
        return newStoryButton;
    }

    public void setNewStoryButton(Button newStoryButton) {
        this.newStoryButton = newStoryButton;
    }

    public Button getOption1Button() {
        return option1Button;
    }

    public void setOption1Button(Button option1Button) {
        this.option1Button = option1Button;
    }

    public Button getOption2Button() {
        return option2Button;
    }

    public void setOption2Button(Button option2Button) {
        this.option2Button = option2Button;
    }

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public VBox getVroot() {
        return Vroot;
    }

    public void setVroot(VBox vroot) {
        Vroot = vroot;
    }
}

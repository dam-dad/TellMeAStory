package dad.Main.controllers;

import dad.Main.apis.TextoApi;
import io.github.fvarrui.jeppetto.Chat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    private final StringProperty textoIA = new SimpleStringProperty("");

    @FXML
    private TextArea IATextArea;

    @FXML
    private AnchorPane placeholder;

    @FXML
    private SplitPane root;

    private TextoApi textoApi;
    private ChoiceController choiceController;

    public RootController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            textoApi = new TextoApi(this, choiceController);

            IATextArea.textProperty().bind(textoIA);
            IATextArea.setWrapText(true); // Habilitar ajuste de línea automático

            // Cargar el archivo FXML y su controlador
            FXMLLoader introLoader = new FXMLLoader(getClass().getResource("/fxml/introroot.fxml"));
            IntroController introController = new IntroController();
            introLoader.setController(introController);
            Parent introContent = introLoader.load();
            introController.setRootController(this);

            // Cargar el contenido en el placeholder
            placeholder.getChildren().setAll(introContent);

            // Configurar que el contenido se ajuste al tamaño del placeholder
            AnchorPane.setTopAnchor(introContent, 0.0);
            AnchorPane.setRightAnchor(introContent, 0.0);
            AnchorPane.setBottomAnchor(introContent, 0.0);
            AnchorPane.setLeftAnchor(introContent, 0.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StringProperty textoIAProperty() {
        return textoIA;
    }

    public void setView(Parent view) {
        placeholder.getChildren().setAll(view);

        // Configurar que el contenido se ajuste al tamaño del placeholder
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
    }


    public TextArea getIATextArea() {
        return IATextArea;
    }

    public void setIATextArea(TextArea IATextArea) {
        this.IATextArea = IATextArea;
    }

    public AnchorPane getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(AnchorPane placeholder) {
        this.placeholder = placeholder;
    }

    public SplitPane getRoot() {
        return root;
    }

    public void setRoot(SplitPane root) {
        this.root = root;
    }

    public TextoApi getTextoApi() {
        return textoApi;
    }

    public void setTextoApi(TextoApi textoApi) {
        this.textoApi = textoApi;
    }
}

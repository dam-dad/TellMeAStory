package dad.Main.apis;

import dad.Main.controllers.ChoiceController;
import dad.Main.controllers.RootController;
import io.github.fvarrui.jeppetto.Chat;
import org.json.JSONObject;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextoApi {

    private final RootController rootController;
    private final ChoiceController choiceController;

    private static final String API_KEY = ResourceBundle.getBundle("config").getString("openai.api.key");

    int interaciones = 4;
    String completeStory = "";

    String model = "gpt-4o-mini";
    String developerMessage = """
        Eres un asistente que crea historias
        y genera dos opciones de continuación en formato JSON.
        En base a la introduccion determinaras el genero de entre estas opciones: fantasia, ciencia ficcion, terror o normal.
        El genero normal lo elegiras cuando la introduccion no se tan especifica como para elegir los otros generos.
        La historia se etiqueta como story, las opciones como option1 y option2 y el genero como genero.
        Con la siguente introduccion:
        """;
        Chat chat = new Chat(API_KEY, model, developerMessage);

    public TextoApi(RootController rootController, ChoiceController choiceController) {
        this.rootController = rootController;
        this.choiceController = choiceController;
    }


    public void textoApi(String introduccion) throws Exception {

        JSONObject jsonObject = new JSONObject(chat.send(introduccion).getContent());

        String story = jsonObject.getString("story");

        String option1 = jsonObject.getString("option1");
        String option2 = jsonObject.getString("option2");
        String option = option1 + System.lineSeparator() + System.lineSeparator() + option2;

        String genero = jsonObject.getString("genero");

        if (interaciones >= 1) {
            rootController.getRoot().getStylesheets().clear();
            switch (genero.toLowerCase()){
                case "fantasia":
                    rootController.getRoot().getStylesheets().add("css/fantacia.css");
                    break;
                case "ciencia ficcion":
                    rootController.getRoot().getStylesheets().add("css/ciencia-ficcion.css");
                    break;
                case "terror":
                    rootController.getRoot().getStylesheets().add("css/terror.css");
                    break;
            }

            completeStory = completeStory + System.lineSeparator() + story;

            rootController.textoIAProperty().set(completeStory);
            choiceController.optionIAProperty().set(option);

            --interaciones;

        } else {

            JSONObject jsonFin = new JSONObject(chat.send(introduccion + " y dale un final a la historia").getContent());

            String endStory = jsonFin.getString("story");
            completeStory = completeStory + System.lineSeparator() + endStory;

            rootController.textoIAProperty().set(completeStory);
            choiceController.optionIAProperty().set("La Historia a terminado.");

            choiceController.getOption1Button().setDisable(true);
            choiceController.getOption2Button().setDisable(true);
            choiceController.getEndButton().setDisable(true);

        }

    }

    public void endStory(String end) throws Exception {
        JSONObject jsonObject = new JSONObject(chat.send(end).getContent());

        String story = jsonObject.getString("story");

        completeStory = completeStory + System.lineSeparator() + story;

        rootController.textoIAProperty().set(completeStory);
        choiceController.optionIAProperty().set("La Historia a terminado.");

        choiceController.getOption1Button().setDisable(true);
        choiceController.getOption2Button().setDisable(true);
        choiceController.getEndButton().setDisable(true);
    }

}

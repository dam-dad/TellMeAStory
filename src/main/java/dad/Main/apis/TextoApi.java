package dad.Main.apis;

import dad.Main.controllers.ChoiceController;
import dad.Main.controllers.RootController;
import io.github.fvarrui.jeppetto.Chat;
import org.json.JSONObject;

import java.util.ResourceBundle;

public class TextoApi {

    private final RootController rootController;
    private final ChoiceController choiceController;

    private static final String API_KEY = ResourceBundle.getBundle("config").getString("openai.api.key");

    String model = "gpt-4o-mini";
    String developerMessage = """
        Eres un asistente que crea historias
        y genera dos opciones de continuación en formato JSON.
        La historia se etiqueta como story y las opciones como option1 y option2.
        Cada opcion comenzara de esta forma: Opciones:.
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
        String completeStory = "";
        completeStory = completeStory + story;

        String option1 = jsonObject.getString("option1");
        String option2 = jsonObject.getString("option2");
        String option = option1 + System.lineSeparator() + System.lineSeparator() + option2;

        if (rootController != null) {
            rootController.textoIAProperty().set(completeStory);
        } else {
            System.out.println("Error: RootController es NULL en TextoApi.");
        }

        if (choiceController != null) {
            choiceController.optionIAProperty().set(option);
        } else {
            System.out.println("Error: ChoiceController es NULL en TextoApi.");
        }

    }

}

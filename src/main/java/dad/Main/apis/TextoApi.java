package dad.Main.apis;

import dad.Main.controllers.RootController;
import io.github.fvarrui.jeppetto.Chat;
import io.github.fvarrui.jeppetto.Jeppetto;

import java.util.ResourceBundle;

public class TextoApi {

    private RootController rootController;
    private Jeppetto jeppetto;

    private static final String API_KEY = ResourceBundle.getBundle("config").getString("openai.api.key");

    String model = "gpt-4o-mini";
    String developerMessage = """
        Eres un asistente que crea historias
        y genera dos opciones de continuación en formato JSON.
        La historia se etiqueta como story y las opciones como option1 y option2.
        Con la siguente introduccion:
        """;
        Chat chat = new Chat(API_KEY, model, developerMessage);

    public void textoApi(String introduccion) throws Exception {

        rootController.getIATextArea().textProperty().set(chat.send(introduccion).getContent());

        //System.out.println(chat.send(introduccion).getContent());

    }

    public void choices(String option) throws Exception {

        System.out.println(chat.send(option).getContent());

    }

}

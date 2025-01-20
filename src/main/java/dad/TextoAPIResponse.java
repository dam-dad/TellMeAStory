package dad;

import org.json.JSONArray;
import org.json.JSONObject;

public class TextoAPIResponse {
    private final TextoApi textoApi;

    public TextoAPIResponse(TextoApi textoApi) {
        this.textoApi = textoApi;
    }

    public void generarHistoria(String historia) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "microsoft/WizardLM-2-8x22B");
            requestBody.put("messages", new JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "Eres un asistente que puede completar historias y generar dos opciones de continuación para que el usuario decida el rumbo de la historia, con un maximo de 256 caracteres. El contenido lo daras en formato JSON, sin saltos de linea"))
                    .put(new JSONObject().put("role", "user").put("content", historia))
            );
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 256);
            requestBody.put("n", 2);

            String response = textoApi.sendPostRequest(requestBody);
            System.out.println("Respuesta completa de la API:");
            System.out.println(response);

            JSONObject jsonResponse = new JSONObject(response);
            processApiResponse(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processApiResponse(JSONObject jsonResponse) {
        if (jsonResponse.has("choices")) {
            JSONArray choices = jsonResponse.getJSONArray("choices");

            for (int i = 0; i < choices.length(); i++) {
                JSONObject choice = choices.getJSONObject(i);
                String content = new JSONObject(choice.getJSONObject("message").getString("content"))
                        .getString("historia");

                String truncatedContent = limitTo256Words(content);

                System.out.println();
                System.out.println("Opción " + (i + 1) + ":");
                System.out.println(truncatedContent);
                System.out.println("--------------------------------------------------");
            }
        } else {
            System.out.println("La respuesta no contiene el campo 'choices' esperado.");
            System.out.println("Respuesta completa: " + jsonResponse.toString());
        }
    }

    private String limitTo256Words(String text) {
        String[] words = text.split("\\s+");
        if (words.length > 256) {
            StringBuilder truncatedText = new StringBuilder();
            for (int i = 0; i < 256; i++) {
                truncatedText.append(words[i]).append(" ");
            }
            return truncatedText.toString().trim();
        } else {
            return text;
        }
    }
}


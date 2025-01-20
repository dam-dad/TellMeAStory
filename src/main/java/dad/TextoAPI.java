package dad;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class TextoAPI {
    public void GenerarHistoria(String historia) {
        try {
            String apiUrl = "https://api.aimlapi.com/v1/chat/completions";
            String apiKey = "";
            String userInput = "Había una vez un astronauta que era muy alto";

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "microsoft/WizardLM-2-8x22B");
            requestBody.put("messages", new JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "Eres un asistente que puede completar historias y generar dos opciones de continuación para que el usuario decida el rumbo de la historia, con un maximo de 256 caracteres. El contenido lo daras en formato JSON, sin saltos de linea"))
                    .put(new JSONObject().put("role", "user").put("content", userInput))
            );
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 256);
            requestBody.put("n", 2);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Respuesta completa de la API: ");
                System.out.println(response.toString());

                JSONObject jsonResponse = new JSONObject(response.toString());
                //processApiResponse(jsonResponse);

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuilder errorResponse = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    errorResponse.append(inputLine);
                }
                in.close();
                System.out.println("Error en la solicitud: " + responseCode);
                System.out.println("Detalles del error: " + errorResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processApiResponse(JSONObject jsonResponse) {
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

    private static String limitTo256Words(String text) {
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

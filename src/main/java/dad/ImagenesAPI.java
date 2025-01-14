package dad;

import java.io.*;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;

public class ImagenesAPI {
    private static final String API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-2";
    private static final String API_TOKEN = "";

    public static void main(String[] args) {
        try {
            URL url = new URL(API_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String inputText = "{\"inputs\": \"Una pintura de un paisaje surrealista\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = inputText.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.err.println("Error en la solicitud. Código de respuesta: " + responseCode);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorResponse.append(errorLine.trim());
                    }
                    System.err.println("Detalles del error: " + errorResponse.toString());
                }
            } else {
                try (InputStream inputStream = connection.getInputStream()) {
                    FileOutputStream fileOutputStream = new FileOutputStream("imagen_generada.jpg");

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    fileOutputStream.close();
                    System.out.println("Imagen generada guardada como 'imagen_generada.jpg'");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.example;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
    }

    public static String incrementAndCheckEndpoint(String endpoint, int increment, boolean isPageExists) {

        if (endpoint.matches(".*/page/\\d+.*")) {
            //Достаём текущий номер страницы
            int currentPage = Integer.parseInt(endpoint.split("/page/")[1].split("/")[0]);
            int newPage = currentPage + increment;

            // Заменяем текущий номер страницы новым
            endpoint = endpoint.replaceFirst("/page/\\d+", "/page/" + newPage);
        }

        //Проверяем существование веб-страницы
        try {
            URL url = new URL(endpoint);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode = huc.getResponseCode();
            assertEquals(isPageExists ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_NOT_FOUND, responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return endpoint;
    }
}
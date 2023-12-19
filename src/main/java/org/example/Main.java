package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
    }

    public static String incrementAndCheckEndpoint(String endpoint, int increment) throws Exception {

        if (endpoint.matches("https://lubart-miniatures.com/shop/page/\\d+/?(\\?.+)?")) {
            //Достаём текущий номер страницы
            int currentPage = Integer.parseInt(endpoint.split("/page/")[1].split("/")[0].split("\\?")[0]);
            int newPage = currentPage + increment;

            // Заменяем текущий номер страницы новым
            endpoint = endpoint.replaceFirst("/page/\\d+", "/page/" + newPage);
        } else throw new Exception("Ссылка неверного формата. " +
                "Принимаются только ссылки формата 'https://lubart-miniatures.com/shop/page/7' " +
                "Допускается наличие query параметров");

        //Проверяем существование веб-страницы
        checkUrlExistence(endpoint);

        return endpoint;
    }

    private static void checkUrlExistence(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
        if (HttpURLConnection.HTTP_NOT_FOUND == responseCode)
            System.out.println("Данной страницы не существует - " + endpoint);
        else assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
}
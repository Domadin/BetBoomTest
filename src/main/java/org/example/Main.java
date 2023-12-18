package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public static String incrementEndpoint(String endpoint, int increment) {

        if (endpoint.matches(".*/page/\\d+.*")) {
            //Достаём текущий номер страницы
            int currentPage = Integer.parseInt(endpoint.split("/page/")[1].split("/")[0]);
            int newPage = currentPage + increment;

            // Заменяем текущий номер страницы новым
            return endpoint.replaceFirst("/page/\\d+", "/page/" + newPage);
        }

        return endpoint;
    }
}
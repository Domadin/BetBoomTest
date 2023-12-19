import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.Main.incrementAndCheckEndpoint;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncrementTests {

    @ParameterizedTest
    @MethodSource
    //Тут я бы разделил тест на позитивные и негативные сценарии, удалили бы параметр isValidUrl,
    //но в требованиях указано, что работу функции должен тестировать один метод
    void checkIncrement(String input, boolean isValidUrl, int increment, String expected) throws Exception {
        if (isValidUrl) assertEquals(expected, incrementAndCheckEndpoint(input, increment));
        else assertThrows(Exception.class, () -> incrementAndCheckEndpoint(input, increment));
    }

    static Stream<Arguments> checkIncrement() {
        return Stream.of(

                // --- Позитивные тесты ---

                //Страница существует, без query параметров, слеш на конце
                Arguments.of("https://lubart-miniatures.com/shop/page/7/",
                        true,
                        3,
                        "https://lubart-miniatures.com/shop/page/10/"),

                //Страница существует, без query параметров, нет слеша на конце
                Arguments.of("https://lubart-miniatures.com/shop/page/7",
                        true,
                        3,
                        "https://lubart-miniatures.com/shop/page/10"),

                //Страница существует, query параметры, слеш перед параметрами
                Arguments.of("https://lubart-miniatures.com/shop/page/7/?orderby=price",
                        true,
                        3,
                        "https://lubart-miniatures.com/shop/page/10/?orderby=price"),

                //Страница существует, query параметры, нет слеша перед параметрами
                Arguments.of("https://lubart-miniatures.com/shop/page/7?orderby=price",
                        true,
                        3,
                        "https://lubart-miniatures.com/shop/page/10?orderby=price"),

                //Страницы не существует
                Arguments.of("https://lubart-miniatures.com/shop/page/7/",
                        true,
                        333,
                        "https://lubart-miniatures.com/shop/page/340/"),


                // --- Негативные тесты ---

                //Неверный домен
                Arguments.of("https://example.com/shop/page/7/",
                        false,
                        1,
                        "https://example.com/shop/page/8/"),

                //Неверный URI
                Arguments.of("https://example.com/test/page/7/",
                        false,
                        1,
                        "https://example.com/test/page/8/"),

                //На конце не query параметры
                Arguments.of("https://example.com/test/page/7/test",
                        false,
                        1,
                        "https://example.com/test/page/8/test"),

                //Не указан протокол
                Arguments.of("lubart-miniatures.com/shop/page/7",
                        false,
                        3,
                        "lubart-miniatures.com/shop/page/10"),

                //Номер страницы указан неверно
                Arguments.of("https://example.com/test/page/seven",
                        false,
                        3,
                        "https://example.com/test/page/ten"),

                //Нет номера страницы + query параметры (страница 1)
                Arguments.of("https://lubart-miniatures.com/shop/?orderby=price",
                        false,
                        3,
                        "https://lubart-miniatures.com/shop/?orderby=price"),

                //Нет номера страницы без query параметров
                Arguments.of("https://lubart-miniatures.com/shop/page/",
                        false,
                        1,
                        "https://lubart-miniatures.com/shop/page/")

                );

    }
}
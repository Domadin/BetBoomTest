import org.example.Main;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncrementTests {

    @ParameterizedTest
    @MethodSource
    void checkIncrement(String input, int increment, boolean isPageExists, String expected) {
        assertEquals(expected, Main.incrementAndCheckEndpoint(input, increment, isPageExists));
    }

    static Stream<Arguments> checkIncrement() {
        return Stream.of(
                Arguments.of("https://lubart-miniatures.com/shop/page/7/", 333, false, "https://lubart-miniatures.com/shop/page/340/"),

                Arguments.of("https://lubart-miniatures.com/shop/page/7/", 3, true, "https://lubart-miniatures.com/shop/page/10/"),
                Arguments.of("https://lubart-miniatures.com/shop/page/7", 3, true, "https://lubart-miniatures.com/shop/page/10"),
                Arguments.of("https://lubart-miniatures.com/shop/page/7/?orderby=price", 3, true, "https://lubart-miniatures.com/shop/page/10/?orderby=price"));
    }
}
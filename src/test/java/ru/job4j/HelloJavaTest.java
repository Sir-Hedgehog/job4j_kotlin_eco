package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 03.09.2021
 */
public class HelloJavaTest {

    @Test
    void checkHelloJava() {
        final var helloJava = new HelloJava();
        var expected = "Hello, Java!";
        assertEquals(expected, helloJava.getHello());
    }
}

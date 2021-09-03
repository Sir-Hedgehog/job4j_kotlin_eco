package ru.job4j

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 03.09.2021
 */
class HelloKotlinTest {

    @Test
    fun checkHelloKotlin() {
        val helloKotlin = HelloKotlin()
        val expected = "Hello, Kotlin!"
        Assertions.assertEquals(expected, helloKotlin.getHello())
    }
}
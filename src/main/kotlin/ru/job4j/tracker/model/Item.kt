package ru.job4j.tracker.model

import javax.persistence.*

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 19.01.2022
 */
@Entity
@Table(name = "Items")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var name: String = "",
    var description: String = "")
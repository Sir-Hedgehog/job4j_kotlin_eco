package ru.job4j.tracker.start

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import ru.job4j.tracker.model.Item
import ru.job4j.tracker.security.BasicAuthFilter
import ru.job4j.tracker.service.ItemService
import spark.Spark.*

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 19.01.2022
 */
fun main() {

    val itemService = ItemService()

    val registry = StandardServiceRegistryBuilder().configure().build()
    val sf = MetadataSources(registry).buildMetadata().buildSessionFactory()

    exception(Exception::class.java) { e, req, res ->
        e.printStackTrace()
        StandardServiceRegistryBuilder.destroy(registry)
    }

    port(8181)

    /**
     * Аутентификация пользователей
     */
    before(BasicAuthFilter(mapOf("admin" to "admin", "user" to "user")))

    /**
     * Эндпоинты для управления списком заявок
     */
    path("/items") {

        get("/:id") { request, response ->
            itemService.findById(request.params("id").toInt(), sf)
        }

        get("") { request, response ->
            jacksonObjectMapper().writeValueAsString(itemService.findAll(sf))
        }

        post("/create") { request, response ->
            val item: Item = Gson().fromJson(request.body(), Item::class.java)
            itemService.create(item, sf)

            response.status(201)
            "Заявка $item успешно создана"
        }

        put("/update/:id") { request, response ->
            val item: Item = Gson().fromJson(request.body(), Item::class.java)
            itemService.update(item, sf)

            response.status(200)
            "Заявка $item успешно обновлена"
        }

        delete("/delete/:id") { request, response ->
            val id = request.params("id")
            itemService.delete(id.toInt(), sf)

            response.status(200)
            "Заявка с идентификатором $id успешно удалена"
        }
    }
}

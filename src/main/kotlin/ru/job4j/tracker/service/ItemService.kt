package ru.job4j.tracker.service

import org.hibernate.Session
import org.hibernate.SessionFactory
import ru.job4j.tracker.model.Item

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 19.01.2022
 */
class ItemService {

    /**
     * Шаблонный метод для создания, обновления и удаления заявки
     */
    fun <T> SessionFactory.tx(block: Session.() -> T): T {
        val session = openSession()
        session.beginTransaction()
        val model = session.block()
        session.transaction.commit()
        session.close()
        return model
    }

    /**
     * Метод создает заявку
     * @param item - заявка
     * @param sf - сессия
     * @return - созданная заявка
     */
    fun create(item: Item, sf: SessionFactory): Item =
        sf.tx { save(item); item }

    /**
     * Метод обновляет заявку
     * @param item - заявка
     * @param sf - сессия
     */
    fun update(item: Item?, sf: SessionFactory) {
        sf.tx { update(item) }
    }

    /**
     * Метод удаляет заявку
     * @param id - идентификатор заявки
     * @param sf - сессия
     */
    fun delete(id: Int, sf: SessionFactory) {
        sf.tx {
            delete(findById(id, sf))
        }
    }

    /**
     * Шаблонный метод для получения заявки или всех заявок
     */
    infix fun <T> SessionFactory.select(query: String): List<T> {
        val session = openSession()
        session.beginTransaction()
        val model = session.createQuery(query).list() as List<T>
        session.transaction.commit()
        session.close()
        return model
    }

    /**
     * Метод получает все заявки
     * @param sf - сессия
     * @return - список всех заявок
     */
    fun findAll(sf: SessionFactory): List<Item> = sf select "from ru.job4j.tracker.model.Item"

    /**
     * Метод получает заявку по идентификатору
     * @param id - идентификатор заявки
     * @param sf - сессия
     * @return - искомая заявка
     */
    fun findById(id: Int?, sf: SessionFactory): Item =
        sf.tx { get(Item::class.java, id) }
}
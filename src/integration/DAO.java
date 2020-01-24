package integration;

import java.util.stream.Stream;

public interface DAO<T> {

    T findByNameOrID(String input);

    T findByName(String name);

    T findByID(int id);

    Stream<T> findAll();

    void create(T t);

    void update(T t);

    void delete(T t);

}

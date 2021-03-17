package by.it.academy.cv.data;


import java.util.List;

public interface Dao<T, K> {

    void save(T t);

    T read(K id);

    List<T> readUsingQuery(String s);
}

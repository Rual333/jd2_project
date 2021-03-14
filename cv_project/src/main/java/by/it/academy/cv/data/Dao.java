package by.it.academy.cv.data;


public interface Dao<T, K> {

    void save(T t);

    T read(K id);
}

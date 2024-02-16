package bg.sofia.uni.fmi.mjt.server.database;

import java.io.Serializable;
import java.util.Collection;

public interface DatabaseOperations<K, V extends Serializable> {
    /**
     * Save elements into specific file
     *
     * @param fileName - name of file
     */
    void saveTo(String fileName);

    /**
     * Load elements from specific file
     *
     * @param fileName - name of file
     */
    void loadFrom(String fileName);

    void add(K key, V value);
    void addAll(K key, Collection<V> values);
    V get(K key);
    Collection<V> getAll(K key);
    boolean contains(K key);
}

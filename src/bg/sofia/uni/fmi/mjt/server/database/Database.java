package bg.sofia.uni.fmi.mjt.server.database;

import bg.sofia.uni.fmi.mjt.exceptions.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Type;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static bg.sofia.uni.fmi.mjt.exceptions.ExceptionMessages.KEY_DOES_NOT_CONTAINS_IN_DATABASE;

public class Database<K, V extends Serializable> implements DatabaseOperations<K, V> {
    private Gson gson;
    private Map<K, List<V>> elements;

    public Database() {
        this.elements = new HashMap<>();
        this.gson = new Gson();
    }

    @Override
    public void saveTo(String fileName) {
        String information = gson.toJson(elements);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(information);
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e); // to-do create custom exception
        }
    }

    @Override
    public void loadFrom(String fileName) {
        try (var reader = new FileReader(fileName)) {

            Type type = new TypeToken<Map<K, List<V>>>() {
            }.getType();
            Map<K, List<V>> result = gson.fromJson(reader, type);

            if (elements == null || elements.isEmpty()) {
                elements = result;
                return;
            }

            elements.putAll(result);

        } catch (IOException e) {
            throw new RuntimeException(e); // to-do create custom exception
        }
    }

    @Override
    public void add(K key, V value) {
        if (key == null) {
            return;
        }

        if (!elements.containsKey(key)) {
            elements.put(key, new ArrayList<>());
        }

        elements.get(key).add(value);
    }

    @Override
    public void addAll(K key, Collection<V> values) {
        if (key == null || values == null || values.isEmpty()) {
            return;
        }

        if (!elements.containsKey(key)) {
            elements.put(key, new ArrayList<>());
        }

        elements.get(key).addAll(values);
    }

    @Override
    public V get(K key) {
        if (!elements.containsKey(key)) {
            throw new NoSuchElementException(KEY_DOES_NOT_CONTAINS_IN_DATABASE);
        }

        return elements.get(key).getFirst();
    }

    @Override
    public Collection<V> getAll(K key) {
        if (!elements.containsKey(key)) {
            throw new NoSuchElementException(KEY_DOES_NOT_CONTAINS_IN_DATABASE);
        }

        return elements.get(key);
    }

    @Override
    public boolean contains(K key) {
        return elements.containsKey(key);
    }
}

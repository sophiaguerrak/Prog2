package uy.edu.um.adt.hash;

import uy.edu.um.exceptions.InformacionInvalida;

import java.util.ArrayList;
import java.util.List;

public class HashImpl<K, V> implements MyHash<K, V> {
    private class Dato<K, V> {
        K key;
        V value;

        Dato(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private ArrayList<Dato<K, V>>[] hashTable;
    private int capacidad;
    private static final double LOAD_FACTOR = 0.75;

    public HashImpl(int capacity) {
        this.capacidad = capacity;
        hashTable = (ArrayList<Dato<K, V>>[]) new ArrayList[capacity];
        for (int i = 0; i<capacity; i++) {
            hashTable[i] = new ArrayList<>();
        }
    }

    private int getHashIndex(K key) {  // lo uso para insertar los datos de forma distribuida en la hash table
        return Math.abs(key.hashCode()) % capacidad;
    }

    @Override
    public void insert(K key, V value) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        if (size() + 1 >= capacidad * LOAD_FACTOR) {
            resize();
        }
        int index = getHashIndex(key);
        hashTable[index].add(new Dato<>(key, value));
    }


    @Override
    public V search(K key) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        int index = getHashIndex(key);
        for (Dato<K, V> dato : hashTable[index]) {
            if (dato.key.equals(key)) {
                return dato.value;
            }
        }
        return null;
    }


    @Override
    public void delete(K key) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        int index = getHashIndex(key);
        List<Dato<K, V>> bucket = hashTable[index];
        for (int i = 0; i < bucket.size(); i++) {
            Dato<K, V> dato = bucket.get(i);
            if (dato.key.equals(key)) {
                bucket.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean contains(K key) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        return search(key) != null;
    }

    @Override
    public int size() {
        int size = 0;
        for (ArrayList<Dato<K, V>> bucket : hashTable) {
            size += bucket.size();
        }
        return size;
    }

    private void resize() throws InformacionInvalida {
        int nuevaCapacidad = capacidad * 2;
        ArrayList<Dato<K, V>>[] newTable = (ArrayList<Dato<K, V>>[]) new ArrayList[nuevaCapacidad];
        for (int i = 0; i < nuevaCapacidad; i++) {
            newTable[i] = new ArrayList<>();
        }
        ArrayList<Dato<K, V>>[] oldTable = hashTable;
        this.capacidad = nuevaCapacidad;
        this.hashTable = newTable;
        for (ArrayList<Dato<K, V>> bucket : oldTable) {
            for (Dato<K, V> dato : bucket) {
                if (dato != null) {
                    insert(dato.key, dato.value);
                }
            }
        }
    }
}


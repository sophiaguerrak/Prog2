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

    private Dato<K, V>[] hashTable;
    private int capacidad;
    private static final double LOAD_FACTOR = 0.75;

    public HashImpl(int capacity) {
        this.capacidad = capacity;
        this.hashTable = new Dato[capacity];
    }

    private int getHashIndex(K key) {  // lo uso para insertar los datos de forma distribuida en la hash table
        int hash = Math.abs(key.hashCode());
        int index = hash % capacidad;

        while (hashTable[index] != null) {
            if (hashTable[index].key.equals(key)) {
                return index;  // Elemento encontrado
            }
            index = (index + 1) % capacidad;
        }
        return index;
    }

    @Override
    public void insert(K key, V value) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        if ((size() + 1 >= capacidad * LOAD_FACTOR) || getHashIndex(key) +1>=capacidad*LOAD_FACTOR) {
            resize();
        }
        int index = getHashIndex(key);
        hashTable[index] = new Dato<K,V>(key, value);
    }


    @Override
    public V search(K key) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        int index = getHashIndex(key);
        for (Dato<K, V> dato : hashTable) {
            if(dato!=null) {
                if (dato.key.equals(key)) {
                    return dato.value;
                }
            }
        }
        return null;
    }


    @Override
    public void delete(K key) throws InformacionInvalida {
        if (key == null || !contains(key)) {
            throw new InformacionInvalida();
        }
        int index = getHashIndex(key);
        hashTable[index] = null;
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
        for (Dato<K,V> dato : hashTable) {
            if(dato!=null)
                size ++;
        }
        return size;
    }

    private void resize() throws InformacionInvalida {
        int nuevaCapacidad = capacidad * 2;
        Dato<K, V>[] nuevoArray = new Dato[nuevaCapacidad];
        Dato<K, V>[] viejoArray = hashTable;
        this.capacidad = nuevaCapacidad;
        this.hashTable = nuevoArray;
        for (Dato<K, V> dato : viejoArray) {
            if (dato != null) {
                insert(dato.key, dato.value);
            }
        }


    }
}


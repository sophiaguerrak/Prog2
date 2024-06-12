package uy.edu.um.adt.hash;

import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.exceptions.InformacionInvalida;

public class HashImpl<K, V> implements MyHash<K, V> {
    private class Dato<K, V> {
        K key;
        V value;

        Dato(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private MyLinkedListImpl<Dato<K, V>>[] hashTable;
    private int capacidad;
    private static final double LOAD_FACTOR = 0.75;
    public int size;

    public HashImpl(int capacity) {
        this.capacidad = capacity;
        this.hashTable = new MyLinkedListImpl[capacity];
        for (int i = 0; i < capacity; i++) {
            hashTable[i] = new MyLinkedListImpl<>();
        }
        this.size = 0;
    }

    private int getHashIndex(K key) {
        int hash = Math.abs(key.hashCode());
        return hash % capacidad;
    }

    @Override
    public void insert(K key, V value) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        if (size + 1 >= capacidad * LOAD_FACTOR) {
            resize();
        }
        int index = getHashIndex(key);
        MyLinkedListImpl<Dato<K, V>> valores = hashTable[index];
        for (int i = 0; i < valores.size(); i++) {
            Dato<K, V> dato = valores.get(i);
            if (dato.key.equals(key)) {
                dato.value = value;
                return;
            }
        }
        valores.add(new Dato<>(key, value));
        size++;
    }

    @Override
    public V search(K key) throws InformacionInvalida {
        if (key == null) {
            throw new InformacionInvalida();
        }
        int index = getHashIndex(key);
        MyLinkedListImpl<Dato<K, V>> valores = hashTable[index];
        for (int i = 0; i < valores.size(); i++) {
            Dato<K, V> dato = valores.get(i);
            if (dato.key.equals(key)) {
                return dato.value;
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
        MyLinkedListImpl<Dato<K, V>> valores = hashTable[index];
        Dato<K, V> toRemove = null;
        for (int i = 0; i < valores.size(); i++) {
            Dato<K, V> dato = valores.get(i);
            if (dato.key.equals(key)) {
                toRemove = dato;
                valores.remove(toRemove);
                size--;
                return;
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
        return size;
    }

    private void resize() throws InformacionInvalida {
        int nuevaCapacidad = capacidad * 2;
        MyLinkedListImpl<Dato<K, V>>[] nuevoArray = new MyLinkedListImpl[nuevaCapacidad];
        for (int i = 0; i < nuevaCapacidad; i++) {
            nuevoArray[i] = new MyLinkedListImpl<>();
        }
        MyLinkedListImpl<Dato<K, V>>[] viejoArray = hashTable;
        this.capacidad = nuevaCapacidad;
        this.hashTable = nuevoArray;
        for (MyLinkedListImpl<Dato<K, V>> valores : viejoArray) {
            for (int i = 0; i < valores.size(); i++) {
                Dato<K, V> dato = valores.get(i);
                insert(dato.key, dato.value);
            }
        }
    }


}

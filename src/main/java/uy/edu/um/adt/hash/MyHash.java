package uy.edu.um.adt.hash;

import uy.edu.um.exceptions.InformacionInvalida;

public interface MyHash<K,T> {
    void insert(K key, T value) throws InformacionInvalida;
    T search(K key) throws InformacionInvalida;
    void delete(K key) throws InformacionInvalida;
    boolean contains(K key) throws InformacionInvalida;
}

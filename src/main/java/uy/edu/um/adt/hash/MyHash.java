package uy.edu.um.adt.hash;

public interface MyHash<T> {
    void insert(T value);
    T search(T key);
    void delete(T value);
}

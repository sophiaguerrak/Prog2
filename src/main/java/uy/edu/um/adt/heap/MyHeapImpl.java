package uy.edu.um.adt.heap;

import lombok.Getter;

import static java.lang.Math.floor;

@Getter
public class MyHeapImpl<T extends Comparable<T>> {
    private T[] heaparray;
    private int size;
    private int capacity;

    public MyHeapImpl(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heaparray = (T[]) new Comparable[capacity];
    }

    public T get() {
        if (size == 0)
            return null;

        T valor = heaparray[0]; // elemento mas grande a la raiz
        heaparray[0] = heaparray[size - 1]; // mueve el ultimo elemento a la raiz
        heaparray[size - 1] = null; // elimina el ultimo elemento
        size--;
        if (size > 1) {
            heapdown(0);
        }
        return valor;
    }

    public void insert(T dato) {
        if (size >= capacity) {
            System.out.println("heap lleno");
            return;
        }
        heaparray[size] = dato;
        heapup(size);
        size++;
    }

    public void heapup(int size) {
        int padre = (size - 1) / 2;
        if (padre >= 0) {
            int value = heaparray[padre].compareTo(heaparray[size]);
            if (value < 0) {
                intercambiar(heaparray, padre, size);
                heapup(padre);
            }
        }
    }

    public void heapdown(int padre) {
        int hijoizq = (2 * padre) + 1;
        int hijoder = (2 * padre) + 2;
        int mayor = padre;
        if (hijoizq < size && heaparray[hijoizq] != null && heaparray[hijoizq].compareTo(heaparray[padre]) > 0) {
            mayor = hijoizq;
        }
        if (hijoder < size && heaparray[hijoder] != null && heaparray[hijoder].compareTo(heaparray[mayor]) > 0) {
            mayor = hijoder;
        }


        if (mayor != padre) {
            intercambiar(heaparray, padre, mayor);
            heapdown(mayor);
        }
    }

    public void intercambiar(T[] heaparray, int indice1, int indice2) {
        T aux = heaparray[indice1];
        heaparray[indice1] = heaparray[indice2];
        heaparray[indice2] = aux;
    }

    public void setHeaparray(T[] heaparray) {
        this.heaparray = heaparray;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void printHeap() {
        if (size == 0) {
            System.out.println("El heap está vacío.");
        } else {
            System.out.println("Estado actual del heap:");
            for (int i = 0; i < size; i++) {
                System.out.print(heaparray[i] + " ");
            }
            System.out.println();
        }
    }
}


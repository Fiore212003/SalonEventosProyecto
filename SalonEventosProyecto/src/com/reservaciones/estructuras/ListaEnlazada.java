/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.reservaciones.estructuras;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author fiore
 */
public class ListaEnlazada<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Nodo interno de la lista
    private class Nodo implements Serializable {
        private static final long serialVersionUID = 1L;
        T dato;
        Nodo siguiente;
        
        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
    
    private Nodo cabeza;
    private int tamanio;
    
    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }
    
   
     // Agrega un elemento al final de la lista
    
    public void agregar(T dato) {
        Nodo nuevoNodo = new Nodo(dato);
        
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamanio++;
    }
    
    
     // Agrega un elemento al inicio de la lista
     
    public void agregarAlInicio(T dato) {
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
        tamanio++;
    }
    
    
     // Elimina un elemento de la lista
     
    public boolean eliminar(T dato) {
        if (cabeza == null) {
            return false;
        }
        
        // si el elemento esta en la cabeza
        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.siguiente;
            tamanio--;
            return true;
        }
        
        // busscar en el resto de la lista
        Nodo actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.dato.equals(dato)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamanio--;
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
    
    
     // busca un elemento en la lista
     
    public T buscar(T dato) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    
     // Obtiene un elemento por indice
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    
     // Verifica si la lista esta vacia
     
    public boolean estaVacia() {
        return cabeza == null;
    }
    
   
      //Obtiene el tamaño de la lista
     
    public int getTamanio() {
        return tamanio;
    }
    
   
     /// Convierte la lista a un ArrayList para facilitar operaciones
     
    public List<T> aLista() {
        List<T> lista = new ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }
    
      // Limpia toda la lista
    
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }
    
   
     // Muestra todos los elementos
     
    public void mostrarTodos() {
        Nodo actual = cabeza;
        System.out.println("== Lista Enlazada ==");
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
        System.out.println("Total elementos: " + tamanio);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo actual = cabeza;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}

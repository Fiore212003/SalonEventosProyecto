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
public class Cola<T> implements Serializable {
    
    
    private static final long serialVersionUID = 1L;
    
    // Nodo interno de la cola
    private class Nodo implements Serializable {
        private static final long serialVersionUID = 1L;
        T dato;
        Nodo siguiente;
        
        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
    
    private Nodo frente;
    private Nodo fin;
    private int tamanio;
    
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }
    
    /**
     * Encola un elemento agrega al final
     */
    public void encolar(T dato) {
        Nodo nuevoNodo = new Nodo(dato);
        
        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        tamanio++;
    }
    
    
      //Desencola un elemento que elimina del frente
     
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        
        T dato = frente.dato;
        frente = frente.siguiente;
        
        if (frente == null) {
            fin = null;
        }
        
        tamanio--;
        return dato;
    }
    
   
     // Obtiene el elemento al frente sin eliminarlo
    
    public T verFrente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frente.dato;
    }
    
   
     // Verifica si la cola est vacia
    
    public boolean estaVacia() {
        return frente == null;
    }
    
   
     // Obtiene el tamaño de la cola
    
    public int getTamanio() {
        return tamanio;
    }
    
    
    // Convierte la cola a una lista
    
    public List<T> aLista() {
        List<T> lista = new ArrayList<>();
        Nodo actual = frente;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }
    
   
     // Limpia toda la cola
     
    public void limpiar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }
    
    
     // Muestra todos los elementos de la cola
     
    public void mostrarTodos() {
        if (estaVacia()) {
            System.out.println("La cola esta vacia");
            return;
        }
        
        System.out.println("=== Cola (Lista de Espera) ===");
        Nodo actual = frente;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". " + actual.dato);
            actual = actual.siguiente;
            posicion++;
        }
        System.out.println("Total en espera: " + tamanio);
    }
    
    
    // Busca si un elemento existe en la cola
     
    public boolean contiene(T dato) {
        Nodo actual = frente;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Frente -> [");
        Nodo actual = frente;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        sb.append("] <- Fin");
        return sb.toString();
    }


}

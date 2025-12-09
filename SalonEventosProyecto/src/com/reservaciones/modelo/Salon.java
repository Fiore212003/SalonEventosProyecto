/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.reservaciones.modelo;

import java.io.Serializable;

/**
 *
 * @author fiore
 */

    
    
    //En esta clase lo que vamos hacer crear toda la informacion del salon de eventos
public class Salon implements Serializable {
    
    //Vamos a crear todos los atributos que tiene el salon 
    
    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String nombre;
    private int capacidad;
    private double precioPorHora;
    private String descripcion;
    private boolean disponible;
    
    
    //Se crean los costructores para poder inicializar los atributos
    // Constructor vacío
    public Salon() {
        this.disponible = true;
    }
    
    // Constructor con parámetros
    public Salon(String codigo, String nombre, int capacidad, double precioPorHora, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioPorHora = precioPorHora;
        this.descripcion = descripcion;
        this.disponible = true;
    }
    
    // Getters y Setters para modificar y acceder a los datos
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public double getPrecioPorHora() {
        return precioPorHora;
    }
    
    public void setPrecioPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() { //´Para cambiar los valores numericos a texto
        return "Salon{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", precioPorHora=" + precioPorHora +
                ", disponible=" + disponible +
                '}';
    }
    
}

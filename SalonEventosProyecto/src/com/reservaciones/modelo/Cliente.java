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
 
    //En esta clase lo que voy hacer es definir los atributos del cliente y generar los constructores para inicializarlos .
    
    public class Cliente implements Serializable { //A esta clase le implementamos SERIALIZABLE para guardar el estado de un objeto y volver a crerarlo cuando sea necesario
    private static final long serialVersionUID = 1L;
    
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    
    // Constructor vacío que permite inizializarlo con valores predeterminados y luego asignar valor a suus propiedades individualmente
    public Cliente() {
    }
    
    // Constructor con parámetros
    public Cliente(String cedula, String nombre, String apellido, String telefono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Luego voy a crear los metodos get y set para poder modificar los datos y tambien poder acceder .
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    @Override //Usamos esta anotacion porque nos indica que un metodo esta redefiniendo o sobreescribiendo un metodo
    
   
    public String toString() { // Usamos este metodo para convertir los valores numericos en un tipo string(texto)
        return "Cliente{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
    
    
}

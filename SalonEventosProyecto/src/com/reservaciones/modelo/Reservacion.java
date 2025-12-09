/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.reservaciones.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author fiore
 */
//En esta clase se crea toda la imformacion de la reservaciones que se hacen
public class Reservacion implements Serializable, Comparable<Reservacion>{
    
    //Creamos todos los atributos que se necesiten para la reservacion
    private static final long serialVersionUID = 1L;
    
    private String codigoReservacion;
    private Cliente cliente;
    private Salon salon;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int numeroPersonas;
    private String tipoEvento;
    private double montoTotal;
    private String estado; // "Confirmada", "Pendiente", "Cancelada"
    
    // Constructor vacío que se inicializa con valores predeterminados y luego se les asigna valores asus propiedades.
    public Reservacion() {
        this.estado = "Pendiente";
    }
    
    // Constructor con parámetros para inicializar los atributos
    public Reservacion(String codigoReservacion, Cliente cliente, Salon salon, 
                       LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                       int numeroPersonas, String tipoEvento) {
        this.codigoReservacion = codigoReservacion;
        this.cliente = cliente;
        this.salon = salon;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numeroPersonas = numeroPersonas;
        this.tipoEvento = tipoEvento;
        this.estado = "Confirmada";
        calcularMontoTotal();
    }
    
    //  Creo un metodo para Calcular el monto total basado en las horas y asi dar un precio
    public void calcularMontoTotal() {
        if (horaInicio != null && horaFin != null && salon != null) {
            long horasDiferencia = java.time.Duration.between(horaInicio, horaFin).toHours();
            this.montoTotal = horasDiferencia * salon.getPrecioPorHora();
        }
    }
    
    // Getters y Setters para acceder o modificar la informacion
    public String getCodigoReservacion() {
        return codigoReservacion;
    }
    
    public void setCodigoReservacion(String codigoReservacion) {
        this.codigoReservacion = codigoReservacion;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Salon getSalon() {
        return salon;
    }
    
    public void setSalon(Salon salon) {
        this.salon = salon;
        calcularMontoTotal();
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        calcularMontoTotal();
    }
    
    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
        calcularMontoTotal();
    }
    
    public int getNumeroPersonas() {
        return numeroPersonas;
    }
    
    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
    
    public String getTipoEvento() {
        return tipoEvento;
    }
    
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
    
    public double getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override// Nos indica el metodo que estamos sobreescribiendo
    public int compareTo(Reservacion otra) {
        // Compara por fecha para ordenamiento
        return this.fecha.compareTo(otra.fecha);
    }
    
    @Override
    public String toString() { // Y este toString nos  permite cambiar los datos numericos a texto
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Reservacion{" +
                "codigo='" + codigoReservacion + '\'' +
                ", cliente=" + cliente.getNombreCompleto() +
                ", salon=" + salon.getNombre() +
                ", fecha=" + fecha.format(formatoFecha) +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", estado='" + estado + '\'' +
                ", monto=" + montoTotal +
                '}';
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.reservaciones.logica;

import com.reservaciones.estructuras.Cola;
import com.reservaciones.estructuras.ListaEnlazada;
import com.reservaciones.modelo.Cliente;
import com.reservaciones.modelo.Reservacion;
import com.reservaciones.modelo.Salon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author fiore
 */

//En esta clase gestiona la reservaciones de un salon de eventos y se manejan clientes 
//Tambien se manejan los clientes los salones que estan disponibles y las reservaciones que se han realizado
public class GestorReservaciones {
    
    //Se crean los atributos
    
    private ListaEnlazada<Reservacion> reservaciones; // Almacena todas las resrvaciones confimadas 
    private Cola<Cliente> listaEspera; // Cola para clientes que esperan disponibilidad y se implementa FIFO que es que el primero en llegar va hacer el primero que se atiende
    private List<Salon> salones; // Representa la lista de los salones que estan disponibles
    private List<Cliente> clientes; // Representa toda la lista de clientes registrados
    
    //y estan las constantes
    private static final String ARCHIVO_RESERVACIONES = "reservaciones.dat";
    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_SALONES = "salones.txt";
    
    
    //Creo los constructores para inicializar los atributos
    public GestorReservaciones() {
        this.reservaciones = new ListaEnlazada<>();
        this.listaEspera = new Cola<>();
        this.salones = new ArrayList<>();
        this.clientes = new ArrayList<>();
        
        inicializarSalones(); //Esto nos permite cargar los salones disponibles
        cargarDatos(); //llamamos a cargarDatos para poder recuperar informacion persistida en archivos
    }
    
    // ==================== MeTODOS DE RESERVACIONES ====================
    //Creo el metodo para agregar una nueva 
    public boolean agregarReservacion(Reservacion reservacion) {
        // Validar que el salón esté disponible
        if (!verificarDisponibilidad(reservacion.getSalon(), reservacion.getFecha(), 
                                     reservacion.getHoraInicio(), reservacion.getHoraFin())) { //si no esta disponible retorna false
            return false;
        }
        
        reservaciones.agregar(reservacion);//Y si esta disponible se agrega la reservacion
        guardarReservaciones(); //Y aqui guardamos la informacion 
        return true;//Retorna true para decir que es exitoso
    }
    
    //Vamos a crear un metodo para eliminar una reservacion pero solo con el codigox
    public boolean eliminarReservacion(String codigo) {
        List<Reservacion> lista = reservaciones.aLista();//Convierte la lista enlazada en una lista estandar para poder iterarla
        for (Reservacion r : lista) { 
            if (r.getCodigoReservacion().equals(codigo)) {//Verifica si el codigo de la reservacion actual conside con el codigo buscado
                boolean eliminado = reservaciones.eliminar(r);//Y llamamos al metodo eliminar 
                if (eliminado) {
                    guardarReservaciones();//Y si la eliminacion fue existosa se guardan los cambios en el archivo
                }
                return eliminado; // y nos va a retornar si se elimino o no
            }
        }
        return false; // si no se encuentra el codigo retorna false
    }
    
    /**
     * Busca  y retorna una reservación especifica  por código
     */
    public Reservacion buscarReservacion(String codigo) {
        List<Reservacion> lista = reservaciones.aLista();
        
        // Este ciclo for recorre cada reservacion y compara los codigos
        //Si se termina el ciclo sin encontrar nada  retorna null
        for (Reservacion r : lista) {
            if (r.getCodigoReservacion().equals(codigo)) {
                return r;
            }
        }
        return null;
    }
    
    /**
     * El objetivo es retornar todas las reservaciones del sistema
     * Este metodo no recibe parametros y va a retornar una lista con todas las reservaciones
     * y es un metodo util para mostrar informacion en interfaces
     */
    public List<Reservacion> obtenerTodasReservaciones() {
        return reservaciones.aLista();
    }
    
    
    //Este metodo es para verificar la disponibilidad 
    //verifica si un salon esta disponible en una fecha y horario especifico
    //Este metodo si recibe parametros como salon ,fecha, hora de inicio y hora fin
    

    private boolean verificarDisponibilidad(Salon salon, LocalDate fecha, 
         java.time.LocalTime horaInicio, 
        java.time.LocalTime horaFin) {
        List<Reservacion> lista = reservaciones.aLista();
        for (Reservacion r : lista) {
            if (r.getSalon().getCodigo().equals(salon.getCodigo()) && 
                r.getFecha().equals(fecha) &&
                !r.getEstado().equals("Cancelada")) {
                
                // Verificar si hay traslape de horarios
                if (!(horaFin.isBefore(r.getHoraInicio()) || 
                      horaInicio.isAfter(r.getHoraFin()))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // ==================== MÉTODOS DE ORDENAMIENTO (QuickSort) ====================
    
    /**
     * Ordena las reservaciones por fecha usando QuickSort
     * QuickSort es un algoritmo de ordenamiento rapido que funciona bajo el principio divide y vencerar
     * lo que hace es que selecciona un elemento pivote (es una informacion reorganizada)y ordena los valores mayores en un lado
     * y los menores de otro y esto se repite asta que se ordene 
     */
    public List<Reservacion> ordenarReservacionesPorFecha() {
        List<Reservacion> lista = reservaciones.aLista();
        if (lista.size() <= 1) {
            return lista;
        }
        quickSort(lista, 0, lista.size() - 1);
        return lista;
    }
    
    /**
     * Implementacion de QuickSort
     */
    private void quickSort(List<Reservacion> lista, int inicio, int fin) {
        if (inicio < fin) {
            int indicePivote = particion(lista, inicio, fin);
            quickSort(lista, inicio, indicePivote - 1);
            quickSort(lista, indicePivote + 1, fin);
        }
    }
    
    /**
     * Metodo de particion para QuickSort
     */
    private int particion(List<Reservacion> lista, int inicio, int fin) {
        Reservacion pivote = lista.get(fin);
        int i = inicio - 1;
        
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j).getFecha().isBefore(pivote.getFecha()) ||
                lista.get(j).getFecha().equals(pivote.getFecha())) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        
        Collections.swap(lista, i + 1, fin);
        return i + 1;
    }
    
    // ==================== MeTODOS DE LISTA DE ESPERA ====================
    
    /**
     * Agrega un cliente a la lista de espera
     */
    public void agregarAListaEspera(Cliente cliente) {
        listaEspera.encolar(cliente);
    }
    
    /**
     * Atiende al siguiente cliente en espera
     */
    public Cliente atenderSiguienteEnEspera() {
        if (!listaEspera.estaVacia()) {
            return listaEspera.desencolar();
        }
        return null;
    }
    
    /**
     * Obtiene la lista de espera completa
     */
    public List<Cliente> obtenerListaEspera() {
        return listaEspera.aLista();
    }
    
    // =================== METODOS DE CLIENTES Y SALONES ====================
    
    public List<Salon> obtenerSalones() {
        return salones;
    }
    
    public Salon buscarSalon(String codigo) {
        for (Salon s : salones) {
            if (s.getCodigo().equals(codigo)) {
                return s;
            }
        }
        return null;
    }
    
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        guardarClientes();
    }
    
    public List<Cliente> obtenerClientes() {
        return clientes;
    }
    
    public Cliente buscarCliente(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }
    
    // ==================== PERSISTENCIA DE DATOS ====================
    
    /**
     * Guarda las reservaciones en archivo
     */
    public void guardarReservaciones() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_RESERVACIONES))) {
            oos.writeObject(reservaciones);
        } catch (IOException e) {
            System.err.println("Error al guardar reservaciones: " + e.getMessage());
        }
    }
    
    /**
     * Carga las reservaciones desde archivo
     */
    @SuppressWarnings("unchecked")
    private void cargarReservaciones() {
        File archivo = new File(ARCHIVO_RESERVACIONES);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(ARCHIVO_RESERVACIONES))) {
                reservaciones = (ListaEnlazada<Reservacion>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar reservaciones: " + e.getMessage());
            }
        }
    }
    
    /**
     * Guarda clientes en archivo de texto
     */
    private void guardarClientes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_CLIENTES))) {
            for (Cliente c : clientes) {
                writer.println(c.getCedula() + "|" + c.getNombre() + "|" + 
                             c.getApellido() + "|" + c.getTelefono() + "|" + c.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }
    
    /**
     * Carga clientes desde archivo de texto
     */
    private void cargarClientes() {
        File archivo = new File(ARCHIVO_CLIENTES);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 5) {
                        Cliente c = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4]);
                        clientes.add(c);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al cargar clientes: " + e.getMessage());
            }
        }
    }
    
    /**
     * Inicializa los salones disponibles
     */
    private void inicializarSalones() {
        salones.add(new Salon("S001", "Salón Imperial", 200, 50000.0, 
                    "Salón elegante para eventos grandes"));
        salones.add(new Salon("S002", "Salón Crystal", 100, 35000.0, 
                    "Salón moderno con iluminación LED"));
        salones.add(new Salon("S003", "Salón Garden", 80, 28000.0, 
                    "Salón con vista al jardín"));
        salones.add(new Salon("S004", "Salón Executive", 50, 20000.0, 
                    "Salón para reuniones corporativas"));
    }
    
    /**
     * Carga todos los datos al iniciar
     */
    private void cargarDatos() {
        cargarReservaciones();
        cargarClientes();
    }
    
    /**
     * Genera un codig unico para reservacion
     */
    public String generarCodigoReservacion() {
        return "R" + System.currentTimeMillis();
    }
    
}



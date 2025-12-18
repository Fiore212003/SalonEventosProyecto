/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.representaciones.gui;

import com.reservaciones.logica.GestorReservaciones;
import com.reservaciones.modelo.Cliente;
import com.reservaciones.modelo.Reservacion;
import com.reservaciones.modelo.Salon;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fiore
 */
public class InterfazPrincipal  extends JFrame{
    
    
     private GestorReservaciones gestor;
    private JTabbedPane panelPestañas;
    
    // Componentes para Reservaciones
    private JTable tablaReservaciones;
    private DefaultTableModel modeloTablaReservaciones;
    
    // Componentes para Nueva Reservacion
    private JTextField txtCodigo, txtCedula, txtNombre, txtApellido, txtTelefono, txtEmail;
    private JTextField txtFecha, txtHoraInicio, txtHoraFin, txtNumPersonas;
    private JComboBox<String> cmbSalon, cmbTipoEvento;
    
    // Componentes para Lista de Espera
    private JTable tablaEspera;
    private DefaultTableModel modeloTablaEspera;
    
    public InterfazPrincipal() {
        gestor = new GestorReservaciones();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Reservaciones de Salones - Universidad Castro Carazo");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        panelPestañas = new JTabbedPane();
        
        // Pestaña de Reservaciones
        panelPestañas.addTab("Reservaciones", crearPanelReservaciones());
        
        // Pestaña de Nueva Reservación
        panelPestañas.addTab("Nueva Reservación", crearPanelNuevaReservacion());
        
        // Pestaña de Lista de Espera
        panelPestañas.addTab("Lista de Espera", crearPanelListaEspera());
        
        // Pestaña de Salones
        panelPestañas.addTab("Salones Disponibles", crearPanelSalones());
        
        add(panelPestañas);
    }
    
    // ==================== PANEL DE RESERVACIONES ====================
    
    private JPanel crearPanelReservaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titulo = new JLabel("Gestión de Reservaciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Tabla de reservaciones
        String[] columnas = {"Código", "Cliente", "Salón", "Fecha", "Hora Inicio", 
                           "Hora Fin", "Personas", "Evento", "Monto", "Estado"};
        modeloTablaReservaciones = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReservaciones = new JTable(modeloTablaReservaciones);
        JScrollPane scrollPane = new JScrollPane(tablaReservaciones);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarReservaciones());
        
        JButton btnOrdenar = new JButton("Ordenar por Fecha");
        btnOrdenar.addActionListener(e -> ordenarReservaciones());
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarReservacion());
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarReservacion());
        
        JButton btnCambiarEstado = new JButton("Cambiar Estado");
        btnCambiarEstado.addActionListener(e -> cambiarEstadoReservacion());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnOrdenar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCambiarEstado);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ==================== Panel nueva reservacion ====================
    
    private JPanel crearPanelNuevaReservacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titulo = new JLabel("Nueva Reservación", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(12, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reservación"));
        
        // Campos del formulario
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setText(gestor.generarCodigoReservacion());
        
        txtCedula = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();
        
        // Cargar salones en combo box
        cmbSalon = new JComboBox<>();
        for (Salon salon : gestor.obtenerSalones()) {
            cmbSalon.addItem(salon.getCodigo() + " - " + salon.getNombre());
        }
        
        txtFecha = new JTextField(LocalDate.now().toString());
        txtHoraInicio = new JTextField("10:00");
        txtHoraFin = new JTextField("15:00");
        txtNumPersonas = new JTextField();
        
        cmbTipoEvento = new JComboBox<>(new String[]{
            "Boda", "XV Años", "Cumpleaños", "Corporativo", "Graduación", "Otro"
        });
        
        // Agregar componentes
        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("Cédula Cliente:"));
        panelFormulario.add(txtCedula);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Salón:"));
        panelFormulario.add(cmbSalon);
        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora Inicio (HH:MM):"));
        panelFormulario.add(txtHoraInicio);
        panelFormulario.add(new JLabel("Hora Fin (HH:MM):"));
        panelFormulario.add(txtHoraFin);
        panelFormulario.add(new JLabel("Número de Personas:"));
        panelFormulario.add(txtNumPersonas);
        panelFormulario.add(new JLabel("Tipo de Evento:"));
        panelFormulario.add(cmbTipoEvento);
        
        panel.add(panelFormulario, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnGuardar = new JButton("Guardar Reservación");
        btnGuardar.addActionListener(e -> guardarReservacion());
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        JButton btnBuscarCliente = new JButton("Buscar Cliente Existente");
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        
        panelBotones.add(btnBuscarCliente);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ==================== Panel lista de espera ====================
    
    private JPanel crearPanelListaEspera() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Lista de Espera", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);
        
        String[] columnas = {"Posición", "Cédula", "Nombre Completo", "Teléfono", "Email"};
        modeloTablaEspera = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEspera = new JTable(modeloTablaEspera);
        JScrollPane scrollPane = new JScrollPane(tablaEspera);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnAgregar = new JButton("Agregar a Espera");
        btnAgregar.addActionListener(e -> agregarAListaEspera());
        
        JButton btnAtender = new JButton("Atender Siguiente");
        btnAtender.addActionListener(e -> atenderSiguiente());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarListaEspera());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnAtender);
        panelBotones.add(btnActualizar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ==================== PANEL SALONES ====================
    
    private JPanel crearPanelSalones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Salones Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel panelSalones = new JPanel(new GridLayout(2, 2, 10, 10));
        
        for (Salon salon : gestor.obtenerSalones()) {
            JPanel panelSalon = new JPanel(new BorderLayout());
            panelSalon.setBorder(BorderFactory.createTitledBorder(salon.getNombre()));
            
            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setText(String.format(
                "Código: %s\n" +
                "Capacidad: %d personas\n" +
                "Precio por hora: ₡%.2f\n" +
                "Descripción: %s\n" +
                "Estado: %s",
                salon.getCodigo(),
                salon.getCapacidad(),
                salon.getPrecioPorHora(),
                salon.getDescripcion(),
                salon.isDisponible() ? "Disponible" : "No disponible"
            ));
            
            panelSalon.add(new JScrollPane(info), BorderLayout.CENTER);
            panelSalones.add(panelSalon);
        }
        
        panel.add(panelSalones, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ==================== Metodo de la logica ====================
    
    private void guardarReservacion() {
        try {
            if (txtCedula.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los datos del cliente");
                return;
            }
            
            Cliente cliente = gestor.buscarCliente(txtCedula.getText().trim());
            if (cliente == null) {
                cliente = new Cliente(
                    txtCedula.getText().trim(),
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtEmail.getText().trim()
                );
                gestor.agregarCliente(cliente);
            }
            
            String codigoSalon = ((String) cmbSalon.getSelectedItem()).split(" - ")[0];
            Salon salon = gestor.buscarSalon(codigoSalon);
            
            Reservacion reservacion = new Reservacion(
                txtCodigo.getText(),
                cliente,
                salon,
                LocalDate.parse(txtFecha.getText()),
                LocalTime.parse(txtHoraInicio.getText()),
                LocalTime.parse(txtHoraFin.getText()),
                Integer.parseInt(txtNumPersonas.getText()),
                (String) cmbTipoEvento.getSelectedItem()
            );
            
            if (gestor.agregarReservacion(reservacion)) {
                JOptionPane.showMessageDialog(this, 
                    "Reservación guardada exitosamente\nMonto Total: ₡" + 
                    String.format("%.2f", reservacion.getMontoTotal()));
                limpiarFormulario();
                cargarReservaciones();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "El salón no está disponible en ese horario");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        txtCodigo.setText(gestor.generarCodigoReservacion());
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtFecha.setText(LocalDate.now().toString());
        txtHoraInicio.setText("10:00");
        txtHoraFin.setText("15:00");
        txtNumPersonas.setText("");
    }
    
    private void buscarCliente() {
        String cedula = JOptionPane.showInputDialog(this, "Ingrese la cédula del cliente:");
        if (cedula != null && !cedula.trim().isEmpty()) {
            Cliente cliente = gestor.buscarCliente(cedula.trim());
            if (cliente != null) {
                txtCedula.setText(cliente.getCedula());
                txtNombre.setText(cliente.getNombre());
                txtApellido.setText(cliente.getApellido());
                txtTelefono.setText(cliente.getTelefono());
                txtEmail.setText(cliente.getEmail());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        }
    }
    
    private void cargarReservaciones() {
        modeloTablaReservaciones.setRowCount(0);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Reservacion r : gestor.obtenerTodasReservaciones()) {
            modeloTablaReservaciones.addRow(new Object[]{
                r.getCodigoReservacion(),
                r.getCliente().getNombreCompleto(),
                r.getSalon().getNombre(),
                r.getFecha().format(formato),
                r.getHoraInicio(),
                r.getHoraFin(),
                r.getNumeroPersonas(),
                r.getTipoEvento(),
                String.format("₡%.2f", r.getMontoTotal()),
                r.getEstado()
            });
        }
    }
    
    private void ordenarReservaciones() {
        modeloTablaReservaciones.setRowCount(0);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        List<Reservacion> ordenadas = gestor.ordenarReservacionesPorFecha();
        for (Reservacion r : ordenadas) {
            modeloTablaReservaciones.addRow(new Object[]{
                r.getCodigoReservacion(),
                r.getCliente().getNombreCompleto(),
                r.getSalon().getNombre(),
                r.getFecha().format(formato),
                r.getHoraInicio(),
                r.getHoraFin(),
                r.getNumeroPersonas(),
                r.getTipoEvento(),
                String.format("₡%.2f", r.getMontoTotal()),
                r.getEstado()
            });
        }
        JOptionPane.showMessageDialog(this, "Reservaciones ordenadas por fecha");
    }
    
    private void buscarReservacion() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código de reservación:");
        if (codigo != null && !codigo.trim().isEmpty()) {
            Reservacion r = gestor.buscarReservacion(codigo.trim());
            if (r != null) {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String info = String.format(
                    "Código: %s\nCliente: %s\nSalón: %s\nFecha: %s\nHorario: %s - %s\n" +
                    "Personas: %d\nTipo: %s\nMonto: ₡%.2f\nEstado: %s",
                    r.getCodigoReservacion(), r.getCliente().getNombreCompleto(),
                    r.getSalon().getNombre(), r.getFecha().format(formato),
                    r.getHoraInicio(), r.getHoraFin(), r.getNumeroPersonas(),
                    r.getTipoEvento(), r.getMontoTotal(), r.getEstado()
                );
                JOptionPane.showMessageDialog(this, info, "Reservación Encontrada", 
                                            JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Reservación no encontrada");
            }
        }
    }
    
    private void eliminarReservacion() {
        int fila = tablaReservaciones.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTablaReservaciones.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar esta reservación?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (gestor.eliminarReservacion(codigo)) {
                    JOptionPane.showMessageDialog(this, "Reservación eliminada");
                    cargarReservaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una reservación");
        }
    }
    
    private void cambiarEstadoReservacion() {
        int fila = tablaReservaciones.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTablaReservaciones.getValueAt(fila, 0);
            Reservacion r = gestor.buscarReservacion(codigo);
            
            if (r != null) {
                String[] opciones = {"Confirmada", "Pendiente", "Cancelada"};
                String nuevoEstado = (String) JOptionPane.showInputDialog(this,
                    "Seleccione el nuevo estado:",
                    "Cambiar Estado",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    r.getEstado());
                
                if (nuevoEstado != null) {
                    r.setEstado(nuevoEstado);
                    gestor.guardarReservaciones();
                    cargarReservaciones();
                    JOptionPane.showMessageDialog(this, "Estado actualizado");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una reservación");
        }
    }
    
    private void agregarAListaEspera() {
        String cedula = JOptionPane.showInputDialog(this, "Ingrese la cédula del cliente:");
        if (cedula != null && !cedula.trim().isEmpty()) {
            Cliente cliente = gestor.buscarCliente(cedula.trim());
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Cliente no encontrado. Primero debe registrarlo en una reservación.");
            } else {
                gestor.agregarAListaEspera(cliente);
                JOptionPane.showMessageDialog(this, "Cliente agregado a lista de espera");
                cargarListaEspera();
            }
        }
    }
    
    private void atenderSiguiente() {
        Cliente cliente = gestor.atenderSiguienteEnEspera();
        if (cliente != null) {
            JOptionPane.showMessageDialog(this, 
                "Atendiendo a: " + cliente.getNombreCompleto() + 
                "\nTeléfono: " + cliente.getTelefono());
            cargarListaEspera();
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en espera");
        }
    }
    
    private void cargarListaEspera() {
        modeloTablaEspera.setRowCount(0);
        List<Cliente> espera = gestor.obtenerListaEspera();
        int posicion = 1;
        for (Cliente c : espera) {
            modeloTablaEspera.addRow(new Object[]{
                posicion++,
                c.getCedula(),
                c.getNombreCompleto(),
                c.getTelefono(),
                c.getEmail()
            });
        }
    }
    
    private void cargarDatos() {
        cargarReservaciones();
        cargarListaEspera();
    }
    
}

package com.alkemy.billetera.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una transacción registrada en la billetera.
 * Cada operación (depósito, retiro, conversión) genera una transacción.
 */
public class Transaccion {

    private int idTransaccion;
    private double monto;
    private TipoTransaccion tipo;
    private LocalDateTime fecha;
    private String descripcion;

    /**
     * Constructor de transacción. La fecha se asigna automáticamente.
     * @param idTransaccion identificador único de la transacción
     * @param monto         monto involucrado
     * @param tipo          tipo de transacción (depósito, retiro, conversión)
     * @param descripcion   detalle descriptivo de la operación
     */
    public Transaccion(int idTransaccion, double monto, TipoTransaccion tipo, String descripcion) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    // Getters
    public int getIdTransaccion() { return idTransaccion; }
    public double getMonto() { return monto; }
    public TipoTransaccion getTipo() { return tipo; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }

    // Setters
    public void setIdTransaccion(int idTransaccion) { this.idTransaccion = idTransaccion; }
    public void setMonto(double monto) { this.monto = monto; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s | Monto: $%.2f | Fecha: %s",
                tipo.getDescripcion(), descripcion, monto, fecha.format(formato));
    }
}

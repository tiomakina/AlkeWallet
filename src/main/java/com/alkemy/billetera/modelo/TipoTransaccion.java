package com.alkemy.billetera.modelo;

/**
 * Enum que representa los tipos de transacción disponibles en la billetera.
 */
public enum TipoTransaccion {
    DEPOSITO("Depósito"),
    RETIRO("Retiro"),
    CONVERSION_MONEDA("Conversión de moneda");

    private final String descripcion;

    TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

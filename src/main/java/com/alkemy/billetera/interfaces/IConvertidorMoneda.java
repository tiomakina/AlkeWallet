package com.alkemy.billetera.interfaces;

/**
 * Interfaz para la conversión entre distintas monedas.
 */
public interface IConvertidorMoneda {

    /**
     * Convierte un monto desde una moneda de origen a una de destino.
     * @param monto          cantidad a convertir
     * @param monedaOrigen   código de moneda de origen (ej: "CLP")
     * @param monedaDestino  código de moneda de destino (ej: "USD")
     * @return monto convertido
     */
    double convertir(double monto, String monedaOrigen, String monedaDestino);

    /**
     * Obtiene la tasa de cambio entre dos monedas.
     * @param monedaOrigen   código de moneda de origen
     * @param monedaDestino  código de moneda de destino
     * @return tasa de cambio
     */
    double obtenerTasa(String monedaOrigen, String monedaDestino);
}

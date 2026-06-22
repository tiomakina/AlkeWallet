package com.alkemy.billetera.servicio;

import com.alkemy.billetera.interfaces.IConvertidorMoneda;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de conversión de monedas.
 * Implementa IConvertidorMoneda con tasas de cambio configurables.
 * Las tasas predeterminadas incluyen el peso chileno (CLP) como moneda base.
 */
public class ConvertidorMoneda implements IConvertidorMoneda {

    /** Mapa de tasas: clave = "ORIGEN_DESTINO", valor = tasa de cambio */
    private Map<String, Double> tasas;

    /**
     * Constructor que inicializa tasas de cambio predeterminadas.
     * Se incluye CLP como moneda principal por contexto chileno.
     */
    public ConvertidorMoneda() {
        tasas = new HashMap<>();
        // Peso chileno (CLP) - moneda base
        tasas.put("CLP_USD", 1.0 / 930.0);
        tasas.put("USD_CLP", 930.0);
        tasas.put("CLP_EUR", 1.0 / 1010.0);
        tasas.put("EUR_CLP", 1010.0);
        tasas.put("CLP_UF",  1.0 / 37200.0);   // Unidad de Fomento
        tasas.put("UF_CLP",  37200.0);
        // Otras monedas de uso frecuente en Chile
        tasas.put("USD_EUR", 0.92);
        tasas.put("EUR_USD", 1.08);
        tasas.put("USD_ARS", 900.0);
        tasas.put("ARS_USD", 1.0 / 900.0);
    }

    /**
     * Convierte un monto entre dos monedas.
     * Si origen y destino son iguales, retorna el mismo monto sin modificar.
     * @throws IllegalArgumentException si el monto es inválido o la tasa no existe
     */
    @Override
    public double convertir(double monto, String monedaOrigen, String monedaDestino) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a convertir debe ser mayor a 0.");
        }
        if (monedaOrigen.equalsIgnoreCase(monedaDestino)) {
            return monto;
        }
        double tasa = obtenerTasa(monedaOrigen.toUpperCase(), monedaDestino.toUpperCase());
        return Math.round(monto * tasa * 100.0) / 100.0;
    }

    /**
     * Retorna la tasa de cambio entre dos monedas.
     * @throws IllegalArgumentException si la tasa no está registrada
     */
    @Override
    public double obtenerTasa(String monedaOrigen, String monedaDestino) {
        String clave = monedaOrigen.toUpperCase() + "_" + monedaDestino.toUpperCase();
        if (!tasas.containsKey(clave)) {
            throw new IllegalArgumentException(
                "Tasa de cambio no disponible: " + monedaOrigen + " -> " + monedaDestino);
        }
        return tasas.get(clave);
    }

    /**
     * Agrega o actualiza una tasa de cambio personalizada.
     * @throws IllegalArgumentException si la tasa es menor o igual a 0
     */
    public void agregarTasa(String monedaOrigen, String monedaDestino, double tasa) {
        if (tasa <= 0) {
            throw new IllegalArgumentException("La tasa de cambio debe ser mayor a 0.");
        }
        tasas.put(monedaOrigen.toUpperCase() + "_" + monedaDestino.toUpperCase(), tasa);
    }
}

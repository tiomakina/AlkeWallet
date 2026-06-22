package com.alkemy.billetera.interfaces;

/**
 * Interfaz que define las operaciones básicas de una billetera digital.
 */
public interface IOperacionesBilletera {

    /**
     * Deposita dinero en la billetera.
     * @param monto cantidad a depositar (debe ser mayor a 0)
     */
    void depositar(double monto);

    /**
     * Retira dinero de la billetera.
     * @param monto cantidad a retirar (debe ser mayor a 0 y no superar el saldo)
     */
    void retirar(double monto);

    /**
     * Retorna el saldo actual de la billetera.
     * @return saldo disponible
     */
    double obtenerSaldo();
}

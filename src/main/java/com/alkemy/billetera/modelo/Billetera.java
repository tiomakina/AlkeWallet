package com.alkemy.billetera.modelo;

import com.alkemy.billetera.interfaces.IOperacionesBilletera;
import com.alkemy.billetera.servicio.ConvertidorMoneda;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que representa la billetera digital de un usuario.
 * Implementa IOperacionesBilletera para la gestión de fondos.
 * Cada operación impacta directamente sobre el saldo y queda registrada.
 */
public class Billetera implements IOperacionesBilletera {

    private int idBilletera;
    private double saldo;
    private String moneda;
    private Usuario usuario;
    private List<Transaccion> transacciones;
    private int contadorTransacciones;

    /**
     * Constructor de la billetera. El saldo inicial es 0.
     * @param idBilletera identificador único
     * @param usuario     propietario de la billetera
     * @param moneda      moneda de la billetera (ej: "CLP", "USD", "EUR")
     */
    public Billetera(int idBilletera, Usuario usuario, String moneda) {
        this.idBilletera = idBilletera;
        this.usuario = usuario;
        this.moneda = moneda.toUpperCase();
        this.saldo = 0.0;
        this.transacciones = new ArrayList<>();
        this.contadorTransacciones = 1;
    }

    /**
     * Deposita dinero en la billetera e impacta en el saldo actual.
     * Registra una transacción de tipo DEPOSITO.
     * @throws IllegalArgumentException si el monto es menor o igual a 0
     */
    @Override
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0.");
        }
        this.saldo += monto;
        Transaccion t = new Transaccion(
                contadorTransacciones++,
                monto,
                TipoTransaccion.DEPOSITO,
                "Depósito en " + moneda
        );
        transacciones.add(t);
        System.out.printf("Depósito exitoso: +$%.2f %s | Saldo actual: $%.2f %s%n",
                monto, moneda, saldo, moneda);
    }

    /**
     * Retira dinero de la billetera e impacta en el saldo actual.
     * Registra una transacción de tipo RETIRO.
     * @throws IllegalArgumentException si el monto es inválido o el saldo es insuficiente
     */
    @Override
    public void retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a 0.");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException(
                "Saldo insuficiente. Saldo disponible: $" + saldo + " " + moneda);
        }
        this.saldo -= monto;
        Transaccion t = new Transaccion(
                contadorTransacciones++,
                monto,
                TipoTransaccion.RETIRO,
                "Retiro de " + moneda
        );
        transacciones.add(t);
        System.out.printf("Retiro exitoso: -$%.2f %s | Saldo actual: $%.2f %s%n",
                monto, moneda, saldo, moneda);
    }

    /**
     * Convierte el saldo completo de la billetera a otra moneda.
     * Actualiza el saldo y la moneda de la billetera.
     * Registra una transacción de tipo CONVERSION_MONEDA.
     * @param monedaDestino código de la moneda de destino (ej: "USD")
     * @param convertidor   instancia del servicio de conversión
     * @throws IllegalStateException si no hay saldo disponible para convertir
     */
    public void convertirMoneda(String monedaDestino, ConvertidorMoneda convertidor) {
        if (saldo <= 0) {
            throw new IllegalStateException("No hay saldo disponible para convertir.");
        }
        double montoConvertido = convertidor.convertir(saldo, this.moneda, monedaDestino);
        String descripcion = String.format(
                "Conversión $%.2f %s -> $%.2f %s",
                saldo, moneda, montoConvertido, monedaDestino.toUpperCase());

        this.saldo = montoConvertido;
        this.moneda = monedaDestino.toUpperCase();

        Transaccion t = new Transaccion(
                contadorTransacciones++,
                montoConvertido,
                TipoTransaccion.CONVERSION_MONEDA,
                descripcion
        );
        transacciones.add(t);
        System.out.println("Conversión exitosa: " + descripcion);
    }

    /**
     * Retorna el saldo actual de la billetera.
     */
    @Override
    public double obtenerSaldo() {
        return saldo;
    }

    /**
     * Imprime el historial completo de transacciones por consola.
     */
    public void imprimirHistorial() {
        System.out.println("\n--- Historial de transacciones | Billetera #" + idBilletera
                + " | " + usuario.getNombre() + " ---");
        if (transacciones.isEmpty()) {
            System.out.println("Sin transacciones registradas.");
        } else {
            transacciones.forEach(System.out::println);
        }
        System.out.printf("Saldo actual: $%.2f %s%n", saldo, moneda);
        System.out.println("---------------------------------------------------\n");
    }

    // --- Getters y Setters ---

    public int getIdBilletera() { return idBilletera; }
    public String getMoneda() { return moneda; }
    public Usuario getUsuario() { return usuario; }
    public List<Transaccion> getTransacciones() { return transacciones; }

    public void setIdBilletera(int idBilletera) { this.idBilletera = idBilletera; }
    public void setMoneda(String moneda) { this.moneda = moneda.toUpperCase(); }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return String.format("Billetera{id=%d, usuario=%s, saldo=$%.2f %s}",
                idBilletera, usuario.getNombre(), saldo, moneda);
    }
}

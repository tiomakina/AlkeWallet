package com.alkemy.billetera;

import com.alkemy.billetera.modelo.Billetera;
import com.alkemy.billetera.modelo.TipoTransaccion;
import com.alkemy.billetera.modelo.Usuario;
import com.alkemy.billetera.servicio.ConvertidorMoneda;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Billetera y ConvertidorMoneda.
 * Valida depósitos, retiros, conversiones de moneda e historial de transacciones.
 */
@TestMethodOrder(MethodOrderer.DisplayName.class)
class BilleteraTest {

    private Billetera billetera;
    private Usuario usuario;
    private ConvertidorMoneda convertidor;

    /**
     * Configuración previa a cada prueba.
     * Se crea un usuario, una billetera en CLP y una instancia del convertidor.
     */
    @BeforeEach
    void configurar() {
        usuario = new Usuario(1, "Usuario Prueba", "prueba@correo.cl", "clave123");
        billetera = new Billetera(1, usuario, "CLP");
        convertidor = new ConvertidorMoneda();
    }

    // ===================== DEPÓSITO =====================

    @Test
    @DisplayName("Depósito: saldo inicial es cero")
    void saldoInicialEsCero() {
        assertEquals(0.0, billetera.obtenerSaldo(),
                "El saldo inicial debe ser 0");
    }

    @Test
    @DisplayName("Depósito: el saldo aumenta correctamente")
    void depositoAumentaSaldo() {
        billetera.depositar(300000.0);
        assertEquals(300000.0, billetera.obtenerSaldo(),
                "El saldo debe ser $300.000 luego de depositar esa cantidad");
    }

    @Test
    @DisplayName("Depósito: múltiples depósitos se acumulan correctamente")
    void multiplesDepositosSeAcumulan() {
        billetera.depositar(100000.0);
        billetera.depositar(200000.0);
        billetera.depositar(50000.0);
        assertEquals(350000.0, billetera.obtenerSaldo(),
                "El saldo debe ser $350.000 luego de tres depósitos");
    }

    @Test
    @DisplayName("Depósito: monto negativo lanza excepción")
    void depositoMontoNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> billetera.depositar(-50000.0),
                "Depositar un monto negativo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("Depósito: monto cero lanza excepción")
    void depositoMontoCeroLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> billetera.depositar(0.0),
                "Depositar $0 debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("Depósito: registra transacción de tipo DEPOSITO")
    void depositoRegistraTransaccion() {
        billetera.depositar(500000.0);
        assertEquals(1, billetera.getTransacciones().size(),
                "Debe haber una transacción registrada");
        assertEquals(TipoTransaccion.DEPOSITO,
                billetera.getTransacciones().get(0).getTipo(),
                "La transacción debe ser de tipo DEPOSITO");
    }

    // ===================== RETIRO =====================

    @Test
    @DisplayName("Retiro: el saldo disminuye correctamente")
    void retiroDisminuyeSaldo() {
        billetera.depositar(500000.0);
        billetera.retirar(200000.0);
        assertEquals(300000.0, billetera.obtenerSaldo(),
                "El saldo debe ser $300.000 luego de retirar $200.000 de $500.000");
    }

    @Test
    @DisplayName("Retiro: retirar el saldo completo deja saldo en cero")
    void retiroTotalDejaSaldoCero() {
        billetera.depositar(100000.0);
        billetera.retirar(100000.0);
        assertEquals(0.0, billetera.obtenerSaldo(),
                "El saldo debe quedar en 0 luego de retirar todo");
    }

    @Test
    @DisplayName("Retiro: saldo insuficiente lanza excepción")
    void retiroSaldoInsuficienteLanzaExcepcion() {
        billetera.depositar(50000.0);
        assertThrows(IllegalArgumentException.class,
                () -> billetera.retirar(100000.0),
                "Retirar más del saldo disponible debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("Retiro: monto negativo lanza excepción")
    void retiroMontoNegativoLanzaExcepcion() {
        billetera.depositar(100000.0);
        assertThrows(IllegalArgumentException.class,
                () -> billetera.retirar(-30000.0),
                "Retirar un monto negativo debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("Retiro: monto cero lanza excepción")
    void retiroMontoCeroLanzaExcepcion() {
        billetera.depositar(100000.0);
        assertThrows(IllegalArgumentException.class,
                () -> billetera.retirar(0.0),
                "Retirar $0 debe lanzar IllegalArgumentException");
    }

    @Test
    @DisplayName("Retiro: registra transacción de tipo RETIRO")
    void retiroRegistraTransaccion() {
        billetera.depositar(200000.0);
        billetera.retirar(100000.0);
        assertEquals(2, billetera.getTransacciones().size(),
                "Deben registrarse 2 transacciones (depósito + retiro)");
        assertEquals(TipoTransaccion.RETIRO,
                billetera.getTransacciones().get(1).getTipo(),
                "La segunda transacción debe ser de tipo RETIRO");
    }

    // ===================== CONVERSIÓN DE MONEDA =====================

    @Test
    @DisplayName("Conversión: saldo se convierte correctamente de CLP a USD")
    void conversionCLPaUSDCorrecta() {
        billetera.depositar(930.0);   // 930 CLP = 1 USD a tasa predeterminada
        billetera.convertirMoneda("USD", convertidor);
        assertEquals(1.0, billetera.obtenerSaldo(), 0.01,
                "930 CLP deben convertirse a 1 USD según la tasa predeterminada");
        assertEquals("USD", billetera.getMoneda(),
                "La moneda de la billetera debe cambiar a USD");
    }

    @Test
    @DisplayName("Conversión: registra transacción de tipo CONVERSION_MONEDA")
    void conversionRegistraTransaccion() {
        billetera.depositar(100000.0);
        billetera.convertirMoneda("USD", convertidor);
        boolean tieneConversion = billetera.getTransacciones().stream()
                .anyMatch(t -> t.getTipo() == TipoTransaccion.CONVERSION_MONEDA);
        assertTrue(tieneConversion,
                "Debe registrarse una transacción de tipo CONVERSION_MONEDA");
    }

    @Test
    @DisplayName("Conversión: billetera sin saldo lanza excepción")
    void conversionSinSaldoLanzaExcepcion() {
        assertThrows(IllegalStateException.class,
                () -> billetera.convertirMoneda("USD", convertidor),
                "Convertir con saldo 0 debe lanzar IllegalStateException");
    }

    @Test
    @DisplayName("Conversión: moneda de destino no disponible lanza excepción")
    void conversionMonedaNoDisponibleLanzaExcepcion() {
        billetera.depositar(100000.0);
        assertThrows(IllegalArgumentException.class,
                () -> billetera.convertirMoneda("JPY", convertidor),
                "Convertir a una moneda sin tasa registrada debe lanzar IllegalArgumentException");
    }

    // ===================== HISTORIAL =====================

    @Test
    @DisplayName("Historial: vacío en billetera nueva")
    void historialVacioEnBilleteraNueva() {
        assertTrue(billetera.getTransacciones().isEmpty(),
                "El historial debe estar vacío al crear una billetera nueva");
    }

    @Test
    @DisplayName("Historial: registra todas las operaciones en orden")
    void historialRegistraOperacionesEnOrden() {
        billetera.depositar(300000.0);
        billetera.retirar(100000.0);
        billetera.convertirMoneda("USD", convertidor);

        assertEquals(3, billetera.getTransacciones().size(),
                "Deben registrarse exactamente 3 transacciones");
        assertEquals(TipoTransaccion.DEPOSITO,
                billetera.getTransacciones().get(0).getTipo());
        assertEquals(TipoTransaccion.RETIRO,
                billetera.getTransacciones().get(1).getTipo());
        assertEquals(TipoTransaccion.CONVERSION_MONEDA,
                billetera.getTransacciones().get(2).getTipo());
    }

    // ===================== CONVERTIDOR DE MONEDA =====================

    @Test
    @DisplayName("ConvertidorMoneda: tasa CLP-USD correcta")
    void convertidorTasaCLPaUSDCorrecta() {
        double tasa = convertidor.obtenerTasa("CLP", "USD");
        assertEquals(1.0 / 930.0, tasa, 0.000001,
                "La tasa CLP->USD debe ser 1/930");
    }

    @Test
    @DisplayName("ConvertidorMoneda: conversión de monto correcta")
    void convertidorConversionMontoCorrecta() {
        double resultado = convertidor.convertir(930.0, "CLP", "USD");
        assertEquals(1.0, resultado, 0.01,
                "930 CLP deben equivaler a 1 USD");
    }

    @Test
    @DisplayName("ConvertidorMoneda: misma moneda retorna el mismo monto")
    void convertidorMismaMonedaRetornaMismoMonto() {
        double resultado = convertidor.convertir(100000.0, "CLP", "CLP");
        assertEquals(100000.0, resultado,
                "Convertir a la misma moneda debe retornar el mismo monto");
    }

    @Test
    @DisplayName("ConvertidorMoneda: agregar tasa personalizada funciona")
    void convertidorAgregaTasaPersonalizada() {
        convertidor.agregarTasa("CLP", "BRL", 0.005);
        assertEquals(0.005, convertidor.obtenerTasa("CLP", "BRL"),
                "La tasa personalizada CLP->BRL debe ser 0.005");
    }

    @Test
    @DisplayName("ConvertidorMoneda: tasa inválida lanza excepción")
    void convertidorTasaInvalidaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> convertidor.agregarTasa("CLP", "BRL", -1.0),
                "Agregar una tasa negativa debe lanzar IllegalArgumentException");
    }
}

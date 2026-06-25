package com.alkemy.billetera;

import com.alkemy.billetera.modelo.Billetera;
import com.alkemy.billetera.modelo.Usuario;
import com.alkemy.billetera.servicio.ConvertidorMoneda;

import java.util.Scanner;

/**
 * Clase principal - Menú interactivo de AlkeWallet.
 * Permite al usuario crear una cuenta y gestionar su billetera
 * desde la consola ingresando datos manualmente.
 */
public class Principal {

    private static Scanner scanner = new Scanner(System.in);
    private static Billetera billetera = null;
    private static ConvertidorMoneda convertidor = new ConvertidorMoneda();

    public static void main(String[] args) {

        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    crearCuenta();
                    break;
                case 2:
                    verSaldo();
                    break;
                case 3:
                    depositar();
                    break;
                case 4:
                    retirar();
                    break;
                case 5:
                    convertirMoneda();
                    break;
                case 6:
                    verHistorial();
                    break;
                case 0:
                    System.out.println("\n¡Hasta pronto! Gracias por usar AlkeWallet.");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    // ─────────────────────────────────────────
    // MENÚ PRINCIPAL
    // ─────────────────────────────────────────

    private static void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("       AlkeWallet - Menú Principal      ");
        System.out.println("========================================");
        System.out.println("  1. Crear cuenta");
        System.out.println("  2. Ver saldo");
        System.out.println("  3. Depositar dinero");
        System.out.println("  4. Retirar dinero");
        System.out.println("  5. Convertir moneda");
        System.out.println("  6. Ver historial de transacciones");
        System.out.println("  0. Salir");
        System.out.println("========================================");
        System.out.print("  Seleccione una opción: ");
    }

    // ─────────────────────────────────────────
    // OPCIÓN 1: CREAR CUENTA
    // ─────────────────────────────────────────

    private static void crearCuenta() {
        System.out.println("\n--- Crear cuenta ---");

        if (billetera != null) {
            System.out.println("Ya existe una cuenta creada para: "
                    + billetera.getUsuario().getNombre());
            System.out.print("¿Desea crear una nueva cuenta? (s/n): ");
            String respuesta = scanner.nextLine().trim();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine().trim();

        System.out.print("Ingrese una contraseña: ");
        String contrasena = scanner.nextLine().trim();

        System.out.println("Seleccione la moneda de su billetera:");
        System.out.println("  1. CLP - Peso chileno");
        System.out.println("  2. USD - Dólar estadounidense");
        System.out.println("  3. EUR - Euro");
        System.out.print("  Opción: ");
        int opMoneda = leerOpcion();

        String moneda;
        switch (opMoneda) {
            case 2:  moneda = "USD"; break;
            case 3:  moneda = "EUR"; break;
            default: moneda = "CLP"; break;
        }

        Usuario usuario = new Usuario(1, nombre, correo, contrasena);
        billetera = new Billetera(101, usuario, moneda);

        System.out.println("\n✔ Cuenta creada exitosamente.");
        System.out.println("  Titular : " + nombre);
        System.out.println("  Correo  : " + correo);
        System.out.println("  Moneda  : " + moneda);
        System.out.println("  Saldo   : $0.00 " + moneda);
    }

    // ─────────────────────────────────────────
    // OPCIÓN 2: VER SALDO
    // ─────────────────────────────────────────

    private static void verSaldo() {
        System.out.println("\n--- Ver saldo ---");
        if (!cuentaExiste()) return;

        System.out.println("  Titular : " + billetera.getUsuario().getNombre());
        System.out.printf("  Saldo   : $%.2f %s%n",
                billetera.obtenerSaldo(), billetera.getMoneda());
    }

    // ─────────────────────────────────────────
    // OPCIÓN 3: DEPOSITAR
    // ─────────────────────────────────────────

    private static void depositar() {
        System.out.println("\n--- Depositar dinero ---");
        if (!cuentaExiste()) return;

        System.out.printf("  Saldo actual: $%.2f %s%n",
                billetera.obtenerSaldo(), billetera.getMoneda());
        System.out.print("  Ingrese el monto a depositar: $");

        try {
            double monto = Double.parseDouble(scanner.nextLine().trim());
            billetera.depositar(monto);
        } catch (NumberFormatException e) {
            System.out.println("  Error: ingrese un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // OPCIÓN 4: RETIRAR
    // ─────────────────────────────────────────

    private static void retirar() {
        System.out.println("\n--- Retirar dinero ---");
        if (!cuentaExiste()) return;

        System.out.printf("  Saldo actual: $%.2f %s%n",
                billetera.obtenerSaldo(), billetera.getMoneda());
        System.out.print("  Ingrese el monto a retirar: $");

        try {
            double monto = Double.parseDouble(scanner.nextLine().trim());
            billetera.retirar(monto);
        } catch (NumberFormatException e) {
            System.out.println("  Error: ingrese un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // OPCIÓN 5: CONVERTIR MONEDA
    // ─────────────────────────────────────────

    private static void convertirMoneda() {
        System.out.println("\n--- Convertir moneda ---");
        if (!cuentaExiste()) return;

        if (billetera.obtenerSaldo() <= 0) {
            System.out.println("  No tiene saldo disponible para convertir.");
            return;
        }

        System.out.printf("  Saldo actual: $%.2f %s%n",
                billetera.obtenerSaldo(), billetera.getMoneda());
        System.out.println("  Seleccione la moneda de destino:");
        System.out.println("  1. CLP - Peso chileno");
        System.out.println("  2. USD - Dólar estadounidense");
        System.out.println("  3. EUR - Euro");
        System.out.println("  4. UF  - Unidad de Fomento");
        System.out.print("  Opción: ");

        int opcion = leerOpcion();
        String destino;
        switch (opcion) {
            case 1:  destino = "CLP"; break;
            case 2:  destino = "USD"; break;
            case 3:  destino = "EUR"; break;
            case 4:  destino = "UF";  break;
            default:
                System.out.println("  Opción no válida.");
                return;
        }

        if (destino.equals(billetera.getMoneda())) {
            System.out.println("  La billetera ya está en " + destino + ".");
            return;
        }

        try {
            billetera.convertirMoneda(destino, convertidor);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // OPCIÓN 6: VER HISTORIAL
    // ─────────────────────────────────────────

    private static void verHistorial() {
        System.out.println("\n--- Historial de transacciones ---");
        if (!cuentaExiste()) return;
        billetera.imprimirHistorial();
    }

    // ─────────────────────────────────────────
    // UTILIDADES
    // ─────────────────────────────────────────

    /**
     * Verifica que exista una cuenta creada antes de operar.
     */
    private static boolean cuentaExiste() {
        if (billetera == null) {
            System.out.println("  No existe una cuenta. Seleccione la opción 1 para crear una.");
            return false;
        }
        return true;
    }

    /**
     * Lee un número entero desde la consola de forma segura.
     */
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
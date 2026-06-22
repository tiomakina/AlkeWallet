package com.alkemy.billetera;

import com.alkemy.billetera.modelo.Billetera;
import com.alkemy.billetera.modelo.Usuario;
import com.alkemy.billetera.servicio.ConvertidorMoneda;

/**
 * Clase principal - demostración funcional de AlkeWallet.
 * Simula el uso de la billetera digital con usuarios chilenos
 * y operaciones en pesos chilenos (CLP).
 */
public class Principal {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("      AlkeWallet - Demostración         ");
        System.out.println("========================================\n");

        // 1. Crear usuario y billetera en pesos chilenos (CLP)
        Usuario usuario = new Usuario(1, "Valentina Rojas", "valentina@correo.cl", "clave123");
        Billetera billetera = new Billetera(101, usuario, "CLP");
        System.out.println("Usuario creado: " + usuario);
        System.out.println("Billetera creada: " + billetera + "\n");

        // 2. Realizar depósitos
        billetera.depositar(500000.00);   // $500.000 CLP
        billetera.depositar(250000.00);   // $250.000 CLP

        // 3. Realizar retiro
        billetera.retirar(100000.00);     // -$100.000 CLP

        System.out.printf("%nSaldo luego de operaciones: $%.2f %s%n",
                billetera.obtenerSaldo(), billetera.getMoneda());

        // 4. Convertir saldo de CLP a USD
        ConvertidorMoneda convertidor = new ConvertidorMoneda();
        System.out.println("\nConvirtiendo saldo de CLP a USD...");
        billetera.convertirMoneda("USD", convertidor);

        // 5. Imprimir historial completo
        billetera.imprimirHistorial();

        // 6. Segundo usuario con billetera en USD que convierte a CLP
        System.out.println("--- Segunda billetera: USD -> CLP ---");
        Usuario usuario2 = new Usuario(2, "Sebastián Morales", "sebastian@correo.cl", "clave456");
        Billetera billetera2 = new Billetera(102, usuario2, "USD");
        billetera2.depositar(1000.00);
        billetera2.retirar(200.00);
        billetera2.convertirMoneda("CLP", convertidor);
        billetera2.imprimirHistorial();
    }
}

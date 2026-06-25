# AlkeWallet 💰

Billetera digital desarrollada en Java con paradigma orientado a objetos.  
Proyecto de evaluación — Módulo 2 Alkemy | Chile.

---

## Estructura del proyecto

```
AlkeWallet/
├── pom.xml
└── src/
    ├── main/java/com/alkemy/billetera/
    │   ├── Principal.java                            ← Menú interactivo (punto de entrada)
    │   ├── interfaces/
    │   │   ├── IOperacionesBilletera.java            ← Contrato de operaciones de fondos
    │   │   └── IConvertidorMoneda.java               ← Contrato de conversión de monedas
    │   ├── modelo/
    │   │   ├── Usuario.java                          ← Entidad usuario
    │   │   ├── Billetera.java                        ← Lógica principal de la billetera
    │   │   ├── Transaccion.java                      ← Registro de transacciones
    │   │   └── TipoTransaccion.java                  ← Enum: DEPOSITO, RETIRO, CONVERSION
    │   └── servicio/
    │       └── ConvertidorMoneda.java                ← Servicio de conversión
    └── test/java/com/alkemy/billetera/
        └── BilleteraTest.java                        ← 23 pruebas unitarias JUnit 5
```

---

## Funcionalidades implementadas

### Menú interactivo por consola
Al ejecutar el programa el usuario verá:

```
========================================
       AlkeWallet - Menú Principal
========================================
  1. Crear cuenta
  2. Ver saldo
  3. Depositar dinero
  4. Retirar dinero
  5. Convertir moneda
  6. Ver historial de transacciones
  0. Salir
========================================
  Seleccione una opción:
```

### Administración de fondos
- Crear cuenta con nombre, correo y moneda
- Ver saldo disponible en tiempo real
- Depositar dinero — impacta directamente sobre el saldo
- Retirar dinero — impacta directamente sobre el saldo
- Ver historial completo de transacciones

### Conversión de moneda
- Convertir saldo entre CLP, USD, EUR y UF
- Tasas actualizables en tiempo de ejecución

---

## Tecnologías

| Tecnología  | Versión | Uso                              |
|-------------|---------|----------------------------------|
| Java        | 17 LTS  | Paradigma orientado a objetos    |
| JUnit 5     | 5.10.0  | Pruebas unitarias (23 pruebas)   |
| Maven       | 3.8+    | Gestión de dependencias y build  |
| Eclipse IDE | 2024+   | Entorno de desarrollo            |

---

## Cómo ejecutar

### Prerrequisitos
- Java 17+
- Maven 3.8+

### Desde Eclipse
1. `File` → `Import` → `Maven` → `Existing Maven Projects`
2. Seleccionar la carpeta `AlkeWallet`
3. Clic derecho en `Principal.java` → `Run As` → `Java Application`
4. Interactuar con el menú en la consola

### Desde terminal
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.alkemy.billetera.Principal"
```

### Ejecutar pruebas unitarias
```bash
mvn test
```

---

## Interfaces utilizadas

- `IOperacionesBilletera` → define `depositar()`, `retirar()`, `obtenerSaldo()` — implementada por `Billetera`
- `IConvertidorMoneda` → define `convertir()` y `obtenerTasa()` — implementada por `ConvertidorMoneda`

---

## Monedas soportadas

| Desde | Hacia | Tasa referencial |
|-------|-------|-----------------|
| CLP   | USD   | 1 / 930         |
| USD   | CLP   | 930.0           |
| CLP   | EUR   | 1 / 1010        |
| EUR   | CLP   | 1010.0          |
| CLP   | UF    | 1 / 37200       |
| UF    | CLP   | 37200.0         |
| USD   | EUR   | 0.92            |

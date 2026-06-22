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
    │   ├── Principal.java                            ← Punto de entrada
    │   ├── interfaces/
    │   │   ├── IOperacionesBilletera.java            ← Operaciones de fondos
    │   │   └── IConvertidorMoneda.java               ← Conversión de monedas
    │   ├── modelo/
    │   │   ├── Usuario.java                          ← Entidad usuario
    │   │   ├── Billetera.java                        ← Billetera (lógica principal)
    │   │   ├── Transaccion.java                      ← Registro de transacciones
    │   │   └── TipoTransaccion.java                  ← Enum: DEPOSITO, RETIRO, CONVERSION
    │   └── servicio/
    │       └── ConvertidorMoneda.java                ← Servicio de conversión
    └── test/java/com/alkemy/billetera/
        └── BilleteraTest.java                        ← Pruebas unitarias JUnit 5
```

---

## Funcionalidades implementadas

### Administración de fondos
- Crear cuenta (usuario + billetera)
- Ver saldo disponible (`obtenerSaldo()`)
- Depositar dinero (`depositar(monto)`) — impacta sobre el saldo
- Retirar dinero (`retirar(monto)`) — impacta sobre el saldo
- Historial de transacciones (`imprimirHistorial()`)

### Conversión de moneda
- Convertir el saldo de una moneda a otra (`convertirMoneda(destino, convertidor)`)
- Tasas disponibles por defecto: CLP ↔ USD, CLP ↔ EUR, CLP ↔ UF, USD ↔ EUR, USD ↔ ARS
- Agregar tasas personalizadas (`agregarTasa(origen, destino, tasa)`)

---

## Tecnologías

| Tecnología  | Detalle                          |
|-------------|----------------------------------|
| Java 17     | Paradigma orientado a objetos    |
| JUnit 5     | Pruebas unitarias                |
| Maven       | Gestión de dependencias y build  |

---

## Cómo ejecutar

### Prerrequisitos
- Java 17+
- Maven 3.8+

### Compilar y ejecutar
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.alkemy.billetera.Principal"
```

### Ejecutar pruebas unitarias
```bash
mvn test
```

### Empaquetar
```bash
mvn package
java -jar target/alke-wallet-1.0.0.jar
```

---

## Pruebas unitarias — resumen

| Clase testeada       | Casos cubiertos                                        |
|----------------------|--------------------------------------------------------|
| `Billetera`          | Depósito, retiro, saldo, transacciones, excepciones    |
| `ConvertidorMoneda`  | Tasas, conversión, misma moneda, tasas personalizadas  |

**Total: 20 pruebas unitarias**

---

## Interfaces utilizadas

- `IOperacionesBilletera` — define `depositar()`, `retirar()`, `obtenerSaldo()`, implementada por `Billetera`
- `IConvertidorMoneda` — define `convertir()` y `obtenerTasa()`, implementada por `ConvertidorMoneda`

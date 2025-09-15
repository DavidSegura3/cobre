# Uso de la Inteligencia Artificial en el Proyecto
Este documento detalla el uso de herramientas de inteligencia artificial para mejorar la 
productividad y la calidad del código durante el desarrollo del Módulo de Cuentas CBMM, 
de acuerdo con las instrucciones del desafío.

### 1. **Casos de uso**
   - La IA fue empleada en las siguientes áreas del proyecto:
     - Generación de Archivos de Eventos de Entrada: Se utilizó la IA para generar el archivo de ejemplo JSON, que representa la estructura de datos necesaria para las pruebas. Esto cumplió con el requerimiento de "Input Event Files" al crear datos estructurados de manera eficiente para el cálculo del saldo final y el listado de transacciones.

Prompt de ejemplo:

```
   "Generate a JSON array of financial events with the following structure: 'event_id', 'event_type', 'operation_date', 'origin' object with 'account_id', 'currency', 'amount', and a 'destination' object with the same fields. Ensure the event type is 'cross_border_money_movement'."
```
#### 2. Creación de la Estrategia de Pruebas 
   - La IA ayudó a planificar y estructurar las pruebas unitarias y de integración. 
   - Proporcionó un enfoque claro para validar la arquitectura hexagonal y sugirió el uso de Mockito para las pruebas unitarias y Testcontainers para las de integración, simplificando la configuración del entorno de prueba.

Prompt de ejemplo:
```
"Help me design a testing strategy for a Spring Boot application using hexagonal architecture. Provide code examples for unit tests on the application layer and integration tests that use Testcontainers with Kafka and a database."
```

### 3. **Redacción y Documentación:**
   - La IA se utilizó para redactar y estructurar el README.md del proyecto y este mismo documento. Esto aseguró que la documentación fuera clara, completa y profesional.

### 4. **Depuración de Errores:** 
   - Se utilizaron los mensajes de error de la consola de Java, como las excepciones de Hibernate, para obtener explicaciones detalladas y soluciones. 
   - La IA fue capaz de identificar la causa raíz de problemas complejos, como el error de constructor por defecto, y sugerir correcciones precisas.


### 5. **Ética y responsabilidad**

```
   El uso de la IA se mantuvo de forma responsable, complementando el trabajo humano y no sustituyéndolo. 
   La gran parte del código fue generado, revisado y validado por el mi,  para garantizar la calidad, seguridad 
   y el cumplimiento de los estándares del proyecto. La IA actuó como un copiloto, acelerando las tareas rutinarias 
   y proporcionando nuevas perspectivas en la resolución de problemas.
```
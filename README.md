# ChallengeAlMundoCallCenter

##Qué pasa cuando no hay ningún empleado libre para atender la llamada?
Se envia la llamada a una cola que en orden FIFO será atendida cuando se libere un empleado. Implementada como ConcurrentLinkedQueue ya que es una cola thread-safe que mantiene el orden deseado.

##Qué pasa con una llamada cuando entran más de 10 llamadas concurrentes?
Suponiendo que hay empleados disponibles para atender la llamada, ya que fue respondido en el punto 1, la llamada esperará un hilo para ser ejecutada. Esto sucede por la implementación del FixedThreadPool

##Herramientas
-Java 8: porque use expresiones lambdas y estructuras de datos complejas.
-Junit 5: utilizado para los test unitarios.
-Lombock: Para evitar crear código irrevelante y usar los métodos getters y setters. 
-Slf4j: Utilizado para el manejo de logs.
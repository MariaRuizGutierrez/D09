Con este fichero queremos aclarar las decisiones que hemos tomado sobre el proyecto Acme Rendezvous.


1. El requisito número 18 de Acme-Rendezvous 1.0, lo hemos interpretado como que solo los user's que van a asistir a la rendezvous podrán contestar las respuestas a las preguntas de esa rendezvous cuando ellos vean oportuno responderlas(no responder obligatoriamente), esto ha sido dado por bueno, lo volvemos a recalcar.

2. Un servicio se puede crear sin categorias y posteriormente se le podra añadir una. Un user no podra solicitar un servicio hasta que este tenga una categoria.

3. Tanto la foto como los mensajes de bienvenida estan puestos en todas nuestras paginas del sistema, por otro lado, los mensajes estan internacionalizados, sin embargo el nombre de la empresa al ser solo un atributo siempre sera el mismo.

4. Una solicitud no se puede borrar ya que en los requisitos no nos lo especifican y hemos tomado la decision de no poder borrarla.

5. El requisito funcional numero 7, click y pinterest son ejemplos(es decir, que no son esas unicas URL que se van a guardar en la base de datos).

6. No se especifico en la entrega pasado en el README que: Cuando un user crea una rendezvous en modo borrador, hasta que no la ponga a modo final no asistira a ella ya que cuando es modo borrador no es "oficial". 

7. Cuando la rendezvous ha pasado ya no se pueden crear Announcements, questions, solicitar servicios, ni clicar similares... Ademas sale la columna de la fecha en roja para que se vea que esta en pasado.

9. Solo cuando la rendezvous está en modo borrador se podran solicitar servicios(dicho por nuestro profesor de seguimiento)

10. Si una categoria ya tiene asociado un servicio, este ya no se puede editar.

11. Una pregunta se puede contestar varias veces por la misma persona.

12. En la query cuando "Top Selling services" hemos elegido que sean 5.


Todas estas decisiones se le comentaron a nuestro profesor de seguimiento, que en este caso ha sido Carlos Muller, y nos ha dado el visto bueno.

PD: Los mockups en la D08, se nos olvidaron entregarlos(hemos realizado esta misma entrega 3 veces dado que hubo problemas con EV y se nos olvidó ponerlo en la última aunque estaban hechos) y los entregamos en la D09.

Además estamos muy orgullosos del test "driverListEdit" de la clase RendezvousServiceTest que es donde aparecen los 10 tests como se piden en los Statements, tambien estamos orgullosos del "driverCreateAndSave" de la clase RequestServiceTest.
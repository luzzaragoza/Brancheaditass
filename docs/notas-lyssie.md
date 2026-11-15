# Notas de Lyssie

Lo que hice en mis tareas y lo que sirve para la defensa.

## 1. Contenedor: de array a ArrayList (Sprint 4)

Pull requests #12 (ArrayList y constructores con `this`) y #21 (`getLugares()`).

| | Con array y contador | Con `ArrayList<Quimico>` |
|---|---|---|
| `sacar()` | 14 líneas. 5 eran para correr los de atrás a mano: el `for`, `cantidad--` y poner `null` en el último lugar. | 13 líneas. Correr es una sola: `quimicos.remove(posicion)`. 3 de las 13 son el control nuevo de casilla vacía; sin él serían 10. |
| `vaciar()` | 6 líneas: un `for` que pone `null` y `cantidad = 0`. | 3 líneas: `quimicos.clear()`. |
| Cantidad | Un atributo `cantidad` que había que mantener a mano en cada método. | No existe: es `quimicos.size()`. |
| Guardar | `quimicos[cantidad] = q; cantidad++;` | `quimicos.add(q);` |
| Leer | `quimicos[i]` | `quimicos.get(i)` |

Los dos constructores quedaron unidos con `this(...)`: `Contenedor(codigo)` llama a `this(codigo, LUGARES)`. La cantidad de lugares está en un solo lugar del código: `LUGARES` en `Contenedor`.

Las cinco pruebas (`PruebaCompatibilidad`, `PruebaDeposito`, `PruebaJuego`, `PruebaMotivos` y `PruebaQuimicos`) dieron igual antes y después del cambio. Para las que usan azar, las corrí con el azar fijo (10 semillas) con el Contenedor viejo y con el nuevo.

## 2. Simulacro del quinto tipo (Sprint 6)

Inventamos un químico `Toxico` (no puede ir al lado de otro `Toxico`; con los demás, sí) en una rama aparte que después se borró. Crear la clase nueva compila sin tocar nada más, pero para que funcione bien hubo que modificar cuatro clases existentes:

| Clase | Por qué |
|---|---|
| `Juego` | `crearQuimico(tipo, ...)` solo conoce los tipos 0 a 3, y los `4` de los `for` y del sorteo son la cantidad de tipos. Sin tocarlo, el químico nuevo nunca aparece en un pedido. |
| `Inflamable` y `Oxidante` | Dicen con quién **sí** se llevan (`otro instanceof Acido \|\| otro instanceof Base`), así que el nuevo les explota. Además queda desparejo: Inflamable y después Tóxico explota, pero Tóxico y después Inflamable no. `Acido` y `Base` dicen con quién **no** se llevan, y esas no hubo que tocarlas. |
| `Colores` | Elige el color con `instanceof`. Sin tocarla, el químico nuevo sale color crema. |

**Respuesta para la defensa:** con el diseño actual, agregar un tipo obliga a tocar `Juego`, `Inflamable`, `Oxidante` y `Colores`. Para que solo haya que tocar `Juego` (que es el que decide qué pedidos aparecen), habría que escribir `Inflamable` y `Oxidante` como `Acido` y `Base` (diciendo con quién no se llevan) y que cada químico diga su color, como ya dice su imagen con `getImagen()`.

## 3. Balanceo (Sprints 4 y 5)

La planilla compartida («Balanceo Pocionella») quedó sin partidas anotadas. Para tener un dato, corrí partidas **simuladas** con el `Juego` real y un jugador automático que juega perfecto: nunca se equivoca de temperatura ni de orden, retira sin dejar pegados incompatibles y canjea apenas un contenedor se llena. **No son partidas de personas**: una persona se equivoca más, así que pierde antes.

| Jugador automático | Turno promedio | Mínimo | Máximo | Antes del turno 10 | Cómo pierde |
|---|---|---|---|---|---|
| Canjea apenas se llena (400 partidas) | 33 | 5 | 99 | 14 de 400 (3,5 %) | Casi siempre «¡NO LO TENÍAS!» |
| No canjea nunca (400 partidas) | 65 | 31 | 129 | 0 de 400 | Siempre «¡NO ENTRA!» (no queda lugar) |

Con la regla del plan (ajustar si la mayoría pierde antes del turno 10), **no hace falta ajustar**: hasta jugando perfecto el canje tiene su riesgo («no lo tenías»), y sin canjear el depósito se llena. Los números son con `LUGARES = 4` y `PUNTOS_CANJE = 40`, después del balanceo de retiros del pull request #19.

## 4. Prueba final de punta a punta (Sprint 7)

Armé el ejecutable (`Pocionella.jar`, con `Main-Class: VentanaJuego` y las carpetas `img` y `fuentes` adentro) desde `main` del 15/11 y lo corrí desde una carpeta donde solo estaba el `.jar`, en una computadora que no es la de desarrollo.

Partida completa: 13 químicos dejados, 6 retirados, 1 canje (+40 puntos), derrota en el turno 19 por «¡NO LO TENÍAS!», iniciales guardadas en el top 5 y «Jugar de nuevo» volvió a la pantalla de inicio. No saltó ninguna excepción. Las imágenes y las fuentes cargan desde adentro del `.jar`.

Lo que encontré:

- **El estante lleno sigue siendo derrota.** Guardar en un contenedor lleno termina la partida con «¡NO ENTRA!». La decisión del 27/10 era que fuera un aviso y se eligiera otro estante.
- **No se puede elegir la encargada.** La pantalla de inicio no lo pregunta y `Juego()` siempre arranca con «Sofi 1», así que no se puede jugar «una partida atendiendo cada una de las cuatro».
- **La fuente pixelada confunde algunos caracteres.** En mi prueba, el «TOP 5» de la pantalla final se ve como «TOP 8», «H2» y «E2» se ven como «H8» y «E8», y «sacás» se ve como «saoás». Revisarlo en otra computadora.
- **La pantalla final tiene fondo gris claro y botones comunes**, distinto del resto del juego, que usa la paleta del boceto.

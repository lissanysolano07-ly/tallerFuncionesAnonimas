# Trabajo de consulta: Funciones anónimas

**Materia:** Programación Funcional · **Entrega:** semana 5 · **Autoría:** trabajo individual

Este repositorio desarrolla las cinco partes de la guía de consulta. Los ejemplos utilizan diez productos inventados de una tienda; no representan datos reales.

## Parte 1. Programación funcional en el mundo real

**Caso 1: Apache Spark.** Spark permite procesar grandes volúmenes de información distribuyendo tareas entre equipos. Ofrece APIs en Scala, Java y Python, y operaciones como `filter` y `map`, que permiten expresar transformaciones sobre conjuntos de datos sin escribir manualmente los bucles de procesamiento distribuido. Por ejemplo, en Scala `datos.filter(x => x > 10)` conserva los valores mayores que diez. La ventaja es expresar una regla de transformación de forma breve y reutilizable. **Fuente:** [Apache Spark: RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html).

**Caso 2: pandas (Python).** pandas se usa para limpiar, transformar y analizar tablas de datos, por ejemplo, registros de ventas. Su método `DataFrame.assign` permite construir columnas con funciones que reciben el DataFrame; `df.assign(total=lambda tabla: tabla.precio * tabla.cantidad)` calcula el total por fila. La ventaja es describir cálculos sobre columnas sin recorrer filas una por una. **Fuente:** [pandas.DataFrame.assign](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.assign.html). Estos fragmentos son ejemplos ilustrativos basados en las APIs documentadas, no copias de repositorios.

## Parte 2. ¿Qué es una función anónima?

Una **función anónima** es una función que puede escribirse sin asignarle un nombre. La palabra *lambda* viene del cálculo lambda, un sistema matemático para representar funciones y aplicaciones de funciones. En Scala, `x => x * 2` recibe un número y devuelve su doble.

Una **función nombrada** tiene un identificador: `def doble(x: Int): Int = x * 2`. La misma lógica como función anónima es `val dobleLambda = (x: Int) => x * 2`. Las dos producen `6` al recibir `3`.

Las funciones son **ciudadanos de primera clase** cuando pueden guardarse en variables, pasarse como argumentos y devolverse desde otras funciones. Por eso `List(1, 2, 3).map(x => x * 2)` puede recibir la lambda y devolver `List(2, 4, 6)`. En `filter`, la lambda debe devolver un valor booleano.

Un **closure** es una función que utiliza variables de su entorno exterior. Ejemplo: `val impuesto = 0.15; val total = (precio: Double) => precio * (1 + impuesto)`. La función utiliza `impuesto` aunque no sea un parámetro suyo.

## Parte 3. Tipos de problemas

Se resolvieron los **siete tipos** propuestos, con los mismos diez productos en Scala, Java y Python. Cada caso se ejecuta en los tres lenguajes. Archivos: [`scala/Parte3.scala`](scala/Parte3.scala), [`java/Parte3.java`](java/Parte3.java) y [`python/parte3.py`](python/parte3.py).

| Tipo | Situación real | Operación y criterio | Resultado esperado en los tres lenguajes |
|---|---|---|---|
| Selección | Mostrar artículos que cuestan más de $50 | `filter`: precio > 50 | Laptop, Monitor, Audifonos |
| Transformación | Preparar nombres para un catálogo en mayúsculas | `map`: nombre en mayúsculas | LAPTOP, MOUSE, TECLADO, MONITOR, CUADERNO, LAPIZ, MOCHILA, AUDIFONOS, BOTELLA, AGENDA |
| Validación | Comprobar si hay artículos agotados y si todos tienen precio positivo | `exists`/`forall`, `anyMatch`/`allMatch`, `any`/`all` | falso, verdadero |
| Conteo | Saber cuántos artículos tecnológicos se ofrecen | `count` o equivalente | 5 |
| Extremos | Detectar el artículo más caro y el más barato | `maxBy`/`minBy` o equivalente | Laptop, Lapiz |
| Aplanamiento | Unir palabras de nombres en una sola lista | `flatMap` o equivalente | 10 palabras, una por producto |
| Acumulación | Calcular el valor monetario total de las existencias | `foldLeft` o `reduce` | 12235 |

Las lambdas concretas y las salidas impresas están en los archivos de código. Las versiones usan `filter`, `map`, `exists`/`anyMatch`/`any`, `count`, `maxBy`/`max`, `flatMap` y `foldLeft`/`reduce` según las herramientas disponibles en cada lenguaje. **Nota:** en Java, `reduce` combina resultados parciales mediante `Integer::sum`.

## Parte 4. Cuatro operaciones nuevas

Archivo: [`operaciones/Parte4.scala`](operaciones/Parte4.scala). Todas las operaciones usan los productos anteriores.

| Operación | Qué hace y lambda que recibe | Problema real y tipo relacionado | Salida esperada |
|---|---|---|---|
| `sortBy` | Ordena por la clave que devuelve `p => p.precio` (Int) | Ordenar catálogo por precio; ordenación, tipo nuevo | Lapiz, Cuaderno, Agenda, Botella, Mouse, Mochila, Teclado, Audifonos, Monitor, Laptop |
| `groupBy` | Agrupa por la clave `p => p.categoria` (String) | Organizar artículos por departamento; agrupación, tipo nuevo | Tecnologia: 5; Papeleria: 3; Accesorios: 2 |
| `partition` | Separa según `p => p.stock > 0` (Boolean) | Separar disponibles y agotados; selección | 10 disponibles y 0 agotados |
| `find` | Devuelve el primer elemento que satisface `p => p.precio > 500` (Boolean) | Localizar un producto costoso; selección | Some(Laptop) |

**Combinación (4.3).** Pregunta: ¿Qué categorías tienen más de dos productos, ordenadas de mayor a menor por cantidad? Se usa una sola cadena `groupBy` → `filter` → `sortBy`: primero se agrupan productos por categoría, después se conservan grupos con más de dos productos y por último se ordenan descendentemente según su tamaño. Resultado: **Tecnologia: 5, Papeleria: 3**.

## Parte 5. Comparación y conclusión

| Criterio | Scala | Java | Python |
|---|---|---|---|
| Sintaxis de lambda | `p => p.precio > 50` | `p -> p.precio() > 50` | `lambda p: p.precio > 50` |
| Cómo se declara el tipo | Tipado estático, frecuentemente inferido | Tipado estático e inferencia contextual en Streams | Tipado dinámico; anotaciones opcionales |
| Operaciones con listas | `map`, `filter`, `count`, `maxBy`, `flatMap`, `foldLeft` | Streams: `map`, `filter`, `count`, `max`, `flatMap`, `reduce` | `map`, `filter`, `max(key=...)`, `reduce`, comprensiones |
| Restricción del lenguaje | Se necesita una versión compatible de Scala y cuidar tipos | Las variables locales capturadas por lambdas deben ser finales o efectivamente finales | Las lambdas contienen una expresión, no varias instrucciones |
| Legibilidad (opinión) | Muy clara para encadenar colecciones | Más extensa, pero explícita | Breve, aunque lambdas complejas pueden resultar difíciles |

**¿En qué lenguaje fue más natural?** En estos ejemplos, Scala resultó más natural porque sus colecciones incluyen directamente operaciones como `count`, `maxBy` y `foldLeft`. Python fue muy sencillo en filtros y transformaciones. Java necesitó más estructura y Streams.

**¿Dónde hubo más diferencias?** En aplanamiento y acumulación: Scala ofrece `flatMap` y `foldLeft` directamente, Java usa Streams y reducción con combinador, mientras Python requiere `reduce` o construcciones equivalentes. La lógica es parecida, pero cambia la forma de expresarla.

**Conclusión.** Las funciones anónimas permiten indicar qué transformación, condición o cálculo se desea aplicar a los elementos de una lista sin crear una función con nombre para cada paso. Esto reduce código repetido y hace más visibles las reglas del problema. En los ejemplos de inventario, la misma idea resolvió selección, validación, conteo y acumulación en tres lenguajes.

Su utilidad también aparece en herramientas reales como Apache Spark: las funciones que se pasan a operaciones de transformación permiten describir qué hacer con grandes colecciones de datos. Aunque la sintaxis varía entre Scala, Java y Python, el concepto de pasar funciones como valores es común a los tres.

## Cómo ejecutar

- **Python 3.10+:** desde la raíz, `python python/parte3.py`.
- **Java 17+:** `cd java`, después `javac Parte3.java` y `java Parte3`.
- **Scala 3 con Scala CLI:** `scala scala/Parte3.scala` para Parte 3; para Parte 4, ejecutar `scala scala/Parte3.scala operaciones/Parte4.scala` si la herramienta admite varios archivos en el mismo proyecto. Alternativamente, abrir los dos archivos en una sesión de Scala y ejecutar primero Parte 3.

En [`img/SALIDAS_EJEMPLO.txt`](img/SALIDAS_EJEMPLO.txt) se incluye una salida verificada de Python. **Las capturas de pantalla reales deben realizarse al ejecutar cada programa en tu equipo** y guardarse en `img/` antes de entregar; no se presentan capturas inventadas.

## Fuentes y uso de IA

- [Apache Spark, RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html)
- [pandas.DataFrame.assign](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.assign.html)
- [Scala Collections](https://docs.scala-lang.org/overviews/collections-2.13/overview.html)
- [Oracle, Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
- [Python, Functional Programming HOWTO](https://docs.python.org/3/howto/functional.html)
- **Herramienta de IA:** ChatGPT (OpenAI), utilizado como apoyo para la redacción, organización del repositorio y elaboración de ejemplos de código. Los ejemplos deben revisarse y probarse por la persona que entrega.

# Trabajo de consulta: Funciones anónimas

**Materia:** Programación Funcional
**Entrega:** semana 5 
**Autoría:** trabajo individual

## Parte 1 – Explorar: la programación funcional en el mundo real

### Caso 1: Apache Spark

**Problema:** Procesar y analizar grandes conjuntos de datos distribuidos entre varias máquinas.

**Herramienta:** Apache Spark, utilizando el lenguaje Scala.

**Ventaja:** Permite utilizar funciones anónimas con operaciones como `filter` y `map` para seleccionar y transformar datos de manera sencilla, sin necesidad de escribir bucles complejos.

**Ejemplo propio basado en la API:**

```scala
val numeros = spark.sparkContext.parallelize(Seq(1, 2, 3, 4, 5))
val paresDobles = numeros.filter(n => n % 2 == 0).map(n => n * 2)
println(paresDobles.collect().mkString(", "))
```

**Salida:**
```text
4, 8
```

**Explicación:** La función anónima de `filter` selecciona los números pares y la de `map` multiplica cada número seleccionado por 2. Apache Spark permite realizar estas operaciones sobre datos distribuidos. Este ejemplo requiere un entorno configurado con Apache Spark.

**Fuente:** [Documentación oficial de Apache Spark: RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html).

---

### Caso 2: pandas (Python)

**Problema:** Analizar registros de ventas y calcular automáticamente el valor total de los productos vendidos.

**Herramienta:** pandas, una biblioteca de Python utilizada para analizar y manipular datos.

**Ventaja:** Permite utilizar funciones anónimas con `lambda` para realizar cálculos y crear nuevas columnas sin necesidad de recorrer manualmente cada fila.

**Ejemplo propio basado en la API:**

```python
import pandas as pd

ventas = pd.DataFrame({
    "producto": ["Laptop", "Mouse", "Teclado"],
    "precio": [850, 20, 45],
    "cantidad": [2, 5, 3]
})

resultado = ventas.assign(
    total=lambda tabla: tabla.precio * tabla.cantidad
)

print(resultado)
```

**Salida:**
```text
  producto  precio  cantidad  total
0   Laptop     850         2   1700
1    Mouse      20         5    100
2  Teclado      45         3    135
```

**Explicación:** La función anónima `lambda` recibe la tabla de ventas y multiplica el precio por la cantidad de cada producto. La operación `assign` crea una nueva columna llamada `total` con los resultados, facilitando el análisis de las ventas.

**Fuente:** [Documentación oficial de pandas: DataFrame.assign](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.assign.html).

**Nota:** Ambos ejemplos son ilustrativos y están basados en las herramientas documentadas oficialmente.

## Parte 2 – Entender: ¿qué es una función anónima?

## ¿Qué es una función anónima y de dónde viene el nombre lambda? 
Una **función anónima** es una función que puede escribirse sin asignarle un nombre. La palabra *lambda* viene del cálculo lambda, un sistema matemático para representar funciones y aplicaciones de funciones. En Scala, `x => x * 2` recibe un número y devuelve su doble.

## ¿En qué se diferencia de una función nombrada? Escribe la misma lógica de ambas forma
Una **función nombrada** tiene un identificador: `def doble(x: Int): Int = x * 2`. La misma lógica como función anónima es `val dobleLambda = (x: Int) => x * 2`. Las dos producen `6` al recibir `3`.

## ¿Qué significa que las funciones sean ciudadanos de primera clase? ¿Por qué eso permite pasar una lambda a map o filter?
Las funciones son **ciudadanos de primera clase** cuando pueden guardarse en variables, pasarse como argumentos y devolverse desde otras funciones. Por eso `List(1, 2, 3).map(x => x * 2)` puede recibir la lambda y devolver `List(2, 4, 6)`. En `filter`, la lambda debe devolver un valor booleano.

## ¿Qué es un closure? Muestra una lambda que use una variable definida fuera de ella
Un **closure** es una función que utiliza variables de su entorno exterior. Ejemplo: `val impuesto = 0.15; val total = (precio: Double) => precio * (1 + impuesto)`. La función utiliza `impuesto` aunque no sea un parámetro suyo.


## Parte 3. Aplicar: ¿en qué problemas sirven las funciones anónimas?


| Tipo | Situación real | Operación y criterio | Resultado esperado en los tres lenguajes |
|---|---|---|---|
| Selección | Mostrar artículos que cuestan más de $50 | `filter`: precio > 50 | Laptop, Monitor, Audifonos |
| Transformación | Preparar nombres para un catálogo en mayúsculas | `map`: nombre en mayúsculas | LAPTOP, MOUSE, TECLADO, MONITOR, CUADERNO, LAPIZ, MOCHILA, AUDIFONOS, BOTELLA, AGENDA |
| Validación | Comprobar si hay artículos agotados y si todos tienen precio positivo | `exists`/`forall`, `anyMatch`/`allMatch`, `any`/`all` | falso, verdadero |
| Conteo | Saber cuántos artículos tecnológicos se ofrecen | `count` o equivalente | 5 |
| Extremos | Detectar el artículo más caro y el más barato | `maxBy`/`minBy` o equivalente | Laptop, Lapiz |
| Aplanamiento | Unir palabras de nombres en una sola lista | `flatMap` o equivalente | 10 palabras, una por producto |
| Acumulación | Calcular el valor monetario total de las existencias | `foldLeft` o `reduce` | 12235 |



## Parte 4. Ampliar: cuatro operaciones nuevas

Archivo: [`operaciones/Parte4.scala`](operaciones/Parte4.scala). Todas las operaciones usan los productos anteriores.

| Operación | Qué hace y lambda que recibe | Problema real y tipo relacionado | Salida esperada |
|---|---|---|---|
| `sortBy` | Ordena por la clave que devuelve `p => p.precio` (Int) | Ordenar catálogo por precio; ordenación, tipo nuevo | Lapiz, Cuaderno, Agenda, Botella, Mouse, Mochila, Teclado, Audifonos, Monitor, Laptop |
| `groupBy` | Agrupa por la clave `p => p.categoria` (String) | Organizar artículos por departamento; agrupación, tipo nuevo | Tecnologia: 5; Papeleria: 3; Accesorios: 2 |
| `partition` | Separa según `p => p.stock > 0` (Boolean) | Separar disponibles y agotados; selección | 10 disponibles y 0 agotados |
| `find` | Devuelve el primer elemento que satisface `p => p.precio > 500` (Boolean) | Localizar un producto costoso; selección | Some(Laptop) |

**4.3 Combinar Operacioens** Pregunta: ¿Qué categorías tienen más de dos productos, ordenadas de mayor a menor por cantidad? 
Se usa una sola cadena `groupBy` → `filter` → `sortBy`: primero se agrupan productos por categoría, después se conservan grupos con más de dos productos y por último se ordenan descendentemente según su tamaño. 

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

**5.2 Conclusión.** 
Las funciones anónimas permiten indicar qué transformación, condición o cálculo se desea aplicar a los elementos de una lista sin crear una función con nombre para cada paso. Esto reduce código repetido y hace más visibles las reglas del problema. En los ejemplos de inventario, la misma idea resolvió selección, validación, conteo y acumulación en tres lenguajes.

Su utilidad también aparece en herramientas reales como Apache Spark: las funciones que se pasan a operaciones de transformación permiten describir qué hacer con grandes colecciones de datos. Aunque la sintaxis varía entre Scala, Java y Python, el concepto de pasar funciones como valores es común a los tres.

## Fuentes y uso de IA

- [Apache Spark, RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html)
- [pandas.DataFrame.assign](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.assign.html)
- [Scala Collections](https://docs.scala-lang.org/overviews/collections-2.13/overview.html)
- [Oracle, Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
- [Python, Functional Programming HOWTO](https://docs.python.org/3/howto/functional.html)
- **Herramienta de IA:** ChatGPT utilizado como apoyo para la redacción, organización del repositorio y elaboración de ejemplos de código. 
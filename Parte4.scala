// Parte 4: cuatro operaciones nuevas, con la misma lista de Parte 3.
// Ejecutar junto con scala/Parte3.scala en el REPL o Scala CLI.
// sortBy: ordena usando una lambda que devuelve una clave numerica.
println("sortBy: " + productos.sortBy(p => p.precio).map(p => p.nombre))
// groupBy: agrupa segun una lambda que devuelve la categoria.
println("groupBy: " + productos.groupBy(p => p.categoria).view.mapValues(_.size).toMap)
// partition: divide la lista segun una lambda booleana.
val (disponibles, agotados) = productos.partition(p => p.stock > 0)
println("partition: " + (disponibles.size, agotados.size))
// find: encuentra el primer elemento que cumple la condicion.
println("find: " + productos.find(p => p.precio > 500).map(p => p.nombre))
// Combinacion: dos nuevas (groupBy y sortBy) y una vista (filter).
// Pregunta: ¿que categorias tienen mas de dos productos y como se ordenan por cantidad?
val resultado = productos.groupBy(p => p.categoria)
  .filter { case (categoria, lista) => lista.size > 2 }
  .toList.sortBy { case (categoria, lista) => -lista.size }
println("Combinacion: " + resultado.map { case (categoria, lista) => (categoria, lista.size) })

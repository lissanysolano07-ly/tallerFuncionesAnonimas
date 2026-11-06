
case class Producto(nombre: String, categoria: String, precio: Int, stock: Int)
val productos = List(
  Producto("Laptop", "Tecnologia", 850, 8),
  Producto("Mouse", "Tecnologia", 20, 30),
  Producto("Teclado", "Tecnologia", 45, 15),
  Producto("Monitor", "Tecnologia", 220, 7),
  Producto("Cuaderno", "Papeleria", 3, 60),
  Producto("Lapiz", "Papeleria", 1, 100),
  Producto("Mochila", "Accesorios", 35, 12),
  Producto("Audifonos", "Tecnologia", 65, 20),
  Producto("Botella", "Accesorios", 12, 25),
  Producto("Agenda", "Papeleria", 8, 40)
)

println("Seleccion: " + productos.filter(p => p.precio > 50).map(p => p.nombre))
println("Transformacion: " + productos.map(p => p.nombre.toUpperCase))
println("Validacion: " + (productos.exists(p => p.stock == 0), productos.forall(p => p.precio > 0)))
println("Conteo: " + productos.count(p => p.categoria == "Tecnologia"))
println("Extremos: " + (productos.maxBy(p => p.precio).nombre, productos.minBy(p => p.precio).nombre))
println("Aplanamiento: " + productos.flatMap(p => p.nombre.split(" ").toList))
println("Acumulacion: " + productos.foldLeft(0)((total, p) => total + p.precio * p.stock))

// Parte 4: cuatro operaciones nuevas, con la misma lista de Parte 3.
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

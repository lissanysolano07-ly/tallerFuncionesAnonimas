from dataclasses import dataclass
from functools import reduce

@dataclass
class Producto:
    nombre: str
    categoria: str
    precio: int
    stock: int

productos = [
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
]

# Seleccion
print("Seleccion:", list(map(lambda p: p.nombre, filter(lambda p: p.precio > 50, productos))))
# Transformacion
print("Transformacion:", list(map(lambda p: p.nombre.upper(), productos)))
# Validacion
print("Validacion:", (any(map(lambda p: p.stock == 0, productos)), all(map(lambda p: p.precio > 0, productos))))
# Conteo
print("Conteo:", len(list(filter(lambda p: p.categoria == "Tecnologia", productos))))
# Extremos
print("Extremos:", (max(productos, key=lambda p: p.precio).nombre, min(productos, key=lambda p: p.precio).nombre))
# Aplanamiento
print("Aplanamiento:", list(reduce(lambda acumulado, p: acumulado + p.nombre.split(), productos, [])))
# Acumulacion
print("Acumulacion:", reduce(lambda total, p: total + p.precio * p.stock, productos, 0))

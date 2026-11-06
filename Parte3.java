import java.util.*;
import java.util.stream.*;

// Ejecutar con Java 17 o superior: javac Parte3.java && java Parte3
public class Parte3 {
  record Producto(String nombre, String categoria, int precio, int stock) {}
  public static void main(String[] args) {
    List<Producto> productos = List.of(
      new Producto("Laptop", "Tecnologia", 850, 8),
      new Producto("Mouse", "Tecnologia", 20, 30),
      new Producto("Teclado", "Tecnologia", 45, 15),
      new Producto("Monitor", "Tecnologia", 220, 7),
      new Producto("Cuaderno", "Papeleria", 3, 60),
      new Producto("Lapiz", "Papeleria", 1, 100),
      new Producto("Mochila", "Accesorios", 35, 12),
      new Producto("Audifonos", "Tecnologia", 65, 20),
      new Producto("Botella", "Accesorios", 12, 25),
      new Producto("Agenda", "Papeleria", 8, 40)
    );
    // Seleccion
    System.out.println("Seleccion: " + productos.stream().filter(p -> p.precio() > 50).map(p -> p.nombre()).toList());
    // Transformacion
    System.out.println("Transformacion: " + productos.stream().map(p -> p.nombre().toUpperCase()).toList());
    // Validacion
    System.out.println("Validacion: (" + productos.stream().anyMatch(p -> p.stock() == 0) + "," + productos.stream().allMatch(p -> p.precio() > 0) + ")");
    // Conteo
    System.out.println("Conteo: " + productos.stream().filter(p -> p.categoria().equals("Tecnologia")).count());
    // Extremos
    System.out.println("Extremos: (" + productos.stream().max(Comparator.comparingInt(p -> p.precio())).orElseThrow().nombre() + "," + productos.stream().min(Comparator.comparingInt(p -> p.precio())).orElseThrow().nombre() + ")");
    // Aplanamiento
    System.out.println("Aplanamiento: " + productos.stream().flatMap(p -> Arrays.stream(p.nombre().split(" "))).toList());
    // Acumulacion
    System.out.println("Acumulacion: " + productos.stream().reduce(0, (total, p) -> total + p.precio() * p.stock(), Integer::sum));
  }
}

package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Libro;
import modelo.Venta;
import servicio.*;

public class VentaControlador {
	private static VentaService ventaService = new VentaService();
	private static int opcion;
	
	public  VentaService getVentaService() {
		return ventaService;
	}
	
	
	public  VentaControlador agregar(Scanner entrada, LibroControlador libroControlador) {
		List<Libro> libros = libroControlador.getLibroService().cargarLibros();
		if(libros.isEmpty()) {
			System.out.println("No hay libros en el inventario.");
			System.out.println("¿Desea registrar un libro? (s) - (n)");
			String respuesta = entrada.nextLine();
			if(!respuesta.equalsIgnoreCase("s")) {
				System.out.println("Saliendo ...");
			}
			
			libroControlador.agregar(entrada);
			System.out.print("Libro agregado correctamente !!");
			libros = libroControlador.getLibroService().cargarLibros();
			libros.forEach(System.out::println);
		}

		boolean valido = false;
		Libro libro = null;
		do {
			libros.forEach(System.out::println);
			System.out.println("Ingresa el nombre del libro: ");
			String nombreL = entrada.nextLine();
			libro = libros.stream().
					filter(l -> l.getTitulo()
							.equals(nombreL))
					.findFirst().orElse(null);
			if(libro == null ) {
				System.err.println("Error: El libro especificado no fue encontrado. Intentelo de nuevo.");
			} else valido = true;
			
		} while(!valido);
		
		System.out.println("El stock actual del libro es de: " + libro.getStock());
		System.out.println("Ingresa la cantidad de libros que deseas adquirir: ");
		int cantidad = entrada.nextInt();
		entrada.nextLine();
		
		while( libro.getStock() < cantidad) {
			System.err.println("Error: La cantidad del libros a comprar supera al stock disponible, vuelve a intentarlo.");
			System.out.println("El stock actual del libro es de: " + libro.getStock());
			System.out.print("Ingresa la cantidad de libros a comprar: ");
			cantidad = entrada.nextInt();
			entrada.nextLine();
		}
		
		for(Libro l : libros) {
			if(l.getTitulo().equals(libro.getTitulo())) {
				l.setStock(l.getStock() - cantidad);
				break;
			}
		}
		
		libroControlador.getLibroService().guardarLibro(libros);
		
		List<Venta> ventas = ventaService.cargarVentas(libros);
		ventas.add(new Venta(libro, cantidad, LocalDate.now()));
		ventaService.guardar(ventas);
		
		System.out.println("Venta guardada correctamente ");
		
		return this;
	}
	
}

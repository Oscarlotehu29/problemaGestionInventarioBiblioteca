package controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Libro;
import servicio.LibroService;

public class LibroControlador {
	private static LibroService libroService = new LibroService();
	private static int opcion;


	public static LibroService getLibroService() {
		return libroService;
	}
	
	public  LibroControlador agregar(Scanner entrada) {
		System.out.println("Ingresa el titulo del libro: ");
		String titulo = entrada.nextLine();
		
		System.out.println("Ingresa el autor: ");
		String autor = entrada.nextLine();
		
		System.out.println("Ingresa el precio: ");
		double precio = entrada.nextDouble();
		entrada.nextLine();
		System.out.println("Ingresa el genero: ");
		String genero = entrada.nextLine();
		
		System.out.println("Ingresa el stock: ");
		int stock = entrada.nextInt();
		entrada.nextLine();
		List<Libro> libros = libroService.cargarLibros();
		libros.add(new Libro(titulo, autor, precio, genero, stock));
		libroService.guardarLibro(libros);
//		libros.clear();
		return this;
	}
	
	
}

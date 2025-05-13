package controlador;

import java.util.Scanner;

public class ReportesControlador {
	
	public static void inicio(Scanner entrada, VentaControlador ventaControlador, LibroControlador inventario) {
		int opcion = 0;
		do {
			menu();
			opcion = entrada.nextInt();
			entrada.nextLine();
			switch(opcion) {
			case 1 -> {
				ventaControlador.getVentaService()
				.librosMasVendidos(inventario.getLibroService().cargarLibros()).ifPresent(en ->{
					System.out.println("Titulo libro: " + en.getKey() + " Cantidad: " + en.getValue());
				});
			}
			
			case 2 -> {
				ventaControlador.getVentaService()
				.librosPorGenero(inventario.getLibroService().cargarLibros()).forEach((genero, cantidad) -> System.out.println("Genero: " + genero + " Cantidad: " + cantidad));
			}
			
			case 4 -> {
				inventario.getLibroService().filtroMenoresCincoStock().forEach((titulo, stock)-> {
					System.out.println("Titulo: " + titulo + " Stock: "+ stock);
				} );
			}
			}
		} while (opcion != 5);
	}
	
	private static void menu() {
		System.out.println("1- Libros más vendidos.");
		System.out.println("2- Total de ventas por género.");
		System.out.println("3- Promedio de precio de los libros vendidos.");
		System.out.println("4- Libros con stock menos a 5 unidades.");
		System.out.println("5- Salir");
	}
}

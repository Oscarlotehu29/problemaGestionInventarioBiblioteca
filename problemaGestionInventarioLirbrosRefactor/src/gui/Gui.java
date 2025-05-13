package gui;

import java.util.Scanner;

import controlador.LibroControlador;
import controlador.ReportesControlador;
import controlador.VentaControlador;

public class Gui {
	private static final Scanner entrada = new Scanner(System.in);
	private static LibroControlador libroControlador = new LibroControlador();
	private static VentaControlador ventaControlador = new VentaControlador();
	private static ReportesControlador reporteControlador = new ReportesControlador();
	public static void inicio() {
		int opcion;
		
		do {
				mostrarMenu();
				opcion = entrada.nextInt();
				entrada.nextLine();
				switch(opcion) {
				case 1 -> ventaControlador.agregar(entrada, libroControlador);
				case 2 -> {
					System.out.println("============= INVENTARIO ===============");
					System.out.println("titulo|autor|precio|genero|stock");
					libroControlador.getLibroService().cargarLibros().forEach(System.out::println);
				}
				case 3 -> reporteControlador.inicio(entrada, ventaControlador, libroControlador);
				
				case 4 -> {
					System.out.println("Titulo|Cantidad|Fecha");
					ventaControlador.getVentaService().cargarVentas(libroControlador.getLibroService().cargarLibros()).forEach(System.out::println);
				}
				
				case 5 -> libroControlador.agregar(entrada);
				case 6 -> System.out.println("Saliendo de la aplicación. Vuelva pronto.");
				default -> System.out.println("El número introducido corresponde a ninguna opción, vuelve a intentarlo.");
			}
			
			
		} while(opcion != 6);
	}
	
	private static void mostrarMenu() {
		System.out.println("============== Menú Principal ================");
		System.out.println("1. Registrar una nueva venta.");
		System.out.println("2. Ver inventario.");
		System.out.println("3. Reportes.");
		System.out.println("4. Ver ventas.");
		System.out.println("5. Registrar libro.");
		System.out.println("6. Guardar y salir.");
		System.out.println("Seleccione un opción: ");
	}	
}

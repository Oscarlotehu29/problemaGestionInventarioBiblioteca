package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Venta {
	private Libro libro;
	private int cantidad;
	private LocalDate fecha;
	
	public Venta(Libro libro, int cantidad, LocalDate fecha) {
		this.libro = libro;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}

	public Libro getLibro() {
		return libro;
	}

	public int getCantidad() {
		return cantidad;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	@Override
	public String toString() {
		return "{Libro:" + libro.getTitulo() + ", cantidad:" + cantidad + ", fecha:" + fecha + "}";
	}
	
	
	public String toCSV() {
		String linea = libro.getTitulo() + "|" + cantidad + "|" + fecha;
		return linea;
		
	}
	
	public static Venta fromCSV(String linea, List<Libro> inventario) {
		
		String[] elementos = linea.split("\\|");
		String titulo = elementos[0];
		int cantidad = Integer.parseInt(elementos[1]);
		LocalDate fecha = LocalDate.parse(elementos[2]);
		
//		Optional<Libro> libro = inventario.stream()
//				.filter(l -> l.getTitulo().equals(titulo))
//				.findFirst();
		
		//Segunda opción
		Libro libro = inventario.stream()
				.filter(l -> l.getTitulo().equals(titulo))
				.findFirst().orElse(null);
		
//		return !libro.isPresent() ? null : new Venta(libro.get(), cantidad, fecha);
		
		if(libro == null) return null;
		
		
		
		Venta venta = new Venta(libro, cantidad, fecha);
		return venta;
	}
	
	
	
}

package servicio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import modelo.Libro;
import modelo.Venta;

public class VentaService {
	private static final String ARCHIVO_VENTAS = "ventas.csv";
	
	
	public void guardar(List<Venta> ventas) {
		try {
			PrintWriter pw = new PrintWriter(ARCHIVO_VENTAS);
			pw.println("libro|cantidad|fecha");
			ventas.stream().forEach(venta -> pw.println(venta.toCSV()));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print(e.toString());
		} 
	}
	
	
	public List<Venta> cargarVentas(List<Libro> inventario){
		List<Venta> ventas = new ArrayList<Venta>();
		if(!Files.exists(Paths.get(ARCHIVO_VENTAS))) return ventas;
		try {
			//Files.lines(Paths.get(ARCHIVO_VENTAS)).findFirst().ifPresent(System.out::println);;
			ventas = Files.lines(Paths.get(ARCHIVO_VENTAS))
					.skip(1)
					.map(linea -> Venta.fromCSV(linea, inventario))
					.collect(Collectors.toList())
					;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			return ventas;
		}
		
		
		
		return ventas;
	}
	
	public Optional<Map.Entry<String, Long>> librosMasVendidos(List<Libro> inventario){
		var ventas = cargarVentas(inventario).stream()
				.collect(
						Collectors.groupingBy
						(v -> v.getLibro().getTitulo(), Collectors.counting() 
								));
		
		
		return ventas.entrySet().stream().max(Map.Entry.comparingByValue());
	}
	
	
	public Map<String, Long> librosPorGenero(List<Libro> inventario){
		var ventas = cargarVentas(inventario).stream()
				.collect(
						Collectors.groupingBy
						(v -> v.getLibro().getGenero(), Collectors.counting() 
								));
		
		
		return ventas;
	}
}

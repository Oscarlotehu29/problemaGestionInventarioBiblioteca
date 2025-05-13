package servicio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import modelo.Libro;

public class LibroService {
	private  final String ARCHIVO_LIBROS = "libros.csv";
	
	
	
	public List<Libro> cargarLibros(){
		List<Libro> libros = new ArrayList<Libro>();
		if(!Files.exists(Paths.get(ARCHIVO_LIBROS))) return libros;
		
		try {
//			Files.lines(Paths.get(ARCHIVO_LIBROS)).findFirst().ifPresent(System.out::println);
			libros = Files.lines(Paths.get(ARCHIVO_LIBROS))
					// .map(Libro::fromCSV);
					.skip(1)
					.map(l -> Libro.fromCSV(l)).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return libros;
	}
	
	
	public void guardarLibro(List<Libro> inventario) {
		
		try {
			PrintWriter pw = new PrintWriter(ARCHIVO_LIBROS);
			pw.println("titulo|autor|precio|genero|stock");
			inventario.stream()
			.forEach(libro -> pw.println(libro.toCSV()));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
			
		}
	}
	
	public Map<String,Integer> filtroMenoresCincoStock(){
		var libros = cargarLibros();
		return libros.stream().filter(l -> l.getStock() < 5).collect(Collectors.toMap(Libro::getTitulo, Libro::getStock));
	}
}
